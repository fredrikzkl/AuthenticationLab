package programs;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import classes.Configuration;
import classes.CryptFunctions;
import classes.FileHandler;
import classes.Role;
import classes.Roles;
import classes.Session;
import classes.User;

public class Server extends UnicastRemoteObject implements Printer{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5580036158652906052L;
	private final String permissionString = "Granted";
	
	private FileHandler io;
	private List<Session> sessionList;
	private List<String> log;
	
	private List<Job> queue;
	
	protected Server() throws RemoteException {
		super();
		sessionList = new ArrayList<>();
		log = new ArrayList<>();
		queue = new ArrayList<>();
		
		queue.add(new Job("Test1", "Printer1", "Fredrik"));
		queue.add(new Job("Test2", "Printer1", "Fredrik"));
		queue.add(new Job("Test3", "Printer1", "Fredrik"));
		queue.add(new Job("Test4", "Printer1", "Fredrik"));
		
		io = new FileHandler("userdata");
	}

	@Override
	public String echo(String input) throws RemoteException {
		return "Answer from server: " + input;
	}
	
	//PRINT: User Function
	@Override
	public int print(String filename, String printer, Session session) throws RemoteException{
		if(approveSession(session)){
			String response = givePermission(session, Roles.User);
			if(response.equals(permissionString)){
				Job temp = new Job(filename,printer,session.getUsername());
				queue.add(temp);
				log(session.getUsername() + " added a new job! The file '"+ filename + "' is going to print at '" + printer + "'. JobID: " + temp.id);
				return temp.id;
			}
		}
		return -1;
	}
	
	//QUEUE: User Function
	@Override
	public String queue(Session session) throws RemoteException{
		if(approveSession(session)){
			String response = givePermission(session, Roles.User);
			if(response.equals(permissionString)){
				String queue_value = "Queue: \n";
				for(int i = 0; i < queue.size() ; i++){
					Job tmp = queue.get(i);
					queue_value += "#" + (i+1) + " " + tmp.filename + " - " + tmp.printer +" - [" + tmp.owner + "] ID: " + tmp.id;
					if(i != queue.size()-1) queue_value += "\n";
				}
				log.add("User " + session.getUsername() + " fetched the queue data");
				return queue_value;
			}else{
			return response;
			}
		}
		return "Access Denied";
	}
	
	//TOP QUEUE: Power User Function
	@Override
	public String topQueue(int job, Session session) throws RemoteException{
		if(approveSession(session)){
			String response =givePermission(session, Roles.PowerUser);
			
			if(response.equals(permissionString)){
				if(queue.isEmpty()) return "No jobs yet. The qeueue is empty";
				
				Job prioJob = null;
				for(int i  = 0; i < queue.size() ; i++){
					if(queue.get(i).id == job){
						prioJob = queue.get(i);
						for(int x = i; x > 0;x--){
							queue.set(x, queue.get(x-1));
						}
						queue.set(0, prioJob);
						log(session.getUsername() + " moved job " + job + " to the top of the queue");
						return "Job '" + prioJob.id + "' moved to top of queue";
					}
				}
				return "Job with id '" + job + "' not found. Try the command 'queue' too see the full job list";
			}else{
				return response;
			}
		}
		return "Access Denied";
	}

	
	//START: Technician Function
	@Override
	public String start(Session s) throws RemoteException{
		if(approveSession(s)){
			String response = givePermission(s,Roles.Technician); 
			if(response.equals(permissionString)){
				return "OK";
			}else{
				return response;
			}
		}
		return "Access Denied";
	}

	
	//STOP: ?? Technician Function
	@Override
	public void stop(Session session) throws RemoteException{
		try{
			log("User " + session.getUsername() + " ended the session");
			sessionList.remove(session);
		}catch(Exception e){
			System.out.println("Invader? Session " + session.getSessionID() + " not found");
		}
	}

	//RESTART: Technician and PowerUser Function
	@Override
	public String restart(Session s) throws RemoteException{
		if(approveSession(s)){
			String response = givePermission(s, Roles.Technician);
			
			if(!response.equals(permissionString)) 
				response = givePermission(s, Roles.PowerUser);
			
			if(response.equals(permissionString)){
				queue.clear();
				return "Server restarted";
			}else{
				return response;
			}
		}
		return "Access Denied";
	}
	
	//STATUS: Technician Function
	@Override
	public String status(Session s) throws RemoteException{
		if(approveSession(s)){
			String response = givePermission(s, Roles.Technician);
			if(response.equals(permissionString)){
				return "Everything is ok";
			}else{
				return response;
			}
		}
		return "Access Denied";
	}

	@Override
	//READ CONFIG: Technician Function
	public String readConfig(String parameter, Session session) throws RemoteException{
		if(approveSession(session)){
			String response = givePermission(session, Roles.Technician);
			if(response.equals(permissionString)){
				User u = io.getUser(session.getUsername());
				if(parameter.isEmpty()) return "Paramter not found";
				String config_log = "";
				if(parameter.toLowerCase().equals("-a") || parameter.toLowerCase().equals("all") || parameter.equals(".")){
					config_log += "Configurations:\n";
					for(Configuration c : u.configList){
						config_log += c.toString() + "\n";
					}
				}
				else{
					for(Configuration c : u.configList){
						if(c.getParamter().equals(parameter)){
							config_log = c.toString();
							break;
						}
					}
				}
				log(session.getUsername() + " fetched the configuration list");
				return config_log;
			}else{
				return response;
			}
		}else{
			return "Request rejected";
		}
	}

	//SET CONFIG: Technician functon
	@Override
	public String setConfig(String parameter, String value, Session session)throws RemoteException {
		if(approveSession(session)){
			String response = givePermission(session, Roles.Technician);
			if(response.equals(permissionString)){
				if(value == "" || parameter == "") return "This command requres a paramter and a value";
				User u = io.getUser(session.getUsername());
				for(Configuration c : u.configList){
					if(c.getParamter().equals(parameter)){
						log(session.getUsername() + "'s configuration for '" + parameter + "' changed from value " + c.getValue() + " to " + value);
						c.setValue(value);
						io.save(u);
						log("User " + session.getUsername() + " edited configuration '" + parameter + "' to '" + value + "'");
						return "Configuration altered!";
					}
				}
				u.configList.add(new Configuration(parameter, value));
				log("New Configuration '" + parameter + "', added for '" + session.getUsername() + "', with value: " + value);
				io.save(u);
				log("User " + session.getUsername() + " added a new configuration '" + parameter + "' with the value: "+ value);
				return "New Configuration added!";
			}else{
				return response;
			}
		}
		return "Request rejected";
	}
	
	private void log(String s){
		System.out.println(s);
		log.add(s);
	}
	

	@Override
	public Session authentication(String username, byte[] password) throws RemoteException {
		List<User> ul = io.getUsers();
		User tmpUser = null;
		for(User u : ul){
			if(username.equals(u.getUsername())){
				tmpUser = u;
				break;
			}
		}
		if(tmpUser == null) return new Session(username, "User not found", false);
		
		byte[] incomingPassword = CryptFunctions.hash(tmpUser.getUsername() + ":" + CryptFunctions.bytesToString(password) + ":" + CryptFunctions.bytesToString(tmpUser.getSalt()));		
		byte[] userPassword = tmpUser.getPassword();
		
		if(Arrays.equals(incomingPassword, userPassword)){
			String uniqueID = UUID.randomUUID().toString();
			Session sesh = new Session(tmpUser.getUsername(), uniqueID, true);
			sessionList.add(sesh);
			log("User '" + tmpUser.getUsername() + "' connected to the printer! SessionID: " + sesh.getSessionID());
			return sesh;
		}
		return new Session(username, "Unauthorized. ", false);
	}
	
	private String findUserFromSessionID(String sessionID){
		for(Session s : sessionList){
			if(s.equals(sessionID)){
				return s.getUsername();
			}
		}
		return "User not found";
	}
	
	private boolean approveSession(Session s){
		for(Session se : sessionList){
			if(s.getSessionID().equals(se.getSessionID())) return true;
		}
		return false;
	}
	
	private String givePermission(Session s, Roles requiredRole){
		Role roleToUser = io.getRoleToUser(s.getUsername());
		switch(roleToUser.getRole()){
			case Admin:
				return permissionString;
			case PowerUser:
				if(requiredRole == Roles.PowerUser || requiredRole == Roles.User){
					return permissionString;
				}
				return "This function not available to Power Users";
			case Technician:
				if(requiredRole == Roles.Technician){
					return permissionString;
				}
				return "This function is not available to Technicians";
			case User:
				if(requiredRole == Roles.User){
					return permissionString;
				}
				return "Users don't have permission to use this function."; 
			default:
				break;
		}
		return "This user has no assigned role";
	}
	
	private static class Job{
		private static final AtomicInteger count = new AtomicInteger(0);
		
		private String printer;
		private String filename;
		private int id;
		
		private String owner;
		
		public Job(String filename, String printer, String owner){
			this.printer = printer;
			this.filename = filename;
			this.owner = owner;
			id = count.incrementAndGet(); 
		}
		
	}
	
	
	public static void main(String[] args) throws RemoteException {
		System.out.println("-Server Initiated-");
		Registry reg = LocateRegistry.createRegistry(5099);
		reg.rebind("printer", new Server());
	}
	
	
}
