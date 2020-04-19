package com.elibrary.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Timestamp;

import com.elibrary.beans.AdminBean;
import com.elibrary.dao.AdminDao;
import com.elibrary.dao.LibrarianDao;
import com.elibrary.util.DemoLogger;
import com.elibrary.util.VerifyRecaptcha;
import com.elibrary.util.WriteLogEntriesToLogFile;


@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Admin Section</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("<script src=\"https://www.google.com/recaptcha/api.js\" async defer></script>");
		out.println("<style>\r\n" + 
				"body {\r\n" + 
				"  background-color: #E6E6FA;\r\n" + 
				"}\r\n" + 
				"</style>");
		out.println("</head>");
		out.println("<body>");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println(gRecaptchaResponse);
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		System.out.println("Captcha "+ verify);
		HttpSession session=request.getSession();
		session.setAttribute("adminemail",email);
		getServletContext().log("email"+email);
		getServletContext().log("password"+password);
		AdminDao adminDao=new AdminDao();
		int failed_attempts,lock_status,reset_failed_attempts,reset_timestamp,freeze_account,timeinterval,frozenstatus,loggedinstatus;
		Timestamp timestamp_from_db,currentTimeStamp;
		frozenstatus = adminDao.checkfrozenstatus(email);
		if (frozenstatus == 1) {
			//timeinterval
			System.out.println("Account is frozen");
			currentTimeStamp = new Timestamp(System.currentTimeMillis());
			timestamp_from_db = adminDao.checkTimestamp(email);
			long milliseconds= currentTimeStamp.getTime()-timestamp_from_db.getTime();
			int seconds =(int) milliseconds /1000;
			int hours = seconds / 3600;
			int minutes = (seconds % 3600) / 60;
			seconds = (seconds % 3600) % 60;
			System.out.println(" Difference: ");
			System.out.println(" Hours: " + hours);
			System.out.println(" Minutes: " + minutes);
			System.out.println(" Seconds: " + seconds);
			if(hours >=1 || minutes >=5)
			{
				System.out.println("Minutes are greater than equal to 5");
				System.out.println(hours + minutes + " hours and minutes");
				// frozenstatus == 0
				frozenstatus=adminDao.resetFrozenAccount(email);
				// numberofattempts == 0
				reset_failed_attempts=adminDao.resetFailedAttempts(email);
				//timestamp = null
				reset_timestamp=adminDao.resetTimestamp(email);
			
			}
		}
		loggedinstatus = adminDao.checkLoggedInStatus(email);
		if (adminDao.authenticate(email, password) && frozenstatus == 0 && loggedinstatus == 0 && verify){
			System.out.println("In admin , Login Successful");
			//HttpSession session=request.getSession();
			session.setAttribute("admin","true");
			adminDao.resetFailedAttempts(email);
			request.getRequestDispatcher("navadmin.html").include(request, response);
			request.getRequestDispatcher("admincarousel.html").include(request, response);
			adminDao.setLoggedInStatus(email, 1);
			
		}else{
			failed_attempts=adminDao.checkfailedattempts(email);
			if (failed_attempts < 5) {
				System.out.println("Less than 5");
				failed_attempts=failed_attempts+1;
				lock_status = adminDao.settimestamp(email);
				System.out.println("failed_attempts "+failed_attempts);
				adminDao.updatefailedattempts(failed_attempts,email);
			}else {
				
				//freeze account
				freeze_account=adminDao.freezeAccount(email);
				System.out.println(freeze_account + "freeze_account");
				out.println("<h3>Your account has been locked<h3>");	
			}
			if (loggedinstatus == 1) {
				request.getRequestDispatcher("navhome.html").include(request, response);
				out.println("<div class='container'>");
				out.println("<h3>Session is already active in other browser</h3>");
				out.println("</div>");
			}else {
				System.out.print("Here");
				request.getRequestDispatcher("navhome.html").include(request, response);
				out.println("<div class='container'>");
				out.println("<h3>Incorrect Login</h3>");
				request.getRequestDispatcher("adminloginform.html").include(request, response);
				out.println("</div>");
			}
			
		}
		
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

	}
