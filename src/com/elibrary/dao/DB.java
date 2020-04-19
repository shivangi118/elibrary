package com.elibrary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;

public class DB {
public static Connection getCon(){
	Connection con=null;
	try{
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		Class.forName("com.mysql.jdbc.Driver");
		//con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary","root","root");
		
	}catch(Exception e){System.out.println(e);}
	return con;
}
}
