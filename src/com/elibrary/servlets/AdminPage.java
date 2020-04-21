package com.elibrary.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elibrary.dao.AdminDao;
@WebServlet("/Adminhomepage")
public class AdminPage extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session=request.getSession();
		String adminsession = (String) session.getAttribute("adminemail");
		int loggedInStatus;
		AdminDao adminDao= new AdminDao();
		loggedInStatus=adminDao.checkLoggedInStatus(adminsession);
		if(loggedInStatus == 1) {
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Admin home page Form</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("<style>\r\n" + 
				"body {\r\n" + 
				"  background-color: #E6E6FA;\r\n" + 
				"}\r\n" + 
				"</style>");
		out.println("</head>");
		out.println("<body>");
		
		request.getRequestDispatcher("navadmin.html").include(request, response);
		out.println("<div class='container'>");
		request.getRequestDispatcher("admincarousel.html").include(request, response);
		out.println("</div>");
		
		
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}else {
		response.sendRedirect("index.html");
	}

}
}
