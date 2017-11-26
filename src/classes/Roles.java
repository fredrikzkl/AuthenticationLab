package classes;

public enum Roles {
	
	User(1),
	Technician(2),
	PowerUser(3),
	Admin(4);
	
	private final int roleID;
	
	Roles(int roleID){
		this.roleID = roleID;
	}
}
