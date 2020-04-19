package com.elibrary.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elibrary.dao.BookDao;
import com.elibrary.dao.LibrarianDao;
@WebServlet("/DeleteBook")
public class DeleteBook extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String librariansession = (String) session.getAttribute("email");
		int loggedInStatus=LibrarianDao.checkLoggedInStatus(librariansession);
		if(loggedInStatus == 1) {
		BookDao.delete(request.getParameter("callno"));
		response.sendRedirect("ViewBook");
		}else {
			PrintWriter out=response.getWriter();
			out.println("<h3>Sorry unable to delete book.<h3>");
			response.sendRedirect("index.html");
		}
		
	}
}
