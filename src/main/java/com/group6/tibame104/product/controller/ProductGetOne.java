package com.group6.tibame104.product.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group6.tibame104.product.model.ProductVO;
import com.group6.tibame104.product.service.ProductService;

@Controller
@RequestMapping("/product/productGetOne")
public class ProductGetOne {
	
	@Autowired
	private ProductService productSvc;

	@GetMapping("/get")
	public String update(Model model,
			@RequestParam("productID") String productIDStr) {
			
		
		Map<String, String> errorMsgs = new HashMap<String, String>();
		model.addAttribute("errorMsgs", errorMsgs);

		if (productIDStr == null || (productIDStr.trim()).length() == 0) {
			errorMsgs.put("productID", "請輸入正常的數字");
		}

		Integer productID = null;
		try {
			productID = Integer.valueOf(productIDStr);
		} catch (Exception e) {
			errorMsgs.put("productID", "請輸入正常的數字");
		}
		/*
		 * 如果報錯 轉去 Error 頁面
		 */
		if (!errorMsgs.isEmpty()) {
			return "front-end/store/Error";
		}
		/*
		 * 取得資料
		 * 
		 */
		ProductVO productVO = productSvc.findByPrimaryKey(productID);

		/* 
		 * 導向 productUpdate頁面
		 */
		model.addAttribute("productVO", productVO);
		return "front-end/product/updateProduct";
		
	}

}
