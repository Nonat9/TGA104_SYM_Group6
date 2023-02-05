package com.group6.tibame104.productSec.Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group6.tibame104.productSec.model.ProductSecDAO_interface;

@Component
public class ProductSecListener implements ServletContextListener {

	@Autowired
	private ProductSecDAO_interface dao;

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("ProductSec", dao.getAll());
	}

}
