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
		io.writeUsersToFile(userBase);
	}
	
}
