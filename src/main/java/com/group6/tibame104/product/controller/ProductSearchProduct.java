package com.group6.tibame104.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group6.tibame104.product.model.ProductVO;
import com.group6.tibame104.product.service.ProductService;

@RestController
@RequestMapping("/product/productSearchProduct")
public class ProductSearchProduct {

	@Autowired
	private ProductService productSvc;

	@PostMapping("/getAll_By_Cond")
	public List<ProductVO> getAllByCond(Model model,
			String storeID,
			@RequestParam(value = "productID", required = false) String productIDStr,
			@RequestParam(value = "productSecID", required = false) String productSecIDStr,
			String productStatus) {

		// 錯誤處理
		Map<String,String> errorMsgs = new HashMap<String, String>();
		model.addAttribute("errorMsgs", errorMsgs);

		/* queryString */
		Map<String, String> queryString = new HashMap<String, String>();

		/* 1. 請求參數的格式整理 */

		queryString.put("storeID", storeID);

		Integer productID = null;
		try {
			productID = Integer.valueOf(productIDStr.trim());
			queryString.put("productID", productID + "");
		} catch (Exception e) {

		}

		Integer productSecID = null;
		try {
			productSecID = Integer.valueOf(productSecIDStr.trim());
			queryString.put("productSecID", productSecID + "");
		} catch (Exception e) {

		}

		queryString.put("productStatus", productStatus);

		/***************************
		 * 搜尋資料
		 ***************************************/
		System.out.println("queryString = " + queryString);
		return productSvc.getAllByCondFront(queryString);

	}

}
