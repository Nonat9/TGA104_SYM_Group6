package com.group6.tibame104.productSec.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductSecJDBCDAO implements ProductSecDAO_interface {

	@Autowired
	private DataSource dataSource;

	private static final String GET_ALL_STMT = "SELECT productSecID , productMainID, productSecName FROM productSec order by productSecID";

	@Override
	public List<ProductSecVO> getAll() {

		try (Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT)) {

			try (ResultSet rs = pstmt.executeQuery()) {
				List<ProductSecVO> list = new ArrayList<ProductSecVO>();

				while (rs.next()) {
					ProductSecVO productSecVO = new ProductSecVO();
					productSecVO.setProductSecID(rs.getInt("productSecID"));
					productSecVO.setProductMainID(rs.getInt("productMainID"));
					productSecVO.setProductSecName(rs.getString("productSecName"));
					list.add(productSecVO);
				}

				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}