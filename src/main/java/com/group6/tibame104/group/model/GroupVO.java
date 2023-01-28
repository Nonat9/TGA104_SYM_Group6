package com.group6.tibame104.group.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Getter
@Setter
@ToString
@Table(name="groupBuying")
public class GroupVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="groupBuyID")
	private Integer groupBuyID;
	@Column(name="groupBuyProductID")
	private Integer groupBuyProductID;
	@Column(name="administratorID")
	private Integer administratorID;
	@Column(name="groupBuyProductOrderTotal")
	private Integer groupBuyProductOrderTotal;
	@Column(name="groupBuyingState")
	private Boolean groupBuyingState;
	@Column(name="groupBuyingOnLoadDate")
	private Timestamp groupBuyingOnLoadDate;
	@Column(name="groupBuyingOffLoadDate")
	private Timestamp groupBuyingOffLoadDate;
	@Column(name="updateTime")
	private Timestamp updateTime;

}
