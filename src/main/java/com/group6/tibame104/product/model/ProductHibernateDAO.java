package com.group6.tibame104.product.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ProductHibernateDAO implements ProductDAO_interface {

	@PersistenceContext
	private Session session;

	@Override
	public void insert(ProductVO productVO) {
		productVO.setInsertTime(new Timestamp(System.currentTimeMillis()));
		productVO.setCommentTotal(0);
		productVO.setCommentAvgStar(0.0);
		session.persist(productVO);
	}

	@Override
	public void update(ProductVO productVO) {

		final String hql = "UPDATE ProductVO"
				+ " set productSecID = :productSecID, productName = :productName, productStock = :productStock,"
				+ "productPrice = :productPrice, productDesc = :productDesc, source = :source,"
				+ "productImg = coalesce(:productImg, productImg), productImg2 = coalesce(:productImg2, productImg2), productImg3 = coalesce(:productImg3, productImg3)"
				+ " where productID = :productID";

		session.createQuery(hql).setParameter("productSecID", productVO.getProductSecID())
				.setParameter("productName", productVO.getProductName())
				.setParameter("productStock", productVO.getProductStock())
				.setParameter("productPrice", productVO.getProductPrice())
				.setParameter("productDesc", productVO.getProductDesc()).setParameter("source", productVO.getSource())
				.setParameter("productImg", productVO.getProductImg())
				.setParameter("productImg2", productVO.getProductImg2())
				.setParameter("productImg3", productVO.getProductImg3())
				.setParameter("productID", productVO.getProductID()).executeUpdate();

	}

	@Override
	public ProductVO findByPrimaryKey(Integer ProductID) {
		return session.get(ProductVO.class, ProductID);
	}

	@Override
	public List<ProductVO> getAll() {
		final String hql = "FROM ProductVO ORDER BY ProductID";
		return session.createQuery(hql, ProductVO.class).list();
	}

	@Override
	public List<ProductVO> getAll(String productName) {
		final String hql = "FROM ProductVO WHERE productName LIKE :productName ORDER BY ProductID";
		return session.createQuery(hql, ProductVO.class).setParameter("productName", "%" + productName + "%").list();
	}

	@Override
	public Integer findMaxID() {
		final String hql = "select max(p.productID) from ProductVO p";
		return (Integer) session.createQuery(hql).uniqueResult();
	}

	// 採用Criteria 多條件查詢
	@Override
	public List<ProductVO> getAllByCond(Map<String, String> queryString) {

		// 建立Criteria
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		// select ProductVO
		CriteriaQuery<ProductVO> criteriaQuery = criteriaBuilder.createQuery(ProductVO.class);
		// from ProductVO
		Root<ProductVO> root = criteriaQuery.from(ProductVO.class);

		/*
		 * 使用List<Predicate> 將  querstString其中的內容組成 SQL的where條件
		 * Map<String, String> key值為參數名稱 value為參數值
		 * 當前內容如下
		 * 		where storeID       = storeID的值
		 * 	    and   productID     = productID的值
		 * 		and	  productSecID  = productSecID的值
		 * 		and	  productStatus = productStatus的值
		 * 
		*/ 
		List<Predicate> predicateList = new ArrayList<Predicate>();
		for (String key : queryString.keySet()) {
			String value = queryString.get(key);

			if ("storeID".equals(key)) {
				predicateList.add(criteriaBuilder.equal(root.get("storeID"), value));
			} else if ("productID".equals(key)) {
				predicateList.add(criteriaBuilder.equal(root.get("productID"), value));
			} else if ("productSecID".equals(key)) {
				predicateList.add(criteriaBuilder.equal(root.get("productSecID"), value));
			} else if ("productStatus".equals(key)) {
				predicateList.add(criteriaBuilder.equal(root.get("productStatus"), Boolean.valueOf(value)));
			}
		}

		// 把條件塞入where
		if (predicateList.size() > 0) {
			Predicate[] predicates = new Predicate[predicateList.size()];
			for (int i = 0; i < predicateList.size(); i++) {
				predicates[i] = predicateList.get(i);
			}
			criteriaQuery.where(predicates);
		}

		TypedQuery<ProductVO> typedQuery = session.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public void putOn(Integer productID) {
		final String hql = "UPDATE ProductVO " + "set productStatus = 1" + "where productID = :productID";
		session.createQuery(hql).setParameter("productID", productID).executeUpdate();
	}

	@Override
	public void putOff(Integer productID) {
		final String hql = "UPDATE ProductVO " + "set productStatus = 0" + "where productID = :productID";
		session.createQuery(hql).setParameter("productID", productID).executeUpdate();
	}

}