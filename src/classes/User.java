package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	
	private byte[] password;
	private byte[] salt;
	
	public List<Configuration> configList;
	
	public User(String username, byte[] hashedPassword){
		this.username = username;
		
		salt = CryptFunctions.generateSalt();
		password = CryptFunctions.hash(username + ":" + CryptFunctions.bytesToString(hashedPassword) + ":" + CryptFunctions.bytesToString(salt));
		
		configList = new ArrayList<>();
		
	}

	public String getUsername() {
		return username;
	}

	public byte[] getPassword() {
		return password;
	}


	public byte[] getSalt() {
		return salt;
	}
	
	
	
}
