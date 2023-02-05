package com.group6.tibame104.productMain.Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group6.tibame104.productMain.model.ProductMainDAO_interface;

@Component
public class ProductMainListener implements ServletContextListener {

	@Autowired
	private ProductMainDAO_interface dao;
	
	
    public void contextDestroyed(ServletContextEvent sce)  { 
  
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	sce.getServletContext().setAttribute("ProductMain", dao.getAll());
    }
	
}
