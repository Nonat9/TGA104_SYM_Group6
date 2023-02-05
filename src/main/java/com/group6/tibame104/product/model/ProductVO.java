package com.group6.tibame104.product.model;

import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "product")
public class ProductVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productID")
	private Integer productID;
	@Column(name = "storeID")
	private Integer storeID;
	@NotNull(message="請輸入正確的商品次分類ID")
	@Column(name = "productSecID")
	private Integer productSecID;
	@NotBlank(message = "請輸入正確的商品名稱")
    @Length(message = "名稱要介於{min}-{max}之間", min= 2,max = 30)
	@Column(name = "productName")
	private String productName;
	@NotNull(message="請輸入正確的商品數量")
	@Column(name = "productStock")
	private Integer productStock;
	@NotNull(message="請輸入正確的商品價格")
	@Column(name = "productPrice")
	private Integer productPrice;
	@NotBlank(message = "請輸入正確的商品描述")
	@Column(name = "productDesc")
	private String productDesc;
	@NotBlank(message = "請輸入正確的商品來源地")
	@Column(name = "source")
	private String source;
	@Column(name = "productImg", columnDefinition = "longblob")
	private byte[] productImg;
	@Column(name = "productImg2", columnDefinition = "longblob")
	private byte[] productImg2;
	@Column(name = "productImg3", columnDefinition = "longblob")
	private byte[] productImg3;
	@Column(name = "insertTime", columnDefinition = "datetime")
	private Timestamp insertTime;
	@Column(name = "productStatus",columnDefinition="int")
	private Boolean productStatus;
	@Column(name = "commentTotal")
	private Integer commentTotal;
	@Column(name = "commentAvgStar")
	private Double commentAvgStar;
	
	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getStoreID() {
		return storeID;
	}

	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}

	public Integer getProductSecID() {
		return productSecID;
	}

	public void setProductSecID(Integer productSecID) {
		this.productSecID = productSecID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductStock() {
		return productStock;
	}

	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public byte[] getProductImg() {
		return productImg;
	}

	public void setProductImg(byte[] productImg) {
		this.productImg = productImg;
	}

	public byte[] getProductImg2() {
		return productImg2;
	}

	public void setProductImg2(byte[] productImg2) {
		this.productImg2 = productImg2;
	}

	public byte[] getProductImg3() {
		return productImg3;
	}

	public void setProductImg3(byte[] productImg3) {
		this.productImg3 = productImg3;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Boolean getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Boolean productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getCommentTotal() {
		return commentTotal;
	}

	public void setCommentTotal(Integer commentTotal) {
		this.commentTotal = commentTotal;
	}

	public Double getCommentAvgStar() {
		return commentAvgStar;
	}

	public void setCommentAvgStar(Double commentAvgStar) {
		this.commentAvgStar = commentAvgStar;
	}

	@Override
	public String toString() {
		return "ProductVO [productID=" + productID + ", storeID=" + storeID + ", productSecID=" + productSecID
				+ ", productName=" + productName + ", productStock=" + productStock + ", productPrice=" + productPrice
				+ ", productDesc=" + productDesc + ", source=" + source + ", productImg=" + Arrays.toString(productImg)
				+ ", productImg2=" + Arrays.toString(productImg2) + ", productImg3=" + Arrays.toString(productImg3)
				+ ", insertTime=" + insertTime + ", productStatus=" + productStatus + ", commentTotal=" + commentTotal
				+ ", commentAvgStar=" + commentAvgStar + "]";
	}

	
}
