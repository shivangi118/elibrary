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

@WebServlet("/AddLibrarian")
public class AddLibrarian extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		out.println("<title>Add Librarian</title>");
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
		
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String smobile=request.getParameter("mobile");
		String encryptedPassword;
		int status;
		long mobile=Long.parseLong(smobile);
		encryptedPassword=EncryptDecrypt.encryptPassword(password);
		LibrarianBean bean=new LibrarianBean(name, email, encryptedPassword, mobile);
		status=LibrarianDao.save(bean);
		adminsession = (String) session.getAttribute("admin");
		if(status == 1 && adminsession == "true") {
			out.print("<h4>Librarian added successfully</h4>");
			request.getRequestDispatcher("addlibrarianform.html").include(request, response);
		} else if(adminsession != "true") {
			out.print("<h4>Please login and try again later!!</h4>");
			request.getRequestDispatcher("addlibrarianform.html").include(request, response);
			
		}
		else {
			out.print("<h4>Email already exists! Please try again!</h4>");
			request.getRequestDispatcher("addlibrarianform.html").include(request, response);
		}
		out.println("</div>");
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}else {
		response.sendRedirect("index.html");
	}

}
}
