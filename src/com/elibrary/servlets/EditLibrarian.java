package com.elibrary.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elibrary.beans.LibrarianBean;
import com.elibrary.dao.AdminDao;
import com.elibrary.dao.LibrarianDao;
import com.elibrary.util.EncryptDecrypt;
@WebServlet("/EditLibrarian")
public class EditLibrarian extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		PrintWriter out=response.getWriter();
		AdminDao adminDao=new AdminDao();
		String encryptedPassword;
		String adminsession = (String) session.getAttribute("adminemail");
		int loggedInStatus=adminDao.checkLoggedInStatus(adminsession);
		if(loggedInStatus == 1) {
		String sid=request.getParameter("id");
		int id=Integer.parseInt(sid);
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String smobile=request.getParameter("mobile");
		long mobile=Long.parseLong(smobile);
		encryptedPassword=EncryptDecrypt.encryptPassword(password);
		LibrarianBean bean=new LibrarianBean(id,name, email, encryptedPassword, mobile);
		LibrarianDao.update(bean);
		response.sendRedirect("ViewLibrarian");
		}else {
			out.println("<h3>Sorry cannot edit. Please login and try again<h3>");
			response.sendRedirect("index.html");
		}
		
	}

}
