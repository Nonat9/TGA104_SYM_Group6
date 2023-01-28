package com.group6.tibame104.grouporder.controller;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group6.tibame104.group.model.GroupService;
import com.group6.tibame104.group.model.GroupVO;
import com.group6.tibame104.grouporder.model.GrouporderService;
import com.group6.tibame104.grouporder.model.GrouporderVO;

@Controller
@RequestMapping("/back-end/grouporder")
public class GrouporderController {
	@Autowired
	private GrouporderService grouporderSvc;
	@Autowired
	private GroupService groupSvc;

	/*
	* 顯示單一訂單資訊
	* */
	@PostMapping("getOneForUpdate")
	public String getOneForUpdate(
			Model model,
			@RequestParam("groupBuyOrderID") Integer groupBuyOrderID
			) {
		List<String> errorMsgs = new LinkedList<String>();
		model.addAttribute("errorMsgs", errorMsgs);

		GrouporderVO grouporderVO = grouporderSvc.getOneGrouporder(groupBuyOrderID);

		model.addAttribute("grouporderVO", grouporderVO);
		return "back-end/grouporder/update_GroupOrder_input";
	}
	/*
	* 更新該筆團購訂單
	* */
	@PostMapping("update")
	public String update(
			Model model,
			@RequestParam("groupBuyOrderID") Integer groupBuyOrderID,
			@RequestParam("groupBuyID") Integer groupBuyID,
			@RequestParam("memberID") Integer memberID,
			@RequestParam("groupBuyProductID") Integer groupBuyProductID,
			@RequestParam("groupBuyQuantity") Integer groupBuyQuantity,
			@RequestParam("groupBuyTotal") Integer groupBuyTotal,
			@RequestParam("orderTime") Timestamp orderTime,
			@RequestParam("paymentTerm") String paymentTerm,
			@RequestParam("paymentState") Integer paymentState,
			@RequestParam("giftVoucher") Integer giftVoucher,
			@RequestParam("contactNumber") String contactNumber,
			@RequestParam("shippingLocation") String shippingLocation
			) {
		
		List<String> errorMsgs = new LinkedList<String>();
		model.addAttribute("errorMsgs", errorMsgs);

		try {
			 groupBuyID = Integer.valueOf(groupBuyID);
		}catch(NumberFormatException e){
			groupBuyID = 0;
			errorMsgs.add("團購編號請填數字.");
		}
		try {
			memberID = Integer.valueOf(memberID);
		}catch(NumberFormatException e){
			memberID = 0;
			errorMsgs.add("會員編號請填數字.");
		}
		try {
			groupBuyProductID = Integer.valueOf(groupBuyProductID);
		}catch(NumberFormatException e){
			groupBuyProductID = 0;
			errorMsgs.add("團購商品編號請填數字.");
		}
		try {
			groupBuyQuantity = Integer.valueOf(groupBuyQuantity);
		}catch(Exception e){
			groupBuyQuantity = 0;
			errorMsgs.add("團購數量請填數字.");
			return "back-end/grouporder/update_GroupOrder_input"; // 程式中斷
		}
		try {
			groupBuyTotal = Integer.valueOf(groupBuyTotal);
		}catch(NumberFormatException e){
			groupBuyTotal = 0;
			errorMsgs.add("總金額請填數字.");
		}
		String paymentTermReg = "^[(\u4e00-\u9fa5)]{1,4}$";
		if (paymentTerm == null || paymentTerm.trim().length() == 0) {
			errorMsgs.add("請勿空白");
		} else if(!paymentTerm.trim().matches(paymentTermReg)) { //以下練習正則(規)表示式(regular-expression)
			errorMsgs.add("付款方式錯誤");
        }
		try {
			giftVoucher = Integer.valueOf(giftVoucher);
		}catch(NumberFormatException e){
			giftVoucher = 0;
			errorMsgs.add("購物金請填數字.");
		}
		try {
			paymentState = Integer.valueOf(paymentState);
		}catch(NumberFormatException e){
			paymentState = 0;
			errorMsgs.add("結帳狀態請填數字.");
		}
		/*
		 * 以下練習正則(規)表示式(regular-expression)
		 * */
		String phoneReg = "^([-_－—\\s\\(]?)([\\(]?)((((0?)|((00)?))(((\\s){0,2})|([-_－—\\s]?)))|(([\\)]?)[+]?))(886)?([\\)]?)([-_－—\\s]?)([\\(]?)[0]?[1-9]{1}([-_－—\\s\\)]?)[1-9]{2}[-_－—]?[0-9]{3}[-_－—]?[0-9]{3}$";
		 contactNumber = contactNumber.trim();
		if (contactNumber == null || contactNumber.trim().length() == 0) {
			errorMsgs.add("請勿空白");
		} else if(!contactNumber.trim().matches(phoneReg)) {
			errorMsgs.add("電話輸入錯誤");
        }

		GrouporderVO grouporderVO = new GrouporderVO();
		
		grouporderVO.setGroupBuyOrderID(groupBuyOrderID);
		grouporderVO.setGroupBuyID(groupBuyID);
		grouporderVO.setMemberID(memberID);
		grouporderVO.setGroupBuyProductID(groupBuyProductID);
		grouporderVO.setGroupBuyQuantity(groupBuyQuantity);
		grouporderVO.setGroupBuyTotal(groupBuyTotal);
		grouporderVO.setOrderTime(orderTime);
		grouporderVO.setPaymentTerm(paymentTerm);
		grouporderVO.setPaymentState(paymentState);
		grouporderVO.setGiftVoucher(giftVoucher);
		grouporderVO.setContactNumber(contactNumber);
		grouporderVO.setShippingLocation(shippingLocation);

		if (!errorMsgs.isEmpty()) {
			model.addAttribute("grouporderVO", grouporderVO);
			return "back-end/grouporder/update_GroupOrder_input"; // 程式中斷
		}
		grouporderVO = grouporderSvc.updateGrouporder(groupBuyOrderID, groupBuyID, memberID,
				 groupBuyProductID, groupBuyQuantity,  groupBuyTotal, orderTime, paymentTerm,
				 paymentState,  giftVoucher,  contactNumber, shippingLocation);

		model.addAttribute("grouporderVO", grouporderVO);
		return "back-end/grouporder/listOneGroupOrder";
	}
	/*
	* 使用者：新增訂單，並更新團購總數
	* */
	@PostMapping("insert")
	public String insert(
			Model model,
			HttpSession session,
			@RequestParam("groupBuyID") Integer groupBuyID,
			@RequestParam("memberID") Integer memberID,
			@RequestParam("groupBuyProductID") Integer groupBuyProductID,
			@RequestParam("groupBuyQuantity") Integer groupBuyQuantity,
			@RequestParam("groupBuyTotal") Integer groupBuyTotal,
			@RequestParam("paymentTerm") String paymentTerm,
			@RequestParam("giftVoucher") Integer giftVoucher,
			@RequestParam("paymentState") Integer paymentState,
			@RequestParam("contactNumber") String contactNumber,
			@RequestParam("shippingLocation") String shippingLocation
		
			) {

			Timestamp orderTime = new Timestamp(System.currentTimeMillis());
			GrouporderVO grouporderVO = new GrouporderVO();

			grouporderVO.setMemberID(memberID);
			grouporderVO.setGroupBuyProductID(groupBuyProductID);
			grouporderVO.setGroupBuyQuantity(groupBuyQuantity);
			grouporderVO.setGroupBuyTotal(groupBuyTotal);
			grouporderVO.setOrderTime(orderTime);
			grouporderVO.setPaymentTerm(paymentTerm);
			grouporderVO.setPaymentState(paymentState);
			grouporderVO.setGiftVoucher(giftVoucher);
			grouporderVO.setContactNumber(contactNumber);
			grouporderVO.setShippingLocation(shippingLocation);

			GroupVO groupVO = new GroupVO();
			/*取得該團購資訊*/
			groupVO = groupSvc.getOneGroup(groupBuyID);
			/*取得現在團購數量*/
			Integer currentQua = groupVO.getGroupBuyProductOrderTotal();
			grouporderSvc.addGrouporder(groupBuyID, groupBuyProductID, memberID, groupBuyQuantity,  groupBuyTotal, orderTime, paymentTerm,
					 paymentState,  giftVoucher,  contactNumber, shippingLocation);
			/*更新團購總數*/
			System.out.println("已經新增訂單");
			groupSvc.updateGroupQua(currentQua+groupBuyQuantity,groupBuyID);
			System.out.println("已經更新團購數量");
			/*取出所有團購訂單*/
			List<GrouporderVO> grouporderVOs = grouporderSvc.getAll();
			model.addAttribute("grouporderVOs",grouporderVOs);

		return "/back-end/grouporder/listAllGroupOrder";
	}

	/*
	* 刪除該筆訂單
	* */
	@PostMapping("delete")
	public String delete(Model model, @RequestParam("groupBuyOrderID") Integer groupBuyOrderID) {

		grouporderSvc.deleteGrouporder(groupBuyOrderID);

		return getAll(model);
	}
	/*
	* 取得所有團購訂單
	* */
	@GetMapping("getAll")
	public String getAll(
			Model model
			) {

		List<GrouporderVO> grouporderVOs = grouporderSvc.getAll();
		model.addAttribute("grouporderVOs",grouporderVOs);

		return "/back-end/grouporder/listAllGroupOrder";
	}
}
