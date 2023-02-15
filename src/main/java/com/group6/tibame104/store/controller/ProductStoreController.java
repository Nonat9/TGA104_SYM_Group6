package com.group6.tibame104.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group6.tibame104.store.model.StoreVO;
import com.group6.tibame104.store.service.StoreService;

@Controller
@RequestMapping("/store")
public class ProductStoreController {

	@Autowired
	private StoreService storeSvc;

	@GetMapping("/productStoreServlet")
	public String productStoreServlet(Model model, @RequestParam("storeID") String storeIDstr) {

		Integer storeID = null;
		try {
			storeID = Integer.parseInt(storeIDstr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		StoreVO storeVO = storeSvc.findByPrimaryKey(storeSvc.findMemberID(storeID));
		model.addAttribute("storeVO", storeVO);
		/*
		 * 導向productStore頁面
		 */

		return "/front-end/store/productStore";

	}
}
