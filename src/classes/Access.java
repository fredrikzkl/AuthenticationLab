package classes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// This class links one user-name with one function that user is allowed to access. 
// In order for a user to get access to multiple functions, multiple instances must be made. 
public class Access implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String accessibleFunction;
	
//	public List<Access> accessList;
	
	public Access(String username, String access) {
		this.username = username;
		this.accessibleFunction = access;
//		accessList = new ArrayList<>();
	}
	
	public String getUsername() {
		return username;
	}
	public String getAccessibleFunction() {
		return accessibleFunction;
	}
}

