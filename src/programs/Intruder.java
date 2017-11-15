package programs;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import classes.CryptFunctions;
import classes.Session;

public class Intruder {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

			System.out.println("Sneaky intruder connecting to server...");
			Printer p = (Printer) Naming.lookup("rmi://localhost:5099/printer");
			System.out.println("Connected!");
			
			System.out.println(p.restart(new Session("Intruder","heheIdontNeedStupidSessions",true)));
		
	}
}
