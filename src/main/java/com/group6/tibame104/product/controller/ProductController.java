package com.group6.tibame104.product.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.group6.tibame104.product.model.ProductVO;
import com.group6.tibame104.product.service.ProductService;
import com.group6.tibame104.store.model.StoreVO;

@Controller
@RequestMapping("/product/productServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ProductController {
	@Autowired
	private ProductService productSvc;

	@PostMapping("/getOne_For_Display")
	public String getOneForDisplay(Model model, @RequestParam("productID") String str) {
		/* 錯誤處理 */
		Map<String, String> errorMsgs = new HashMap<String, String>();
		model.addAttribute("errorMsgs", errorMsgs);

		if (str == null || (str.trim()).length() == 0) {
			errorMsgs.put("productID", "請輸入正常的數字");
		}

		Integer productID = null;
		try {
			productID = Integer.valueOf(str);
		} catch (Exception e) {
			errorMsgs.put("productID", "請輸入正常的數字");
		}

		ProductVO productVO = productSvc.findByPrimaryKey(productID);
		if (productVO == null) {
			errorMsgs.put("productID", "查無資料");
		}

		/*
		 * 如果報錯 轉去 Error 頁面
		 */
		if (!errorMsgs.isEmpty()) {
			return "front-end/store/Error";
		}

		model.addAttribute("productVO", productVO);
		return "front-end/product_detail/product_detail";

	}

	@PostMapping("/insert")
	public String insert(Model model, @Valid ProductVO productVO, BindingResult bindingResult, HttpSession session,
			@RequestPart(name = "upfile1", required = false) Part productImg,
			@RequestPart(name = "upfile2", required = false) Part productImg2,
			@RequestPart(name = "upfile3", required = false) Part productImg3) throws IOException {

		/* 錯誤處理 */
		Map<String, String> errorMsgs = new HashMap<String, String>();
		model.addAttribute("errorMsgs", errorMsgs);

		if (bindingResult != null && bindingResult.hasFieldErrors()) {
			/*
			 * 自動轉換錯誤 加入errorMsgs
			 */
			if (bindingResult.hasFieldErrors("productSecID")) {
				errorMsgs.put("productSecID", "請輸入正確的商品分類");
			}
			if (bindingResult.hasFieldErrors("productStock")) {
				errorMsgs.put("productStock", "請輸入正確的商品數量");
			}
			if (bindingResult.hasFieldErrors("productPrice")) {
				errorMsgs.put("productPrice", "請輸入正確的商品價格");
			}
			if (bindingResult.hasFieldErrors("productDesc")) {
				errorMsgs.put("productStatus", "請輸入正確的商品上下架狀態");
			}

			/*
			 * 非自動轉換失敗的錯誤 也就是我用constraint自定義的錯誤 加入errorMsgs
			 */
			for (FieldError s : bindingResult.getFieldErrors()) {
				if (!errorMsgs.keySet().contains(s.getField())) {
					errorMsgs.put(s.getField(), s.getDefaultMessage());
				}
			}
			for (String key : errorMsgs.keySet()) {
				System.out.println(errorMsgs.get(key));
			}

		}

		/* 取得賣場ID */
		StoreVO attribute = (StoreVO) session.getAttribute("storeVO2");
		productVO.setStoreID(attribute.getStoreID());

		/* 圖片 */
		productVO.setProductImg(productImg.getInputStream().readAllBytes());
		productVO.setProductImg2(productImg2.getInputStream().readAllBytes());
		productVO.setProductImg3(productImg3.getInputStream().readAllBytes());
	
		/*
		 * 如果報錯 轉去 Error 頁面
		 */
		if (!errorMsgs.isEmpty()) {
			model.addAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
			return "front-end/store/Error";
		}

		/***************************
		 * 2.新增資料
		 ***************************************/
		productSvc.insert(productVO);
		productVO.setProductID(productSvc.findMaxID());
		/*
		 * 導向 listOneProduct頁面
		 */
		model.addAttribute("productVO", productVO);
		return "front-end/store/myStore";

	}

	@PostMapping("/update")
	public String update(Model model, HttpServletRequest request, @RequestParam("productID2") String productIDStr,
			@RequestParam("productName") String productName, @RequestParam("productSec") String productSecStr,
			@RequestParam("productStock") String productStockStr, @RequestParam("productPrice") String productPriceStr,
			@RequestParam("productDesc") String productDesc, @RequestParam("source") String source,
			@RequestPart(name = "upfile1", required = false) Part productImg,
			@RequestPart(name = "upfile2", required = false) Part productImg2,
			@RequestPart(name = "upfile3", required = false) Part productImg3) throws IOException {

		Map<String,String> errorMsgs = new HashMap<String,String>();
		model.addAttribute("errorMsgs", errorMsgs);

		/* 1. 請求參數的格式整理 */
		Integer productID = null;
		try {
			productID = Integer.valueOf(productIDStr.trim());
		} catch (NumberFormatException e) {
			productID = 0;
			errorMsgs.put("productID","productID不正確");
		}

		String productNameReg = "^.{2,20}$";
		if (productName == null || productName.trim().length() == 0) {
			errorMsgs.put("productName","產品名稱: 請勿空白");
		} else if (!productName.trim().matches(productNameReg)) {
			errorMsgs.put("productName","產品名稱: 長度介於2-20之間");
		}

		Integer productSec = null;
		try {
			productSec = Integer.valueOf(productSecStr.trim());
		} catch (NumberFormatException e) {
			productSec = 0;
			errorMsgs.put("productSec","商品次分類不正確");
		}

		Integer productStock = null;
		try {
			productStock = Integer.valueOf(productStockStr.trim());
		} catch (NumberFormatException e) {
			productStock = 0;
			errorMsgs.put("productStock","庫存請填數字");
		}

		Integer productPrice = null;
		try {
			productPrice = Integer.valueOf(productPriceStr.trim());
		} catch (NumberFormatException e) {
			productPrice = 0;
			errorMsgs.put("productPrice","商品價格請填數字");
		}

		if (productDesc == null || productDesc.trim().length() == 0) {
			errorMsgs.put("productDesc","產品描述: 請勿空白");
		}

		if (source == null || source.trim().length() == 0) {
			errorMsgs.put("source","產地: 請勿空白");
		}

		/* 放入Bean */
		ProductVO productVO = new ProductVO();
		productVO.setProductID(productID);
		productVO.setProductSecID(productSec);
		productVO.setProductName(productName);
		productVO.setProductStock(productStock);
		productVO.setProductPrice(productPrice);
		productVO.setProductDesc(productDesc);
		productVO.setSource(source);

		/* 配合SQL函數coalesce
		 * productImg的size為0 則塞productImg_=null
		 * productImg的size不為0 則productImg_讀入productImg
		 * */
		byte[] productImg_ = null;
		
		if (productImg.getSize() != 0) {
			InputStream in = productImg.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(in);
			productImg_ = new byte[bis.available()];
			bis.read(productImg_);
		}
		productVO.setProductImg(productImg_);
		
		/* 配合SQL函數coalesce
		 * productImg2的size為0 則塞productImg2_=null
		 * productImg2的size不為0 則productImg2_讀入productImg2
		 * */
		byte[] productImg2_ = null;
		
		if (productImg2.getSize() != 0) {
			InputStream in2 = productImg2.getInputStream();
			BufferedInputStream bis2 = new BufferedInputStream(in2);
			productImg2_ = new byte[bis2.available()];
			bis2.read(productImg2_);
		}
		productVO.setProductImg2(productImg2_);
		
		/* 配合SQL函數coalesce
		 * productImg3的size為0 則塞productImg3_=null
		 * productImg3的size不為0 則productImg3_讀入productImg3
		 * */
		byte[] productImg3_ = null;
		
		if (productImg3.getSize() != 0) {
			InputStream in3 = productImg3.getInputStream();
			BufferedInputStream bis3 = new BufferedInputStream(in3);
			productImg3_ = new byte[bis3.available()];
			bis3.read(productImg3_);
		}
		productVO.setProductImg3(productImg3_);
		

		/*
		 * 如果報錯 轉去 Error 頁面
		 */
		if (!errorMsgs.isEmpty()) {
			return "front-end/store/Error";
		}

		/***************************
		 * 2.修改資料
		 ***************************************/
		productSvc.update(productVO);

		/*
		 * 導向 myStore頁面
		 */
		model.addAttribute("productVO", productVO);
		return "front-end/store/myStore";

	}

}
