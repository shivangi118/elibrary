package com.elibrary.servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elibrary.dao.LibrarianDao;
@WebServlet("/LogoutLibrarian")
public class LogoutLibrarian extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		int loggedInStatus;
		String librarianemailsession = (String) session.getAttribute("librarianemail");
		loggedInStatus=LibrarianDao.checkLoggedInStatus(librarianemailsession);
		if(loggedInStatus == 1) {
		LibrarianDao.setLoggedInStatus(librarianemailsession, 0);
		request.getSession().invalidate();
		response.sendRedirect("index.html");
	}else {
		response.sendRedirect("index.html");
	}
}
}