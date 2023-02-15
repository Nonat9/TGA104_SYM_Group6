package com.group6.tibame104.store.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group6.tibame104.member.model.MemberVO;
import com.group6.tibame104.store.model.StoreVO;
import com.group6.tibame104.store.service.StoreService;

@Controller
@RequestMapping("/store/storeServlet")
public class StoreController{
	
	@Autowired
	private StoreService storeSvc;
	
	@PostMapping("/update")
	public String getOneForDisplay(Model model, HttpSession session,
			@RequestParam("storeName") String storeName,@RequestParam("storeDelBank") String storeDelBank,
			@RequestParam("storeBankAccount") String storeBankAccount,@RequestParam("storeAddress") String storeAddress,
			@RequestParam("phoneNumber") String phoneNumber,@RequestParam("taxID") String taxID) {
		/* 錯誤處理 */
		Map<String, String> errorMsgs = new HashMap<String, String>();
		model.addAttribute("errorMsgs", errorMsgs);

		/* 1. 請求參數的格式整理 */
		
		Integer memberID = null;
		try {
			MemberVO attribute = (MemberVO)session.getAttribute("memVO");
			memberID = attribute.getMemberID();
		} catch (NumberFormatException e) {
			errorMsgs.put("memberID","memberID不正確");
		}

		String Reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
		if (storeName == null || storeName.trim().length() == 0) {
			errorMsgs.put("storeName","商店名稱: 請勿空白");
		} else if (!storeName.trim().matches(Reg)) {
			errorMsgs.put("storeName","商店名稱: 只能是中 英字母 數字 _ , 且長度介於2-10之間");
		}

		if (storeDelBank == null || storeDelBank.trim().length() == 0) {
			errorMsgs.put("storeDelBank","銀行代碼 請勿空白");
		}

		if (storeBankAccount == null || storeBankAccount.trim().length() == 0) {
			errorMsgs.put("storeBankAccount","銀行帳號 請勿空白");
		}

		if (storeAddress == null || storeAddress.trim().length() == 0) {
			errorMsgs.put("storeAddress","商店地址: 請勿空白");
		}

		if (phoneNumber == null || phoneNumber.trim().length() == 0) {
			errorMsgs.put("phoneNumber","電話號碼: 請勿空白");
		}
		
		if (taxID == null || taxID.trim().length() == 0) {
			errorMsgs.put("taxID","稅務代號: 請勿空白");
		}

		/* VO */
		StoreVO storeVO = (StoreVO)session.getAttribute("storeVO2");
		storeVO.setMemberID(memberID);
		storeVO.setStoreName(storeName);
		storeVO.setStoreDelBankCode(storeDelBank);
		storeVO.setStoreBankAccount(storeBankAccount);
		storeVO.setStoreAddress(storeAddress);
		storeVO.setPhoneNumber(phoneNumber);
		storeVO.setTaxID(taxID);

		/*
		 * 如果報錯 轉去 Error 頁面
		 */
		if (!errorMsgs.isEmpty()) {
			model.addAttribute("storeVO", storeVO); 
			return "front-end/store/Error";
		}

		/***************************
		 * 2.修改資料
		 ***************************************/
		storeSvc.update(storeVO);


		session.setAttribute("storeVO2", storeVO);
		return "front-end/store/myStore";

	}

}
