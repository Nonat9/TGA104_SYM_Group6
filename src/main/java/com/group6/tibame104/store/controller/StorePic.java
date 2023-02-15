package com.group6.tibame104.store.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group6.tibame104.store.service.StoreService;

@RestController
@RequestMapping("/store/storePic")
public class StorePic {
	@Autowired
	private StoreService storeSvc;

	@GetMapping("/get")
	public byte[] getOneForDisplay(Model model, @RequestParam("storeID") String storeIDstr) {
		/* 錯誤處理 */
		Map<String, String> errorMsgs = new HashMap<String, String>();
		model.addAttribute("errorMsgs", errorMsgs);

		try {
			Integer storeID = Integer.valueOf(storeIDstr);
			return storeSvc.findPic(storeID);
		} catch (Exception e) {

		}
		return null;

	}

}