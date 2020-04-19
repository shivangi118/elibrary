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
			System.out.println("In checkfailed");
			PreparedStatement ps=con.prepareStatement("select failedcount from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
				count=rs.getInt("failedcount");
				System.out.println(count + "count");
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);
		}
		System.out.println("count" + count);
		return count;
	}
	
	public void updatefailedattempts(int failedattempts,String username) {
		boolean status1=false;
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("In updatefailed");
			PreparedStatement ps=con.prepareStatement("update e_admin set failedcount=? where username=?");
			ps.setInt(1,failedattempts);
			ps.setString(2,username);
			status=ps.executeUpdate();
			
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
	}
	
	public int settimestamp(String username) {
		boolean status1=false;
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("inSetitmestamp");
			PreparedStatement ps=con.prepareStatement("update e_admin set timeinterval=? where username=?");
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
			/*
			 * System.out.println("day "+ timestamp.getDay()); System.out.println("month "+
			 * timestamp.getMonth()); System.out.println("year "+ timestamp.getYear());
			 * System.out.println("Hour "+ timestamp.getHours());
			 * System.out.println("Minutes "+ timestamp.getMinutes());
			 */
			ps.setTimestamp(1,timestamp );
			ps.setString(2,username);
			status=ps.executeUpdate();
			
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}
	public Timestamp checkTimestamp(String username) {
		boolean status=false;
		Timestamp timeinterval=null;
		try{
			Connection con=DB.getCon();
			System.out.println("In checktimestamp");
			PreparedStatement ps=con.prepareStatement("select timeinterval from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
				timeinterval=rs.getTimestamp("timeinterval");
				System.out.println(timeinterval + "timeinterval");
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
			System.out.println("In checkfailed");
			PreparedStatement ps=con.prepareStatement("select accountfrozen from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
				accountFrozen=rs.getInt("accountfrozen");
				System.out.println(accountFrozen + "accountFrozen");
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
			System.out.println("in resetFrozenAccount");
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
			System.out.println("in resetFailedAttempts");
			PreparedStatement ps=con.prepareStatement("update e_admin set failedcount=? where username=?");
			ps.setInt(1, 0);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}

	public int resetTimestamp(String username) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("in resetTimestamp");
			PreparedStatement ps=con.prepareStatement("update e_admin set timeinterval=? where username=?");
			ps.setNull(1, java.sql.Types.TIMESTAMP );
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}

	public int freezeAccount(String username) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("in freezeAccount");
			PreparedStatement ps=con.prepareStatement("update e_admin set accountfrozen=? where username=?");
			ps.setInt(1, 1);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		
		return status;
	}
	public int checkLoggedInStatus(String username) {
		int loggedInStatus =0;
		try{
			Connection con=DB.getCon();
			System.out.println("In checkLoggedInStatus");
			PreparedStatement ps=con.prepareStatement("select loggedinstatus from e_admin where username=?");
			ps.setString(1,username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				loggedInStatus=rs.getInt("loggedinstatus");
				System.out.println("loggedinstatus " + loggedInStatus);
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
			System.out.println("in setLoggedInStatus");
			PreparedStatement ps=con.prepareStatement("update e_admin set loggedinstatus=? where username=?");
			ps.setInt(1, status);
			ps.setString(2,username);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
	}
	
}


