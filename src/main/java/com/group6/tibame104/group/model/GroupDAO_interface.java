package com.group6.tibame104.group.model;

import java.util.List;

public interface GroupDAO_interface {
	public void insert(GroupVO groupVO);

	 public void update(GroupVO groupVO);
	 
	 public void updateGroupQua(GroupVO groupVO);

	 public void delete(Integer grouporderID);

	 public GroupVO findByPrimaryKey(Integer groupBuyID);

	 public List<GroupVO> getAll();
	 public List<GroupVO> getAllDesc();
	 public List<GroupVO> orderBy();
	 public List<Object> getJoinAll();


}
