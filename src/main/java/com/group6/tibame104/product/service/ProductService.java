package com.group6.tibame104.product.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group6.tibame104.product.model.ProductDAO_interface;
import com.group6.tibame104.product.model.ProductVO;

@Service
@Transactional
public class ProductService implements ProductService_interface {
	@Resource(name = "productHibernateDAO")
	private ProductDAO_interface dao;

	@Override
	public void insert(ProductVO productVO) {
		dao.insert(productVO);
	}

	@Override
	public void update(ProductVO productVO) {
		dao.update(productVO);
	}

	@Override
	public ProductVO findByPrimaryKey(Integer productID) {
		return dao.findByPrimaryKey(productID);
	}

	@Override
	public List<ProductVO> getAll() {
		return dao.getAll();
	}

	@Override
	public List<ProductVO> getAll(String productName) {
		return dao.getAll(productName);
	}

	@Override
	public Integer findMaxID() {
		return dao.findMaxID();
	}

	@Override
	public List<ProductVO> getAllByCondFront(Map<String, String> queryString) {
		// TODO Auto-generated method stub
		return dao.getAllByCond(queryString);
	}
	
	@Override
	public void putOn(Integer productID) {
		dao.putOn(productID);
	}

	@Override
	public void putOff(Integer productID) {
		dao.putOff(productID);
	}

}
