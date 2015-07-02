package com.leo.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leo.dao.ISessionStatus;
import com.leo.dao.NoStatus;
import com.leo.dao.ShopService;

@WebServlet("/OracleTestServlet")
public class OracleTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB(mappedName="SessionStatus")
	private ISessionStatus iSessionStatus;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print("start");
		iSessionStatus.addData();
		response.getWriter().print("end....");
	}

}
