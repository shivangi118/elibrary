package com.elibrary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.elibrary.beans.LibrarianBean;

public class LibrarianDao {

	public static int save(LibrarianBean bean){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("insert into e_librarian(name,email,password,mobile) values(?,?,?,?)");
			ps.setString(1,bean.getName());
			ps.setString(2,bean.getEmail());
			ps.setString(3,bean.getPassword());
			ps.setLong(4,bean.getMobile());
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	
	public static int update(LibrarianBean bean){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("update e_librarian set name=?,email=?,password=?,mobile=? where id=?");
			ps.setString(1,bean.getName());
			ps.setString(2,bean.getEmail());
			ps.setString(3,bean.getPassword());
			ps.setLong(4,bean.getMobile());
			ps.setInt(5,bean.getId());
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	
	public static List<LibrarianBean> view(){
		List<LibrarianBean> list=new ArrayList<LibrarianBean>();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from e_librarian");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				LibrarianBean bean=new LibrarianBean();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setEmail(rs.getString("email"));
				bean.setPassword(rs.getString("password"));
				bean.setMobile(rs.getLong("mobile"));
				list.add(bean);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return list;
	}
	
	public static LibrarianBean viewById(int id){
		LibrarianBean bean=new LibrarianBean();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from e_librarian where id=?");
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPassword(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setMobile(rs.getLong(5));
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return bean;
	}
	
	public static int delete(int id){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("delete from e_librarian where id=?");
			ps.setInt(1,id);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}

	public static boolean authenticate(String email,String password){
		boolean status=false;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from e_librarian where email=? and password=?");
			ps.setString(1,email);
			ps.setString(2,password);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	
	public static boolean authenticate1(String email,String password){
		boolean status=false;
		String password1;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select password from e_librarian where email=?");
			ps.setString(1,email);
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
	
	public static int checkFrozenStatus(String username) {
		boolean status=false;
		int accountFrozen=0;
		try{
			Connection con=DB.getCon();
			System.out.println("In checkFrozenStatus");
			PreparedStatement ps=con.prepareStatement("select accountfrozen from e_librarian where email=?");
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
	
	public static Timestamp checkTimestamp(String email) {
		boolean status=false;
		Timestamp timeinterval=null;
		try{
			Connection con=DB.getCon();
			System.out.println("In checkTimeStamp");
			PreparedStatement ps=con.prepareStatement("select timeinterval from e_librarian where email=?");
			ps.setString(1,email);
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
	
	public static int resetFrozenAccount(String email) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("in resetFrozenAccount");
			PreparedStatement ps=con.prepareStatement("update e_librarian set accountfrozen=? where email=?");
			ps.setInt(1, 0);
			ps.setString(2,email);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}
	
	public static int resetFailedAttempts(String email) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("in resetFailedAttempts");
			PreparedStatement ps=con.prepareStatement("update e_librarian set failedcount=? where email=?");
			ps.setInt(1, 0);
			ps.setString(2,email);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}
	
	public static int resetTimestamp(String email) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("in resetTimestamp");
			PreparedStatement ps=con.prepareStatement("update e_librarian set timeinterval=? where email=?");
			ps.setNull(1, java.sql.Types.TIMESTAMP );
			ps.setString(2,email);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}
	
	public static int checkFailedAttempts(String email) {
		boolean status=false;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("In checkFailedAttempts");
			PreparedStatement ps=con.prepareStatement("select failedcount from e_librarian where email=?");
			ps.setString(1,email);
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
	
	public static int setFailedTimestamp(String email) {
		boolean status1=false;
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("setFailedTimestamp");
			PreparedStatement ps=con.prepareStatement("update e_librarian set timeinterval=? where email=?");
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(1,timestamp );
			ps.setString(2,email);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		return status;
	}
	
	public static void updateFailedAttempts(int failedAttempts,String email) {
		boolean status1=false;
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("In updateFailedAttempts");
			PreparedStatement ps=con.prepareStatement("update e_librarian set failedcount=? where email=?");
			ps.setInt(1,failedAttempts);
			ps.setString(2,email);
			status=ps.executeUpdate();
			
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
	}

	public static int freezeAccount(String email) {
		int status=0;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("in freezeAccount");
			PreparedStatement ps=con.prepareStatement("update e_librarian set accountfrozen=? where email=?");
			ps.setInt(1, 1);
			ps.setString(2,email);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
		
		return status;
	}

	public static void setLoggedInStatus(String email, int logstatus) {
		int status= logstatus;
		int count=0;
		try{
			Connection con=DB.getCon();
			System.out.println("in setLoggedInStatus");
			PreparedStatement ps=con.prepareStatement("update e_librarian set loggedinstatus=? where email=?");
			ps.setInt(1, status);
			ps.setString(2,email);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println("Exception"+e);
		}
		System.out.println(status+" status");
	}

	public static int checkLoggedInStatus(String email) {
		int loggedInStatus =0;
		try{
			Connection con=DB.getCon();
			System.out.println("In checkLoggedInStatus");
			PreparedStatement ps=con.prepareStatement("select loggedinstatus from e_librarian where email=?");
			ps.setString(1,email);
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
		
	
}
