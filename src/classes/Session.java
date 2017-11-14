package classes;

import java.io.Serializable;

public class Session implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1348000430233678721L;
	
	private String username;
	private String sessionID;
	public boolean authenticated;
	
	
	
	public Session(String username, String sessionID, boolean authenticated){
		this.username = username;
		this.sessionID = sessionID;
		this.authenticated = authenticated;
	}

	public String getUsername() {
		return username;
	}

	public String getSessionID() {
		return sessionID;
	}
	
	public String toString(){
		return "" + username + " - " + sessionID;
	}
	
	
}
