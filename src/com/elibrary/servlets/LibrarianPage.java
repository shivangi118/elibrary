package com.elibrary.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/LibrarianHomePage")
public class LibrarianPage extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Librarian home page Form</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("<style>\r\n" + 
				"body {\r\n" + 
				"  background-color: #E6E6FA;\r\n" + 
				"}\r\n" + 
				"</style>");
		out.println("</head>");
		out.println("<body>");
		
		request.getRequestDispatcher("navlibrarian.html").include(request, response);
		out.println("<div class='container'>");
		request.getRequestDispatcher("librariancarousel.html").include(request, response);
		out.println("</div>");
		
		
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

}
