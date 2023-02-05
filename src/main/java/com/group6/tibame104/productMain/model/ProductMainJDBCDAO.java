package com.group6.tibame104.productMain.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductMainJDBCDAO implements ProductMainDAO_interface {

	@Autowired
	private DataSource dataSource;

	private static final String GET_ALL_STMT = "SELECT productMainID , productMainName FROM productMain order by productMainID";

	@Override
	public List<ProductMainVO> getAll() {

		try (Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT)) {

			try (ResultSet rs = pstmt.executeQuery()) {
				List<ProductMainVO> list = new ArrayList<ProductMainVO>();

				while (rs.next()) {
					ProductMainVO productMainVO = new ProductMainVO();
					productMainVO.setProductMainID(rs.getInt("productMainID"));
					productMainVO.setProductMainName(rs.getString("productMainName"));
					list.add(productMainVO);
				}

				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}