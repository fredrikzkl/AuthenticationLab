package programs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import classes.Session;

public interface Printer extends Remote{
	
	public String echo(String input) throws RemoteException;
	public Session authentication(String username, byte[] password) throws RemoteException;
	public String print(String filename, String printer, Session session) throws RemoteException; 
	public String queue(Session session) throws RemoteException;   
	public String topQueue(int job, Session session) throws RemoteException;
	public String start(Session s) throws RemoteException;
	public void stop(Session session) throws RemoteException;
	public String restart(Session s) throws RemoteException;
	public String status(Session s) throws RemoteException;
	public String readConfig(String parameter, Session session) throws RemoteException;
	public String setConfig(String parameter, String value, Session session) throws RemoteException;  
}
