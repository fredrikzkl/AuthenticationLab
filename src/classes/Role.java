package classes;

import java.io.Serializable;

public class Role implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userID;
	
	private Roles role;
	
	public Role(String userID, Roles role){
		this.userID = userID;
		this.role = role;
	}

	public String getUserID() {
		return userID;
	}

	public Roles getRole() {
		return role;
	}
	
}
