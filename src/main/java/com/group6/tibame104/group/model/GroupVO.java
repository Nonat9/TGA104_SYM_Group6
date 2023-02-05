package com.group6.tibame104.group.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@ToString
@Table(name = "groupBuying")
public class GroupVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groupBuyID")
	private Integer groupBuyID;
	@Column(name = "groupBuyProductID")
	private Integer groupBuyProductID;
	@Column(name = "administratorID")
	private Integer administratorID;
	@Column(name = "groupBuyProductOrderTotal")
	private Integer groupBuyProductOrderTotal;
	@Column(name = "groupBuyingState")
	private Boolean groupBuyingState;
	@Column(name = "groupBuyingOnLoadDate")
	private Timestamp groupBuyingOnLoadDate;
	@Column(name = "groupBuyingOffLoadDate")
	private Timestamp groupBuyingOffLoadDate;
	@Column(name = "updateTime")
	private Timestamp updateTime;

	public Integer getGroupBuyID() {
		return groupBuyID;
	}

	public void setGroupBuyID(Integer groupBuyID) {
		this.groupBuyID = groupBuyID;
	}

	public Integer getGroupBuyProductID() {
		return groupBuyProductID;
	}

	public void setGroupBuyProductID(Integer groupBuyProductID) {
		this.groupBuyProductID = groupBuyProductID;
	}

	public Integer getAdministratorID() {
		return administratorID;
	}

	public void setAdministratorID(Integer administratorID) {
		this.administratorID = administratorID;
	}

	public Integer getGroupBuyProductOrderTotal() {
		return groupBuyProductOrderTotal;
	}

	public void setGroupBuyProductOrderTotal(Integer groupBuyProductOrderTotal) {
		this.groupBuyProductOrderTotal = groupBuyProductOrderTotal;
	}

	public Boolean getGroupBuyingState() {
		return groupBuyingState;
	}

	public void setGroupBuyingState(Boolean groupBuyingState) {
		this.groupBuyingState = groupBuyingState;
	}

	public Timestamp getGroupBuyingOnLoadDate() {
		return groupBuyingOnLoadDate;
	}

	public void setGroupBuyingOnLoadDate(Timestamp groupBuyingOnLoadDate) {
		this.groupBuyingOnLoadDate = groupBuyingOnLoadDate;
	}

	public Timestamp getGroupBuyingOffLoadDate() {
		return groupBuyingOffLoadDate;
	}

	public void setGroupBuyingOffLoadDate(Timestamp groupBuyingOffLoadDate) {
		this.groupBuyingOffLoadDate = groupBuyingOffLoadDate;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
