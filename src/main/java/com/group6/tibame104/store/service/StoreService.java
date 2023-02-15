package com.group6.tibame104.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group6.tibame104.store.model.StoreDAO_interface;
import com.group6.tibame104.store.model.StoreVO;

@Repository
public class StoreService implements StoreService_interface {

	@Autowired
	private StoreDAO_interface dao;

	@Override
	public void update(StoreVO storeVO) {
		dao.update(storeVO);
	}

	@Override
	public StoreVO findByPrimaryKey(Integer storeVO) {
		return dao.findByPrimaryKey(storeVO);
	}

	@Override
	public void insert(StoreVO storeVO) {
		dao.insert(storeVO);
	}

	@Override
	public void pass(Integer memberID) {
		dao.pass(memberID);
	}

	@Override
	public List<StoreVO> findAllByAudit0() {
		return dao.findAllByAudit0();
	}

	@Override
	public Integer findMemberID(Integer storeID) {
		return dao.findMemberID(storeID);
	}

	@Override
	public byte[] findPic(Integer storeID) {
		return dao.findPic(storeID);
	}

}