package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	
	private byte[] password;
	private byte[] salt;
	
	private String id;
	private Role role;
	
	
	
	public User(String username, byte[] hashedPassword, Role role){
		this.username = username;
		this.role = role;
		
		salt = CryptFunctions.generateSalt();
		password = CryptFunctions.hash(username + ":" + CryptFunctions.bytesToString(hashedPassword) + ":" + CryptFunctions.bytesToString(salt));
				
		id = UUID.randomUUID().toString();
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

	public String getId() {
		return id;
	}

	public Role getRole() {
		return role;
	}
	
	
	
}
