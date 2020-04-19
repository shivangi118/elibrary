package com.elibrary.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elibrary.beans.BookBean;
import com.elibrary.beans.IssueBookBean;
import com.elibrary.dao.BookDao;
import com.elibrary.dao.LibrarianDao;
@WebServlet("/IssueBook")
public class IssueBook extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");HttpSession session=request.getSession();
		String librariansession = (String) session.getAttribute("email");
		int loggedInStatus;
		loggedInStatus=LibrarianDao.checkLoggedInStatus(librariansession);
		if(loggedInStatus == 1) {
		PrintWriter out=response.getWriter();
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Add Book Form</title>");
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
		String callno=request.getParameter("callno");
		String studentid=request.getParameter("studentid");
		String studentname=request.getParameter("studentname");
		String sstudentmobile=request.getParameter("studentmobile");
		long studentmobile=Long.parseLong(sstudentmobile);
		
		IssueBookBean bean=new IssueBookBean(callno,studentid,studentname,studentmobile);
		int i=BookDao.issueBook(bean);
		if(i>0){
			out.println("<h3>Book issued successfully</h3>");
		}else{
			out.println("<h3>Sorry, unable to issue book.</h3><p>We may have shortage of books. Kindly visit later.</p>");
		}
		request.getRequestDispatcher("issuebookform.html").include(request, response);
		out.println("</div>");
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}else {
		response.sendRedirect("index.html");
	}

}
}