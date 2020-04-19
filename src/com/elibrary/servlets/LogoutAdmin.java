package com.elibrary.servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elibrary.dao.AdminDao;
@WebServlet("/LogoutAdmin")
public class LogoutAdmin extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDao adminDao=new AdminDao();
		HttpSession session=request.getSession();
		int loggedInStatus;
		String adminemailsession = (String) session.getAttribute("adminemail");
		loggedInStatus=adminDao.checkLoggedInStatus(adminemailsession);
		if(loggedInStatus == 1) {
		adminDao.setLoggedInStatus(adminemailsession, 0);
		request.getSession().invalidate();
		response.sendRedirect("index.html");
		
	}else {
		response.sendRedirect("index.html");
	}
}}
