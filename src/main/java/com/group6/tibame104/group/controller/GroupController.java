package com.group6.tibame104.group.controller;

import com.group6.tibame104.group.model.GroupService;
import com.group6.tibame104.group.model.GroupVO;
import com.group6.tibame104.groupdiscount.model.GroupdiscountService;
import com.group6.tibame104.groupdiscount.model.GroupdiscountVO;
import com.group6.tibame104.groupproduct.model.GroupproductService;
import com.group6.tibame104.groupproduct.model.GroupproductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/front-end/group")
public class GroupController {
    @Autowired
    private GroupService groupSvc;
    @Autowired
    private GroupproductService groupproductSvc;
    @Autowired
    private GroupdiscountService groupdiscountSvc;

	/*
	 * 新增團購訂單
	 * */
    @PostMapping("/addOrder")
    public String addOrder(
            HttpSession session,
            Model model,
            @RequestParam("groupBuyID") String str,
            @RequestParam("groupBuyProductID") String str2,
            @RequestParam("groupBuyCount") String str3
        ) {
        List<String> errorMsgs = new LinkedList<String>();
        model.addAttribute("errorMsgs", errorMsgs);

        Integer groupBuyID = null;
        try {
            groupBuyID = Integer.valueOf(str);
        } catch (Exception e) {
            errorMsgs.add("團購編號錯誤");
        }
        if (!errorMsgs.isEmpty()) {
            return "front-end/group/addOrder";
        }

        Integer groupBuyProductID = null;
        try {
            groupBuyProductID = Integer.valueOf(str2);
        } catch (Exception e) {
            errorMsgs.add("團購商品編號錯誤");
        }
        if (!errorMsgs.isEmpty()) {
            return "front-end/group/addOrder";
        }
        
        Double groupBuyCount = null;
        try {
            groupBuyCount = Double.valueOf(str3);
        } catch (Exception e) {
            errorMsgs.add("折扣錯誤");
        }
       	/*如果有錯誤就中斷*/
        if (!errorMsgs.isEmpty()) {
            return "front-end/group/addOrder";// 程式中斷
        }
		/*
		*  取得當前團購跟團購商品資訊
		* */
        GroupVO groupVO = groupSvc.getOneGroup(groupBuyID);
        GroupproductVO groupproductVO = groupproductSvc.getOneGroupproduct(groupBuyProductID);

        if (groupVO == null) {
            errorMsgs.add("查無資料");
        }

		session.setAttribute("groupVO", groupVO);
		session.setAttribute("groupBuyCount", groupBuyCount);
		session.setAttribute("groupproductVO", groupproductVO);

        return "front-end/group/addOrder";
        
    }
	/*
	* 後台取得該筆團購
	* */
    @PostMapping("/getOneForUpdate")
    public String getOneForUpdate(
    		Model model,
    		@RequestParam("groupBuyID") Integer groupBuyID
    		) {

		GroupVO groupVO = groupSvc.getOneGroup(groupBuyID);
		 List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();

		model.addAttribute("groupVO", groupVO);
		model.addAttribute("groupproductVOs",groupproductVOs);

		return "back-end/group/updateGroup";
    }
	/*
	 *	後台更新該筆團購
	 * */
   @PostMapping("/update")
   public String update(
		   Model model,
		   @RequestParam("groupBuyID") Integer groupBuyID,
		   @RequestParam("groupBuyProductID") Integer groupBuyProductID,
		   @RequestParam("administratorID") Integer administratorID,
		   @RequestParam("groupBuyProductOrderTotal") Integer groupBuyProductOrderTotal,
		   @RequestParam("groupBuyingState") Boolean groupBuyingState,
		   @RequestParam("groupBuyingOnLoadDate") Timestamp groupBuyingOnLoadDate,
		   @RequestParam("groupBuyingOffLoadDate") Timestamp groupBuyingOffLoadDate
		   ) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());// 獲取系統當前時間

			GroupVO groupVO = new GroupVO();

			groupVO.setGroupBuyProductID(groupBuyProductID);
			groupVO.setAdministratorID(administratorID);
			groupVO.setGroupBuyProductOrderTotal(groupBuyProductOrderTotal);
			groupVO.setGroupBuyingState(groupBuyingState);
			groupVO.setGroupBuyingOnLoadDate(groupBuyingOnLoadDate);
			groupVO.setGroupBuyingOffLoadDate(groupBuyingOffLoadDate);
			groupVO.setUpdateTime(updateTime);
			groupVO.setGroupBuyID(groupBuyID);

		groupVO = groupSvc.updateGroup(groupBuyProductID, administratorID, groupBuyProductOrderTotal,
				groupBuyingState, groupBuyingOnLoadDate, groupBuyingOffLoadDate, updateTime, groupBuyID);

		model.addAttribute("groupVO", groupVO);

	   return getAll(model);
   }
	/*
	 * 後台新增團購
	 * */
   @PostMapping("/insert")
   public String insert(
		   Model model,
		   @RequestParam("groupBuyProductID") Integer groupBuyProductID,
		   @RequestParam("administratorID") Integer administratorID,
		   @RequestParam("groupBuyProductOrderTotal") Integer groupBuyProductOrderTotal,
		   @RequestParam("groupBuyingState") Boolean groupBuyingState,
		   @RequestParam("groupBuyingOnLoadDate") Timestamp groupBuyingOnLoadDate,
		   @RequestParam("groupBuyingOffLoadDate") Timestamp groupBuyingOffLoadDate
		   ) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());// 獲取系統當前時間

			GroupVO groupVO = new GroupVO();

			groupVO.setGroupBuyProductID(groupBuyProductID);
			groupVO.setAdministratorID(administratorID);
			groupVO.setGroupBuyProductOrderTotal(groupBuyProductOrderTotal);
			groupVO.setGroupBuyingState(groupBuyingState);
			groupVO.setGroupBuyingOnLoadDate(groupBuyingOnLoadDate);
			groupVO.setGroupBuyingOffLoadDate(groupBuyingOffLoadDate);
			groupVO.setUpdateTime(updateTime);

			groupVO = groupSvc.addGroup(groupBuyProductID, administratorID, groupBuyProductOrderTotal, groupBuyingState,
					groupBuyingOnLoadDate, groupBuyingOffLoadDate, updateTime);

		return getAll(model);
   		}
		/*
		 * 後台刪除團購
		 * */
	   @PostMapping("/delete")
	   public String delete(
			   Model model,
			   @RequestParam("groupBuyID") Integer groupBuyID
			   ) {

			groupSvc.deleteGroup(groupBuyID);

			return getAll(model);
	   }
	   /*
	   * 首頁用，取得所有團購資訊
	   * */
	   @GetMapping("/allGroup")
	   public String allGroup(
			   Model model
			   ) {
		   
		  List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		  List<GroupVO> groupVOs = groupSvc.getAll();
		  List<GroupdiscountVO> groupdiscountVOs = groupdiscountSvc.getAll();

		  model.addAttribute("groupproductVOs",groupproductVOs);
		  model.addAttribute("groupVOs",groupVOs);
		  model.addAttribute("groupdiscountVOs",groupdiscountVOs);
		  
		   return "front-end/group/listAllGroup";
	   }
	   /*
	   * 團購主頁用，排序新到舊
	   * */
	   @GetMapping("/getAllDesc")
	   public String getAllDesc(
			   Model model
			   ) {
		   
		  List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		  List<GroupVO> groupVOs = groupSvc.getAllDesc();
		  List<GroupdiscountVO> groupdiscountVOs = groupdiscountSvc.getAll();

		  model.addAttribute("groupproductVOs",groupproductVOs);
		  model.addAttribute("groupVOs",groupVOs);
		  model.addAttribute("groupdiscountVOs",groupdiscountVOs);

		  return "front-end/group/listAllGroup";
	   }
		/*
		 * 團購主頁用，團購數量多到少
		 * */
	   @GetMapping("/orderBy")
	   public String orderBy(
			   Model model
			   ) {
		   
		  List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		  List<GroupVO> groupVOs = groupSvc.orderBy();
		  List<GroupdiscountVO> groupdiscountVOs = groupdiscountSvc.getAll();

		  model.addAttribute("groupproductVOs",groupproductVOs);
		  model.addAttribute("groupVOs",groupVOs);
		  model.addAttribute("groupdiscountVOs",groupdiscountVOs);

		  return "front-end/group/listAllGroup";
	   }

		/*
		* 後台用，取的所有團購資訊
		* */
	   @GetMapping("/getAll")
	   public String getAll(
			   Model model
			   ) {
		   List<GroupVO> groupVOs = groupSvc.getAll();
		   model.addAttribute("groupVOs",groupVOs);
		   List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		   model.addAttribute("groupproductVOs",groupproductVOs);
		   return "back-end/group/listAllGroup";
	   }

	   //取得全部團購商品 for新增
	   @GetMapping("/getGroupproduct")
	   public String getGroupproduct(
			   Model model) {
		   
		   List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		   model.addAttribute("groupproductVOs",groupproductVOs);
		   
		   return"back-end/group/addGroup";
	   }
	   //取得全部團購商品 for修改
	   @GetMapping("/getGroupproduct2")
	   public String getGroupproduct2(
			   Model model) {
		   
		   List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		   model.addAttribute("groupproductVOs",groupproductVOs);
		   
		   return"back-end/group/updateGroup";
	   }
		/*
		 * 測試用
		 * */
	   @GetMapping("/getJoinAll")
	   public String getJoinAll(
			   Model model) {
		   List<Object> groupVOs = groupSvc.getJoinAll();
		   model.addAttribute("groupVOs",groupVOs);

		   return "front-end/group/listAllGroup";
	   }
}