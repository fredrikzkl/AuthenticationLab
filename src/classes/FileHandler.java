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
		User alice = new User("Alice",  CryptFunctions.hash("password"));
		userBase.add(alice);
		User bob = new User("Bob",  CryptFunctions.hash("password"));
		userBase.add(bob);
		User cecilia = new User("Cecilia",  CryptFunctions.hash("password"));
		userBase.add(cecilia);
		User david = new User("David",  CryptFunctions.hash("password"));
		userBase.add(david);
		User erica = new User("Erica",  CryptFunctions.hash("password"));
		userBase.add(erica);
		User fred = new User("Fred",  CryptFunctions.hash("password"));
		userBase.add(fred);
		User george = new User("George",  CryptFunctions.hash("password"));
		userBase.add(george);
		io.writeUsersToFile(userBase);
		
		// Administrating user-names and accesses
		FileHandler io2 = new FileHandler("userAccess");
		List<Access> userAcc = new ArrayList<Access>();
		// Adding Alice
		Access alice1 = new Access("Alice", "print");
		userAcc.add(alice1);
		Access alice2 = new Access("Alice", "queue");
		userAcc.add(alice2);
		Access alice3 = new Access("Alice", "topQueue");
		userAcc.add(alice3);
		Access alice4 = new Access("Alice", "start");
		userAcc.add(alice4);
		Access alice5 = new Access("Alice", "stop");
		userAcc.add(alice5);
		Access alice6 = new Access("Alice", "restart");
		userAcc.add(alice6);
		Access alice7 = new Access("Alice", "status");
		userAcc.add(alice7);
		Access alice8 = new Access("Alice", "readConfig");
		userAcc.add(alice8);
		Access alice9 = new Access("Alice", "setConfig");
		userAcc.add(alice9);
		// Adding Bob
		Access bob1 = new Access("Bob", "print");
		userAcc.add(bob1);
		Access bob2 = new Access("Bob", "queue");
		userAcc.add(bob2);
		Access bob3 = new Access("Bob", "start");
		userAcc.add(bob3);
		Access bob4 = new Access("Bob", "stop");
		userAcc.add(bob4);
		Access bob5 = new Access("Bob", "restart");
		userAcc.add(bob5);
		Access bob6 = new Access("Bob", "status");
		userAcc.add(bob6);
		Access bob7 = new Access("Bob", "readConfig");
		userAcc.add(bob7);
		Access bob8 = new Access("Bob", "setConfig");
		userAcc.add(bob8);
		// Adding Cecilia
		Access cecilia1 = new Access("Cecilia", "print");
		userAcc.add(cecilia1);
		Access cecilia2 = new Access("Cecilia", "queue");
		userAcc.add(cecilia2);
		Access cecilia3 = new Access("Cecilia", "topQueue");
		userAcc.add(cecilia3);
		Access cecilia4 = new Access("Cecilia", "restart");
		userAcc.add(cecilia4);
		// Adding the four normal users:
		Access david1 = new Access("David", "print");
		userAcc.add(david1);
		Access david2 = new Access("David", "queue");
		userAcc.add(david2);
		Access erica1 = new Access("Erica", "print");
		userAcc.add(erica1);
		Access erica2 = new Access("Erica", "queue");
		userAcc.add(erica2);
		Access fred1 = new Access("Fred", "print");
		userAcc.add(fred1);
		Access fred2 = new Access("Fred", "queue");
		userAcc.add(fred2);
		Access george1 = new Access("George", "print");
		userAcc.add(george1);
		Access george2 = new Access("George", "queue");
		userAcc.add(george2);
		
		
		io2.writeAccessToFile(userAcc);
		
		List<Access> al = io2.getAccesses();
		for(Access a : al) {
			System.out.println(a.getUsername() + " " + a.getAccessibleFunction());
		}
	}
	
}
