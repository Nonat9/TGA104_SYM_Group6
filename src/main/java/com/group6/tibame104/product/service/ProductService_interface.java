package com.group6.tibame104.product.service;

import java.util.List;
import java.util.Map;

import com.group6.tibame104.product.model.ProductVO;

public interface ProductService_interface {

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
	public List<ProductVO> getAllByCondFront(Map<String, String> queryString);
	
	// 8.根據商品ID上架
	public void putOn(Integer productID);
	
	// 9.根據商品ID下架
	public void putOff(Integer productID);
}
