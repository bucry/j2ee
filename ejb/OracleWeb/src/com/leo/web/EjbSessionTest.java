package com.leo.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leo.dao.NoStatus;
import com.leo.dao.ShopService;

@WebServlet("/EjbSessionTest")
public class EjbSessionTest extends HttpServlet {
	

	@EJB(mappedName="NoStatusImpl")
	private NoStatus noStatus;
	
	@EJB(mappedName="ShopService")
	private ShopService shopService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		for (int i = 0; i < 5; i++) {
			response.getWriter().print(noStatus.sayHello("bfc"));
		}
		
		for (int i = 0; i < 5; i++) {
			shopService.addItem("ss");
		}
	}

}
