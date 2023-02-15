package com.group6.tibame104.store.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StoreJDBCDAO implements StoreDAO_interface {

	@Autowired
	private DataSource dataSource;

	private static final String GET_ONE_STMT = "SELECT storeID,storeName,storeDelBankCode,storeBankAccount,storeAddress,phoneNumber,createDate"
			+ ",taxID,storeAuditStatus FROM store where memberID = ?";
	private static final String UPDATE = "UPDATE store set memberID=?, storeName=?, storeDelBankCode=?, storeBankAccount=?, storeAddress=?, phoneNumber=?,"
			+ "updateDate=now(),taxID=? where storeID = ?";
	private static final String INSERT_STMT = "INSERT INTO store (memberID,storeName,storeDelBankCode,storeBankAccount,storeAddress,phoneNumber,createDate,updateDate,taxID,storeAuditStatus) "
			+ "VALUES (?, ?, ?, ?, ?, ?, now(), now(), ?, 0)";
	private static final String UPDATE_STATUS = "UPDATE store set storeAuditStatus =1 where memberID = ?";

	private static final String GET_All_STMT_BY_AUDI_0 = "SELECT memberID,storeName,storeAddress,phoneNumber,createDate,updateDate,taxID,storeAuditStatus FROM store where storeAuditStatus = 0";

	private static final String GET_STORE_MEMBERID = "SELECT memberID FROM store where storeID = ?";

	private static final String GET_PIC = "SELECT storePhoto FROM store WHERE storeID = ?";

	@Override
	public void update(StoreVO storeVO) {

		try (Connection con = dataSource.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE)) {

			pstmt.setInt(1, storeVO.getMemberID());
			pstmt.setString(2, storeVO.getStoreName());
			pstmt.setString(3, storeVO.getStoreDelBankCode());
			pstmt.setString(4, storeVO.getStoreBankAccount());
			pstmt.setString(5, storeVO.getStoreAddress());
			pstmt.setString(6, storeVO.getPhoneNumber());
			pstmt.setString(7, storeVO.getTaxID());
			pstmt.setInt(8, storeVO.getMemberID());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public StoreVO findByPrimaryKey(Integer memberID) {

		try (Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT)) {

			pstmt.setInt(1, memberID);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {

					StoreVO storeVO = new StoreVO();
					storeVO.setStoreID(rs.getInt("storeID"));
					storeVO.setStoreName(rs.getString("storeName"));
					storeVO.setStoreDelBankCode(rs.getString("storeDelBankCode"));
					storeVO.setStoreBankAccount(rs.getString("storeBankAccount"));
					storeVO.setStoreAddress(rs.getString("storeAddress"));
					storeVO.setPhoneNumber(rs.getString("phoneNumber"));
					storeVO.setCreateDate(rs.getTimestamp("createDate"));
					storeVO.setTaxID(rs.getString("taxID"));
					storeVO.setStoreAuditStatus(rs.getInt("storeAuditStatus"));
					return storeVO;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(StoreVO storeVO) {

		try (Connection con = dataSource.getConnection(); PreparedStatement pstmt = con.prepareStatement(INSERT_STMT)) {

			pstmt.setInt(1, storeVO.getMemberID());
			pstmt.setString(2, storeVO.getStoreName());
			pstmt.setString(3, storeVO.getStoreDelBankCode());
			pstmt.setString(4, storeVO.getStoreBankAccount());
			pstmt.setString(5, storeVO.getStoreAddress());
			pstmt.setString(6, storeVO.getPhoneNumber());
			pstmt.setString(7, storeVO.getTaxID());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pass(Integer memberID) {

		try (Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE_STATUS)) {

			pstmt.setInt(1, memberID);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<StoreVO> findAllByAudit0() {

		try (Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(GET_All_STMT_BY_AUDI_0)) {

			try (ResultSet rs = pstmt.executeQuery()) {
				List<StoreVO> all = new ArrayList<StoreVO>();
				while (rs.next()) {
					StoreVO storeVO = new StoreVO();
					storeVO.setMemberID(rs.getInt("memberID"));
					storeVO.setStoreName(rs.getString("storeName"));
					storeVO.setStoreAddress(rs.getString("storeAddress"));
					storeVO.setPhoneNumber(rs.getString("phoneNumber"));
					storeVO.setCreateDate(rs.getTimestamp("createDate"));
					storeVO.setUpdateDate(rs.getTimestamp("updateDate"));
					storeVO.setTaxID(rs.getString("taxID"));
					storeVO.setStoreAuditStatus(rs.getInt("storeAuditStatus"));
					all.add(storeVO);
				}
				return all;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Integer findMemberID(Integer storeID) {
		try (Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(GET_STORE_MEMBERID)) {

			pstmt.setInt(1, storeID);
			try (ResultSet rs = pstmt.executeQuery()) {
				Integer memberID = null;
				while (rs.next()) {
					memberID = rs.getInt("memberID");
				}
				return memberID;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] findPic(Integer storeID) {
		try (Connection con = dataSource.getConnection(); PreparedStatement pstmt = con.prepareStatement(GET_PIC)) {

			pstmt.setInt(1, storeID);
			try (ResultSet rs = pstmt.executeQuery()) {
				byte[] pic = null;
				while (rs.next()) {
					pic = rs.getBytes("storePhoto");
				}
				return pic;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}