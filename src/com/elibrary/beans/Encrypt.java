package com.elibrary.beans;
import org.mindrot.jbcrypt.BCrypt;

public class Encrypt {
public static void main(String args[]) {
	String hah = BCrypt.hashpw("Landmark@202", BCrypt.gensalt());
	String LibPass=BCrypt.hashpw("Zurich@13", BCrypt.gensalt() );
			System.out.println(hah);
			System.out.println(LibPass);
	
}
}
