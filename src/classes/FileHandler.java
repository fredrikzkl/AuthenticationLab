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
	
	
	public FileHandler(String filename){
		this.filename = "./files/" + filename + ".ser";
	}
//
//  Functions controlling file containing user-names and passwords
//
	public void writeUsersToFile(List<User> userList) {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(userList);
			System.out.println("Saving completed");
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
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
		writeUsersToFile(userList);
		return "Changes saved";
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {

		List<User> pl = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(filename);
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

//
//  Functions controlling file containing user-names and access
//
	public void writeAccessToFile(List<Access> accessList) {

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		try {

			fout = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(accessList);
			System.out.println("Saving completed");

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
	public Access getAccess(String username){
		List<Access> ul = getAccesses();
		for(int i = 0; i < ul.size(); i++){
			Access tmp = ul.get(i);
			if(tmp.getUsername().equals(username)) return tmp;
		}
		return null;
	}
	public String save(Access access){
		List<Access> accessList = getAccesses();
		for(int i = 0; i<accessList.size() ; i++){
			if(accessList.get(i).getUsername().equals(access.getUsername())){
				accessList.set(i, access);
			}
		}
		writeAccessToFile(accessList);
		return "Changes saved";
	}
	@SuppressWarnings("unchecked")
	public List<Access> getAccesses() {

		List<Access> pl = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
	        pl = (List<Access>) ois.readObject();
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

	
	
	
//
//  Function that initiates the files.
//
	public static void main(String[] args) {
		// Administrating user-names and passwords
		FileHandler io = new FileHandler("userdata");
		List<User> userBase = new ArrayList<User>();
		userBase.add(new User("Alice",  CryptFunctions.hash("password")));
		userBase.add(new User("Bob",  CryptFunctions.hash("password")));
		userBase.add(new User("Cecilia",  CryptFunctions.hash("password")));
		userBase.add(new User("David",  CryptFunctions.hash("password")));
		userBase.add(new User("Erica",  CryptFunctions.hash("password")));
		io.writeUsersToFile(userBase);
		
		// Administrating user-names and accesses
		FileHandler io2 = new FileHandler("userAccess");
		List<Access> userAcc = new ArrayList<Access>();
		// Adding Alice
		userAcc.add(new Access("Alice","print"));
		userAcc.add(new Access("Alice","queue"));
		userAcc.add(new Access("Alice","topQueue"));
		userAcc.add(new Access("Alice","start"));
		userAcc.add(new Access("Alice","stop"));
		userAcc.add(new Access("Alice","restart"));
		userAcc.add(new Access("Alice","status"));
		userAcc.add(new Access("Alice","readConfig"));
		userAcc.add(new Access("Alice","setConfig"));
		// Adding Bob
		userAcc.add(new Access("Bob","start"));
		userAcc.add(new Access("Bob","stop"));
		userAcc.add(new Access("Bob","restart"));
		userAcc.add(new Access("Bob","status"));
		userAcc.add(new Access("Bob","readConfig"));
		userAcc.add(new Access("Bob","setConfig"));
		// Adding Cecilia
		userAcc.add(new Access("Cecilia","print"));
		userAcc.add(new Access("Cecilia","queue"));
		userAcc.add(new Access("Cecilia","topQueue"));
		userAcc.add(new Access("Cecilia","restart"));
		// Adding the four normal users:
		userAcc.add(new Access("David","print"));
		userAcc.add(new Access("David","queue"));
		userAcc.add(new Access("Erica","print"));
		userAcc.add(new Access("Erica","queue"));
		userAcc.add(new Access("Fred","print"));
		userAcc.add(new Access("Fred","queue"));
		userAcc.add(new Access("George","print"));
		userAcc.add(new Access("George","queue"));	
		
		io2.writeAccessToFile(userAcc);
		
		List<Access> al = io2.getAccesses();
	}
	
}
