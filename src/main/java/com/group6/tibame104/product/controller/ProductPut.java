package com.group6.tibame104.product.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group6.tibame104.product.service.ProductService;

@RestController
@RequestMapping("/product/productPut")
public class ProductPut {

	@Autowired
	private ProductService productSvc;

	@PostMapping("/on")
	public Map<String, String> putOn(Model model,@RequestBody String productID) throws IOException {
		/* 錯誤處理 */
		Map<String, String> errorMsgs = new HashMap<String, String>();

		try {
			Integer valueOf = Integer.valueOf(productID);
			productSvc.putOn(valueOf);;
			errorMsgs.put("message", "success");
			return errorMsgs;
		} catch (Exception e) {
			errorMsgs.put("message", "fail");
			return errorMsgs;
		}

	}

	@PostMapping("/off")
	public Map<String, String> putOff(Model model, @RequestBody String productID) throws IOException {
		/* 錯誤處理 */
		Map<String, String> errorMsgs = new HashMap<String, String>();

		try {
			Integer valueOf = Integer.valueOf(productID);
			productSvc.putOff(valueOf);
			errorMsgs.put("message", "success");
			return errorMsgs;
		} catch (Exception e) {
			errorMsgs.put("message", "fail");
			return errorMsgs;
		}

	}

}
