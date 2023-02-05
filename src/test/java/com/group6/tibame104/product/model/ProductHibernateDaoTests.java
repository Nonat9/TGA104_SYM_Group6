package com.group6.tibame104.product.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductHibernateDaoTests {

	@Resource(name = "productHibernateDAO")
	private ProductDAO_interface dao;

	@Test
	public void test() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("storeID", "1");
		map.put("productStatus", "true");
		List<ProductVO> allByCond = dao.getAllByCond(map);

		long c1 = System.currentTimeMillis();

		for (ProductVO s : allByCond) {
			System.out.println(s);
		}
		System.out.println("時間 = " + (System.currentTimeMillis() - c1));

		Stream<ProductVO> stream = allByCond.stream();
		long c2 = System.currentTimeMillis();
		stream.forEach(System.out::println);
		System.out.println("lambda時間 = " + (System.currentTimeMillis() - c2));

	}

}
