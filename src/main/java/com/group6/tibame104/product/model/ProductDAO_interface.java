package com.group6.tibame104.product.model;

import java.util.List;
import java.util.Map;

public interface ProductDAO_interface {
	/*
	 * 前台
	 * 1.插入商品
	 * 2.修改商品
	 * 3.刪除商品(應該是改狀態?)
	 * 4.List 1
	 * 5.List All
	 * 6.List + 條件(前端?)
	 * 
	 * 
	 * 後台
	 * 
	 * 1.插入商品
	 * 2.修改商品
	 * 3.刪除商品(應該是改狀態?)
	 * 4.List 1
	 * 5.List All
	 * 6.List + 條件	 
	 * 
	 * */
	
	// 1.商品 新增
	public void insert(ProductVO productVO);

	// 2.商品 修改
	public void update(ProductVO productVO);

	// 3.根據商品ID找到商品
	public ProductVO findByPrimaryKey(Integer productID);

	// 4.無條件搜尋商品
	public List<ProductVO> getAll();
	
	// 5.根據商品名稱搜尋商品
	public List<ProductVO> getAll(String productName);
	
	// 6.尋找當前最大的商品ID
	public Integer findMaxID();
	
	// 7.多條件 根據form表單內容查詢商品
	public List<ProductVO> getAllByCond(Map<String, String> queryString);
	
	// 8.根據商品ID上架
	public void putOn(Integer productID);

	// 9.根據商品ID下架
	public void putOff(Integer productID);
	
}
