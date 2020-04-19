package com.elibrary.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elibrary.dao.LibrarianDao;
import com.elibrary.util.VerifyRecaptcha;
@WebServlet("/LibrarianLogin")
public class LibrarianLogin extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Librarian Section</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("<script src=\"https://www.google.com/recaptcha/api.js\" async defer></script>");
		out.println("<style>\r\n" + 
				"body {\r\n" + 
				"  background-color: #E6E6FA;\r\n" + 
				"}\r\n" + 
				"</style>");
		out.println("</head>");
		out.println("<body>");
		boolean append = true;
		FileHandler handler = new FileHandler("default1.log", append);
	    Logger logger = Logger.getLogger("com.elibrary.servlets");
	    logger.addHandler(handler);
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println(gRecaptchaResponse);
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		System.out.println("Captcha "+ verify);
		HttpSession session=request.getSession();
		session.setAttribute("librarianemail",email);
		String password1;
		logger.info("password "+password);
		logger.entering("username", email);
		int failed_attempts,lock_status,reset_failed_attempts,reset_timestamp,freeze_account,timeinterval,frozenstatus,loggedinstatus;
		Timestamp timestamp_from_db,currentTimeStamp;
		frozenstatus=LibrarianDao.checkFrozenStatus(email);
		if (frozenstatus == 1) {
			System.out.println("Account is frozen");
			currentTimeStamp = new Timestamp(System.currentTimeMillis());
			timestamp_from_db = LibrarianDao.checkTimestamp(email);
			long milliseconds= currentTimeStamp.getTime()-timestamp_from_db.getTime();
			int seconds =(int) milliseconds /1000;
			int hours = seconds / 3600;
			int minutes = (seconds % 3600) / 60;
			seconds = (seconds % 3600) % 60;
			System.out.println("Difference: ");
			System.out.println(" Hours: " + hours);
			System.out.println(" Minutes: " + minutes);
			System.out.println(" Seconds: " + seconds);
			if(hours >=1 || minutes >=5)
					{
						System.out.println("Minutes are greater than equal to 5");
						System.out.println(hours + minutes + " hours and minutes");
						// frozenstatus == 0
						frozenstatus=LibrarianDao.resetFrozenAccount(email);
						// numberofattempts == 0
						reset_failed_attempts=LibrarianDao.resetFailedAttempts(email);
						//timestamp = null
						reset_timestamp=LibrarianDao.resetTimestamp(email);
					}
		}
		loggedinstatus=LibrarianDao.checkLoggedInStatus(email);
		if(LibrarianDao.authenticate1(email, password) && frozenstatus == 0 && loggedinstatus == 0 && verify){ 
			session.setAttribute("email",email);
			LibrarianDao.resetFailedAttempts(email);
			request.getRequestDispatcher("navlibrarian.html").include(request, response);
			request.getRequestDispatcher("librariancarousel.html").include(request, response);
			LibrarianDao.setLoggedInStatus(email, 1);
			
		}else{
			failed_attempts=LibrarianDao.checkFailedAttempts(email);
			if (failed_attempts < 5) {
				System.out.println("Less than 5");
				failed_attempts=failed_attempts+1;
				lock_status = LibrarianDao.setFailedTimestamp(email);
				System.out.println("failed_attempts "+failed_attempts);
				LibrarianDao.updateFailedAttempts(failed_attempts, email);
			}else {
				freeze_account=LibrarianDao.freezeAccount(email);
				System.out.println(freeze_account + "freeze_account");
				out.println("Your account has been locked");
			}if (loggedinstatus == 1) {
				request.getRequestDispatcher("navhome.html").include(request, response);
				out.println("<div class='container'>");
				out.println("<h3>Session is already active in other browser</h3>");
				out.println("</div>");
			}else {
			request.getRequestDispatcher("navhome.html").include(request, response);
			out.println("<div class='container'>");
			out.println("<h3>Incorrect Login</h3>");
			request.getRequestDispatcher("librarianloginform.html").include(request, response);
			out.println("</div>");
		}
		}
		
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

}
