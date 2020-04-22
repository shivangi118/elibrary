package com.elibrary.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class AdminDao {
	
	public boolean authenticate(String username,String password){
		boolean status=false;
		String password1;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select password from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
				password1=rs.getString("password");
				status=BCrypt.checkpw(password, password1);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	
	public int checkfailedattempts(String username) {
		boolean status=false;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Checking number of failed attempts...");
			PreparedStatement ps=con.prepareStatement("select failedcount from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
				count=rs.getInt("failedcount");
				System.out.println("The count is "+count);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);
		}
		return count;
	}
	
	public void updatefailedattempts(int failedattempts,String username) {
		int status=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Updating failed attempts...");
			PreparedStatement ps=con.prepareStatement("update e_admin set failedcount=? where username=?");
			ps.setInt(1,failedattempts);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
		}catch(Exception e){System.out.println(e);
		}
	}
	
	public int settimestamp(String username) {
		int status=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Setting time stamp.....");
			PreparedStatement ps=con.prepareStatement("update e_admin set timeinterval=? where username=?");
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(1,timestamp );
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
		}catch(Exception e){System.out.println(e);
		}
		return status;
	}
	public Timestamp checkTimestamp(String username) {
		boolean status=false;
		Timestamp timeinterval=null;
		try{
			Connection con=DB.getCon();
			System.out.println("Checking time stamp.....");
			PreparedStatement ps=con.prepareStatement("select timeinterval from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
				timeinterval=rs.getTimestamp("timeinterval");
				System.out.println("Timestamp is "+timeinterval);
			}
			con.close();
		}catch(Exception e){System.out.println(e);
		}
		return timeinterval;
	}
	
	public int checkfrozenstatus(String username) {
		boolean status=false;
		int accountFrozen=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Checking frozen status....");
			PreparedStatement ps=con.prepareStatement("select accountfrozen from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
				accountFrozen=rs.getInt("accountfrozen");
			}
			con.close();
		}catch(Exception e){System.out.println(e);
		}
		
		return accountFrozen;
	}
	public int resetFrozenAccount(String username) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Restoring account to active status....");
			PreparedStatement ps=con.prepareStatement("update e_admin set accountfrozen=? where username=?");
			ps.setInt(1, 0);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}
	public int resetFailedAttempts(String username) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Resetting failed attempts.....");
			PreparedStatement ps=con.prepareStatement("update e_admin set failedcount=? where username=?");
			ps.setInt(1, 0);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);
		}
		System.out.println(status+" status");
		return status;
	}

	public int resetTimestamp(String username) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("update e_admin set timeinterval=? where username=?");
			ps.setNull(1, java.sql.Types.TIMESTAMP );
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);
		}
		return status;
	}

	public int freezeAccount(String username) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Freezing account.....");
			PreparedStatement ps=con.prepareStatement("update e_admin set accountfrozen=? where username=?");
			ps.setInt(1, 1);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);
		}
		return status;
	}
	public int checkLoggedInStatus(String username) {
		int loggedInStatus =0;
		try{
			Connection con=DB.getCon();
			System.out.println("Checking logged in status.....");
			PreparedStatement ps=con.prepareStatement("select loggedinstatus from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				loggedInStatus=rs.getInt("loggedinstatus");
				System.out.println("Loggedinstatus is " + loggedInStatus);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);
		}
		
		return loggedInStatus;
	}
	public void setLoggedInStatus(String username, int logstatus) {
		int status= logstatus;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("Setting logged in status.....");
			PreparedStatement ps=con.prepareStatement("update e_admin set loggedinstatus=? where username=?");
			ps.setInt(1, status);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);
		}
	}
	
}


