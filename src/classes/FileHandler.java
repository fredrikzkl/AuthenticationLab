package classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;



public class FileHandler {
	
	private String filename;
	
	private final String userDir = "./files/" + "userdata" + ".ser";
	private final String rolesDir = "./files/" + "roles" + ".ser";
	
	
	public FileHandler(String filename){
		this.filename = "./files/" + filename + ".ser";
	}
	
	public void writeListToFile(List<?> list) {

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		try {

			fout = new FileOutputStream("filename");
			oos = new ObjectOutputStream(fout);
			
			oos.writeObject(list);

			System.out.println("Saving completed");

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public User getUser(String username){
		List<User> ul = getUsers();
		for(int i = 0; i < ul.size(); i++){
			User tmp = ul.get(i);
			if(tmp.getUsername().equals(username)) return tmp;
		}
		return null;
	}
	
	public Role getRoleToUser(String username){
		String userID = getUser(username).getId();
		List<Role> rl = getRoles();
		//System.out.println(rl.size());
		for(Role r : rl){
			if(r.getUserID().equals(userID)) return r;
		}
		return null;
	}
	
	public String save(User user){
		List<User> userList = getUsers();
		for(int i = 0; i<userList.size() ; i++){
			if(userList.get(i).getUsername().equals(user.getUsername())){
				userList.set(i, user);
			}
		}
		System.out.println("USERS CHANGED!");
		for(User u : userList){
			if(u.getUsername().equals(user.getUsername())){
				for(Configuration c : u.configList){
					System.out.println(c);
				}
			}
		}
		writeListToFile(userList);
		return "Changes saved";
	}
	
	
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {

		List<User> pl = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(userDir);
			ois = new ObjectInputStream(fin);
			
	        pl = (List<User>) ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pl;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {

		List<Role> pl = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(rolesDir);
			ois = new ObjectInputStream(fin);
			
	        pl = (List<Role>) ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pl;
	}
	
	public static void main(String[] args) {
		FileHandler io = new FileHandler("userdata");
		List<User> userBase = new ArrayList<User>();
		User andreas = new User("Andreas",  CryptFunctions.hash("monkey"));
		userBase.add(andreas);
		User tank = new User("Jon", CryptFunctions.hash("MegaStrongPasswordHashtagWillNeverBeCracked1337h4x0r"));
		userBase.add(tank);
		User fredrik = new User("Fredrik", CryptFunctions.hash("asdasd"));
		fredrik.configList.add(new Configuration("Size", "3"));
		userBase.add(fredrik);
		User alice = new User("Alice",CryptFunctions.hash("IHaveThePower"));
		userBase.add(alice);
		User bob = new User("Bob",CryptFunctions.hash("ILoveJanitorLyfe"));
		userBase.add(bob);
		io.writeListToFile(userBase);
		
		FileHandler roles_file = new FileHandler("roles");
		List<Role> r_list = new ArrayList<Role>();
		
		Role r1 = new Role(andreas.getId(),Roles.User);
		r_list.add(r1);
		
		Role r3 = new Role(fredrik.getId(),Roles.PowerUser);
		r_list.add(r3);

		Role r5 = new Role(bob.getId(),Roles.Technician);
		r_list.add(r5);
		
		Role r6 = new Role(alice.getId(), Roles.Admin);
		r_list.add(r6);
		
		roles_file.writeListToFile(r_list);
		
		
	}
	
}
