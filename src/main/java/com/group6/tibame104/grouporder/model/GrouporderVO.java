package com.group6.tibame104.grouporder.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Table(name="groupBuyOrder")
public class GrouporderVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="groupBuyOrderID")
	private Integer groupBuyOrderID;
	@Column(name="groupBuyID")
	private Integer groupBuyID;
	@Column(name="memberID")
	private Integer memberID;
	@Column(name="groupBuyProductID")
	private Integer groupBuyProductID;
	@Column(name="groupBuyQuantity")
	private Integer groupBuyQuantity;
	@Column(name="groupBuyTotal")
	private Integer groupBuyTotal;
	@Column(name="orderTime")
	private Timestamp orderTime;
	@Column(name="paymentTerm")
	private String paymentTerm;
	@Column(name="paymentState", columnDefinition = "bit")
	private Integer paymentState;
	@Column(name="giftVoucher")
	private Integer giftVoucher;
	@Column(name="contactNumber")
	private String contactNumber;
	@Column(name="shippingLocation")
	private String shippingLocation;

}
