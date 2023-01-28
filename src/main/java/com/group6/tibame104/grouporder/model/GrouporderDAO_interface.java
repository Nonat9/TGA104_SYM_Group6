package com.group6.tibame104.grouporder.model;

import java.util.List;


public interface GrouporderDAO_interface {
	
	public void insert(GrouporderVO grouporderVO);

	public void update(GrouporderVO grouporderVO);

	public void delete(Integer grouporderID);

	public GrouporderVO findByPrimaryKey(Integer groupBuyOrderID);

	public List<GrouporderVO> getAll();
	
}
