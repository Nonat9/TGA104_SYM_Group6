package com.group6.tibame104.store.service;

import java.util.List;

import com.group6.tibame104.store.model.StoreVO;

public interface StoreService_interface {

	public void update(StoreVO storeVO);

	public StoreVO findByPrimaryKey(Integer storeVO);
	
	public void insert(StoreVO storeVO);
	
	public void pass(Integer memberID);

	public List<StoreVO> findAllByAudit0();

	public Integer findMemberID(Integer storeID);
	
	public byte[] findPic(Integer storeID);
}
