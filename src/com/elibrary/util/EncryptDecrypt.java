package com.elibrary.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptDecrypt {
	
	public static String encryptPassword(String password) {
	String encryptedPassword= BCrypt.hashpw(password, BCrypt.gensalt());
	return encryptedPassword;
	}
}
