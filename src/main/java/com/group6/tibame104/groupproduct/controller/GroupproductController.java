package com.group6.tibame104.groupproduct.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.group6.tibame104.group.model.GroupService;
import com.group6.tibame104.group.model.GroupVO;
import com.group6.tibame104.groupdiscount.model.GroupdiscountService;
import com.group6.tibame104.groupdiscount.model.GroupdiscountVO;
import com.group6.tibame104.groupproduct.model.GroupproductService;
import com.group6.tibame104.groupproduct.model.GroupproductVO;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/back-end/groupproduct")
@MultipartConfig
public class GroupproductController {
	@Autowired
	private GroupproductService groupproductSvc;
	@Autowired
	private GroupService groupSvc;
	@Autowired
	private GroupdiscountService GroupdiscountSvc;

	@GetMapping("getAll")
	public String getAll(Model model) {
		List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		model.addAttribute("groupproductVOs", groupproductVOs);
		return "back-end/groupproduct/listAllGroupProducts";
	}

	@PostMapping("getOneForDisplay")
	public String getOneForDisplay(Model model, HttpSession session,
			@RequestParam("groupBuyProductID") Integer groupBuyProductID) {

		List<String> errorMsgs = new LinkedList<String>();
		model.addAttribute("errorMsgs", errorMsgs);

		if (!errorMsgs.isEmpty()) {
			return "front-end/groupproduct/select_page";// ????????????
		}

		/*
		*
		* ????????????????????????
		* */
		GroupproductVO groupproductVO = groupproductSvc.getOneGroupproduct(groupBuyProductID);

		if (groupproductVO == null) {
			errorMsgs.add("????????????");
		}

		if (!errorMsgs.isEmpty()) {
			return "front-end/groupproduct/select_page";// ????????????
		}

		model.addAttribute("groupproductVO", groupproductVO);

		List<GroupproductVO> groupproductVOs = groupproductSvc.getAll();
		model.addAttribute("groupproductVOs", groupproductVOs);


		/*Redis ??????????????????*/
		Jedis jedis = new Jedis("localhost", 6379);

		double count_num = 1;

		Map<String, Double> groupViews = new HashMap<>();
		String strID = groupBuyProductID.toString();

		Double count_current = jedis.zscore("groupViews", strID);
		/*
		* ?????????????????????????????????????????????????????????1
		* */
		if (count_current == null) {
			count_num = 1;
		} else {
			/* ????????? : 1 + ??????????????? */
			count_num += count_current;
		}

		groupViews.put(strID, count_num);
		/*
		* Jedis??????Key & Value
		* */
		jedis.zadd("groupViews", groupViews);

		/*
		* ?????????????????????????????????????????????
		* */
		Set<String> popProducts = jedis.zrevrangeByScore("groupViews", "+inf", "0", 0, 3);
		/*
		* ???????????????????????????????????????popProducts
		* */
		model.addAttribute("popProducts", popProducts);

		model.addAttribute("count_num" + groupBuyProductID, count_num);

		Integer countNow = jedis.zscore("groupViews", strID).intValue();

		model.addAttribute("countNow", countNow);

		jedis.close();


		return "back-end/groupproduct/listOneGroupProduct";
	}

	@PostMapping("getOneForUpdate")
	public String getOneForUpdate(Model model, @RequestParam("groupBuyProductID") Integer groupBuyProductID) {

		List<String> errorMsgs = new LinkedList<String>();
		model.addAttribute("errorMsgs", errorMsgs);

		/*************************** 2.?????????????????? ****************************************/
		GroupproductVO groupproductVO = groupproductSvc.getOneGroupproduct(groupBuyProductID);
		/*************************** 3.????????????,????????????(Send the Success view) ************/
		model.addAttribute("groupproductVO", groupproductVO); // ??????????????????empVO??????,??????req
		return "back-end/groupproduct/update_groupProduct";
	}

	@PostMapping("update")
	public String update(Model model, @RequestParam("groupBuyProductID") Integer groupBuyProductID,
			@RequestParam("groupBuyProductPrice") Integer groupBuyProductPrice,
			@RequestParam("groupBuyProductPicture") Part groupBuyProductPicture,
			@RequestParam("groupBuyProductDescrip") String groupBuyProductDescrip) throws IOException {
		List<String> errorMsgs = new LinkedList<String>();

		model.addAttribute("errorMsgs", errorMsgs);
		// ????????????
		byte[] groupBuyProductPicture1 = null;
		//????????????????????????0
		if (groupBuyProductPicture.getSize() == 0) {
			groupBuyProductPicture = null;
		}else {
			InputStream in = groupBuyProductPicture.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(in);
			groupBuyProductPicture1 = new byte[bis.available()];
			bis.read(groupBuyProductPicture1);
		}
		
	

		GroupproductVO groupproductVO = new GroupproductVO();

		groupproductVO.setGroupBuyProductPrice(groupBuyProductPrice);
		groupproductVO.setGroupBuyProductPicture(groupBuyProductPicture1);
		groupproductVO.setGroupBuyProductDescrip(groupBuyProductDescrip);
		groupproductVO.setGroupBuyProductID(groupBuyProductID);

		// Send the use back to the form, if there were errors
		if (!errorMsgs.isEmpty()) {
			model.addAttribute("errorMsgs", errorMsgs); // ???????????????????????????empVO??????,?????????req
			return "emp/update_emp_input."; // ????????????
		}

		/*************************** 2.?????????????????? *****************************************/
		groupproductVO = groupproductSvc.updateGroupproduct(groupBuyProductPrice, groupBuyProductPicture1,
				groupBuyProductDescrip, groupBuyProductID);

		/*************************** 3.????????????,????????????(Send the Success view) *************/
		model.addAttribute("groupproductVO", groupproductVO); // ?????????update?????????,????????????empVO??????,??????req

		return getAll(model);
	}

	@PostMapping("insert")
	public String insert(Model model, @RequestParam("groupBuyProductPrice") Integer groupBuyProductPrice,
			@RequestParam("groupBuyProductPicture") Part groupBuyProductPicture,
			@RequestParam("groupBuyProductDescrip") String groupBuyProductDescrip

	) throws IOException {
		List<String> errorMsgs = new LinkedList<String>();
		model.addAttribute("errorMsgs", errorMsgs);

		byte[] groupBuyProductPicture1 = null;

		// ????????????
		InputStream in = groupBuyProductPicture.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(in);
		groupBuyProductPicture1 = new byte[bis.available()];
		bis.read(groupBuyProductPicture1);

		GroupproductVO groupproductVO = new GroupproductVO();

		groupproductVO.setGroupBuyProductPrice(groupBuyProductPrice);
		groupproductVO.setGroupBuyProductPicture(groupBuyProductPicture1);
		groupproductVO.setGroupBuyProductDescrip(groupBuyProductDescrip);

		// Send the use back to the form, if there were errors
		if (!errorMsgs.isEmpty()) {
			model.addAttribute("errorMsgs", errorMsgs); // ???????????????????????????empVO??????,?????????req
			return "/back-end/groupproduct/addEmp";
		}

		/*************************** 2.?????????????????? ***************************************/
		groupproductVO = groupproductSvc.addGroupproduct(groupBuyProductPrice, groupBuyProductPicture1,
				groupBuyProductDescrip);

		/*************************** 3.????????????,????????????(Send the Success view) ***********/

		return getAll(model);
	}

	@PostMapping("delete")
	public String delete(Model model, @RequestParam("groupBuyProductID") Integer groupBuyProductID) {

		List<String> errorMsgs = new LinkedList<String>();
		model.addAttribute("errorMsgs", errorMsgs);

		/*************************** 2.?????????????????? ***************************************/
		groupproductSvc.deleteGroupproduct(groupBuyProductID);

		/*************************** 3.????????????,????????????(Send the Success view) ***********/

		return getAll(model);
	}

	@PostMapping("/GroupproductSearch")
	public void GroupproductSearch(Model model, HttpServletRequest req, HttpServletResponse res,
//				@RequestParam("groupbuyProductID") String groupbuyProductID1,
//				@RequestParam("groupbuyProductPrice") String groupbuyProductPrice1,
			@RequestParam("groupbuyProductDescrip") String groupbuyProductDescrip1) throws IOException {

		res.setCharacterEncoding("UTF-8");
		PrintWriter writer = res.getWriter();
		/* queryString */
		Map<String, String> queryString = new HashMap<String, String>();
		/* JSON */
		Gson gson = new Gson();

		/* 1. ??????????????????????????? */
//			Integer groupbuyProductID = null;
//			try {
//				groupbuyProductID = Integer.valueOf(groupbuyProductID1.trim());
//				queryString.put("groupbuyProductID", groupbuyProductID + "");
//			} catch (Exception e) {
//				System.out.println("groupbuyProductID??????");
//			}
//
//			Integer groupbuyProductPrice = null;
//			try {
//				groupbuyProductPrice = Integer.valueOf(groupbuyProductPrice1.trim());
//				queryString.put("groupbuyProductPrice", groupbuyProductPrice + "");
//			} catch (Exception e) {
//				System.out.println("groupbuyProductPrice??????");
//			}
		String groupbuyProductDescrip = null;
		try {
			groupbuyProductDescrip = groupbuyProductDescrip1.trim();
			queryString.put("groupbuyProductDescrip", "'%" + groupbuyProductDescrip + "%'");
		} catch (Exception e) {
			System.out.println("groupbuyProductDescrip??????");
		}

//			System.out.println("queryString = " + queryString);

		List<GroupproductVO> allByCond = groupproductSvc.getAllBySearch(queryString);
//			List<GroupVO> groupVOs = groupSvc.getAll();
//			List<List> test = new ArrayList<List>();
//			for(int i = 0 ; i < allByCond.size() ; i++) {
//				for(int j = 0 ; j < groupVOs.size() ; j++) {
//				if(allByCond.get(i).getGroupBuyProductID() == groupVOs.get(j).getGroupBuyProductID()) {
//					System.out.print(allByCond.get(i));
//					System.out.println(groupVOs.get(j));
//					test.add();
//					test.add();
//				}
//				
//				}
//				
//			}

//			List<GroupdiscountVO> groupdiscountVOs = GroupdiscountSvc.getAll();
//			test.add(allByCond);
//			test.add(groupVOs);
//			test.add(groupdiscountVOs);

		String json = gson.toJson(allByCond);
		writer.write(json);

	}

}
