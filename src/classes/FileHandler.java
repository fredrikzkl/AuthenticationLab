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
	private final String rolesDir = "./files/" + "accessList" + ".ser";
	
	
	public FileHandler(String filename){
		this.filename = "./files/" + filename + ".ser";
	}
	
	public void writeListToFile(List<?> list) {

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		try {

			fout = new FileOutputStream(filename);
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
	/*
	public Role getRoleToUser(String username){
		String userID = getUser(username).getId();
		List<Role> rl = getRoles();
		//System.out.println(rl.size());
		for(Role r : rl){
			if(r.getUserID().equals(userID)) return r;
		}
		return null;
	}*/
	
	public String save(User user){
		List<User> userList = getUsers();
		for(int i = 0; i<userList.size() ; i++){
			if(userList.get(i).getUsername().equals(user.getUsername())){
				userList.set(i, user);
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
	public List<Access> getAccessList() {

		List<Access> al = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
			
	        al = (List<Access>) ois.readObject();
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
		return al;
	}
	

	public void generateAccessList(){
		List<Access> al = new ArrayList<>();
		
		al.add(new Access(Role.User, "print"));
		al.add(new Access(Role.User, "queue"));
		al.add(new Access(Role.User, "stop"));
		
		al.add(new Access(Role.PowerUser,"print"));
		al.add(new Access(Role.PowerUser,"queue"));
		al.add(new Access(Role.PowerUser,"topQueue"));
		al.add(new Access(Role.PowerUser,"stop"));
		al.add(new Access(Role.PowerUser,"restart"));
		
		al.add(new Access(Role.Technician,"start"));
		al.add(new Access(Role.Technician,"stop"));
		al.add(new Access(Role.Technician,"print"));
		al.add(new Access(Role.Technician,"restart"));
		al.add(new Access(Role.Technician,"status"));
		al.add(new Access(Role.Technician,"readConfig"));
		al.add(new Access(Role.Technician,"setConfig"));
		
		al.add(new Access(Role.Admin,"print"));
		al.add(new Access(Role.Admin,"queue"));
		al.add(new Access(Role.Admin,"topQueue"));
		al.add(new Access(Role.Admin,"start"));
		al.add(new Access(Role.Admin,"stop"));
		al.add(new Access(Role.Admin,"print"));
		al.add(new Access(Role.Admin,"restart"));
		al.add(new Access(Role.Admin,"status"));
		al.add(new Access(Role.Admin,"readConfig"));
		al.add(new Access(Role.Admin,"setConfig"));
		
		writeListToFile(al);
	}
	
	
	public static void main(String[] args) {
		FileHandler access = new FileHandler("accessData");
		access.generateAccessList();
		FileHandler io = new FileHandler("userdata");
		List<User> userBase = new ArrayList<User>();
		User andreas = new User("Andreas",  CryptFunctions.hash("monkey"), Role.User);
		userBase.add(andreas);
		User tank = new User("Jon", CryptFunctions.hash("MegaStrongPasswordHashtagWillNeverBeCracked1337h4x0r"),Role.User);
		userBase.add(tank);
		User fredrik = new User("Fredrik", CryptFunctions.hash("asdasd"),Role.PowerUser);
		userBase.add(fredrik);
		User alice = new User("Alice",CryptFunctions.hash("IHaveThePower"),Role.Admin);
		userBase.add(alice);
		User bob = new User("Bob",CryptFunctions.hash("ILoveJanitorLyfe"),Role.Technician);
		userBase.add(bob);
		io.writeListToFile(userBase);
	}
	
	
	
}
