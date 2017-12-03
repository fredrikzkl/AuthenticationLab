package classes;

public enum Role {
	
	User(1),
	Technician(2),
	PowerUser(3),
	Admin(4);
	
	private final int roleID;
	
	Role(int roleID){
		this.roleID = roleID;
	}
}
