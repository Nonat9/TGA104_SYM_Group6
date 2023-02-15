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
	public List<ProductVO> getAllByCond(Model model, String storeID,
			@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "productID", required = false) String productIDStr,
			@RequestParam(value = "productSecID", required = false) String productSecIDStr,
			@RequestParam(value = "productStock", required = false) String productStockStr,
			@RequestParam(value = "productStock2", required = false) String productStockStr2,
			@RequestParam(value = "productPrice", required = false) String productPriceStr,
			@RequestParam(value = "productPrice2", required = false) String productPriceStr2, String productStatus) {

		// 錯誤處理
		Map<String, String> errorMsgs = new HashMap<String, String>();
		model.addAttribute("errorMsgs", errorMsgs);

		/* queryString */
		Map<String, String> queryString = new HashMap<String, String>();

		/* 1. 請求參數的格式整理 */

		queryString.put("storeID", storeID);

		// 只要productName不是空值
		if (productName != null && (!productName.isEmpty())) {
			queryString.put("productName", productName);
		}

		Integer productID = null;
		try {
			productID = Integer.valueOf(productIDStr.trim());
			queryString.put("productID", productID + "");
		} catch (Exception e) {

		}

		Integer productStock = null;
		try {
			productStock = Integer.valueOf(productStockStr.trim());
			queryString.put("productStock", productStock + "");
		} catch (Exception e) {

		}

		Integer productStock2 = null;
		try {
			productStock2 = Integer.valueOf(productStockStr2.trim());
			queryString.put("productStock2", productStock2 + "");
		} catch (Exception e) {

		}

		Integer productSecID = null;
		try {
			productSecID = Integer.valueOf(productSecIDStr.trim());
			queryString.put("productSecID", productSecID + "");
		} catch (Exception e) {

		}

		Integer productPrice = null;
		try {
			productPrice = Integer.valueOf(productPriceStr.trim());
			queryString.put("productPrice", productPrice + "");
		} catch (Exception e) {

		}

		Integer productPrice2 = null;
		try {
			productPrice2 = Integer.valueOf(productPriceStr2.trim());
			queryString.put("productPrice2", productPrice2 + "");
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
