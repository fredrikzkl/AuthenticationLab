package classes;

import java.io.Serializable;

public class Access implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6473199323649972802L;
	private Role role;
	private String functionName;
	
	public Access(Role role, String functionName){
		this.role = role;
		this.functionName = functionName;
	}

	public Role getRole() {
		return role;
	}

	public String getFunctionName() {
		return functionName;
	}
	
}
