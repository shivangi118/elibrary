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
		//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary","root","root");
		con=DriverManager.getConnection("mysql://letav0u0mx45zir6:fyev1nxm4s0n6juu@zwgaqwfn759tj79r.chr7pe7iynqr.eu-west-1.rds.amazonaws.com:3306/pggjq3jjkr2nkpl3");
		
	}catch(Exception e){System.out.println(e);}
	return con;
}
}
