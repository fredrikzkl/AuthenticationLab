package programs;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;

import classes.CryptFunctions;
import classes.Session;

import java.io.Console;

public class Client {
	
	private static Scanner reader;
	private static Console console;


	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		reader = new Scanner(System.in);
		console = System.console();
		try{
			System.out.println("Connecting to server...");
			Printer p = (Printer) Naming.lookup("rmi://localhost:5099/printer");
			
			System.out.print("Username: ");
			String username = reader.nextLine();
			
			byte[] password;
			
			System.out.print("Password: ");
			
			password = CryptFunctions.hash(reader.nextLine());
			
			Session sesh = p.authentication(username,password);
			
			if(sesh.authenticated){
				System.out.println("Authorazation Successful. SessionID: " + sesh.getSessionID() + "\nEnter command: ");
				while(true){
					String input = reader.nextLine();
					String [] cmd = input.split(" "); 
					
					String response = "Request Denied";
					switch(cmd[0].toLowerCase()){

						case "print":
							try{
								int id = p.print(cmd[1], cmd[2], sesh);
								if(id != -1) response = "Print added! ID: " + id;
							}catch(ArrayIndexOutOfBoundsException e){
								response  = "The Print function requre a 'filename' and a 'printer'";
							}
							break;
						case "queue":
							response = p.queue(sesh);
							break;
						case "topqueue":
							try{
								System.out.println("PARAMTER: "+ Integer.parseInt(cmd[1]));
								response = p.topQueue(Integer.parseInt(cmd[1]),sesh);
							}catch(Exception e){
								response = "'topQueue' require an integer as paramter " + e;
							}
							break;
						case "setconfig":
							try{
								response = p.setConfig(cmd[1], cmd[2], sesh);
							}catch(ArrayIndexOutOfBoundsException e){
								response = "A parameter and value is needed";
							}
							break;
						case "readconfig":
							try{
								response = p.readConfig(cmd[1], sesh);
							}catch(ArrayIndexOutOfBoundsException e){
								response = "A parameter is needed. Write 'all' for displaying all configs, or type the parameter";
							}
							break;
						case "restart":
							response = p.restart(sesh);
							break;
						case "stop":
							p.stop(sesh);
							System.out.println("Session Stopped. Thank you using the printer <3");
							System.exit(0);
						case "?":
							String s = "Commands: \n";
							s += "'print' 'file' 'printer' - Adds new print job\n";
							s += "'queue' - See the job queue \n";
							s += "'topQueue' 'jobId' - Moves a job to top of queue\n";
							s += "'setConfig' 'paramter' 'value' - Add / edit a configuration\n";
							s += "'readConfig' 'paramter' - Read configuration. Use 'all' to see full list\n";
							s += "'restart' - Restart the printer. Clears the job queue";
							s += "'stop' - Quit this session";
							response = s;
							break;
						default:
							response = "Command not found. Type '?' to see all commands";
							break;
					}
					System.out.println(response);
					
				}
			}else{
				System.out.println(sesh.getSessionID() + "Connection could not be made.");
			}
			
			
		}finally{
			
		}
		
		
	}


}
