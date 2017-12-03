package programs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import classes.Session;


public interface Printer extends Remote{
	
	public String echo(String input) throws RemoteException;
	
	public Session authentication(String username, byte[] password) throws RemoteException;
	
	public int print(String filename, String printer, Session session) throws RemoteException;   // prints file filename on the specified printer
	public String queue(Session session) throws RemoteException;   // lists the print queue on the user's display in lines of the form <job number>   <file name>
	public String topQueue(int job, Session session) throws RemoteException;   // moves job to the top of the queue
	public void start(Session session) throws RemoteException;   // starts the print server
	public void stop_print(Session session) throws RemoteException; // stops the print server? 
	public void stop(Session session) throws RemoteException;   // stops the print server
	public String restart(Session s) throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
	public void status() throws RemoteException;  // prints status of printer on the user's display
	public String readConfig(String parameter, Session session) throws RemoteException;   // prints the value of the parameter on the user's display
	public String setConfig(String parameter, String value, Session session) throws RemoteException;   // sets the parameter to value
}
