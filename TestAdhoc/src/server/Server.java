package server;

import gui.ServerUI;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class Server extends Observable{
	public int port;
	public String ip;

	public static ServerSocket server;
	public static ArrayList<Socket> list_sockets;
	public static ArrayList<Integer> list_client_states;
	public static ArrayList<DataPackage> list_data;

	public static Thread accept,sent,receive;
	
	/*
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	private Object lock3 = new Object();
	*/
	
	public Server(int port) {
		this("", port); // use localhost (listening in all inf.)
	}
	
	public Server(String ip, int port) {
		this.ip = ip;
		this.port = port;
		Server.list_sockets = new ArrayList<Socket>();
		Server.list_client_states = new ArrayList<Integer>();
		Server.list_data = new ArrayList<DataPackage>();
		
		this.runServer();

	}

	private void runServer(){
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			if (this.ip.isEmpty()) {
				this.ip = InetAddress.getLocalHost().getHostAddress();
			} // otherwise, specified by user initialization
			InetAddress addr = InetAddress.getByName(this.ip);
			Server.server = new ServerSocket(this.port, 100, addr);

			Server.accept = new Thread(new ServerAccepeter());
			Server.accept.start();
			

		} catch (IOException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "Error runServer(): " + e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	


	public static void disconnectClient(int index) {
		System.out.println("disconnectClient() "+index);
		// remove client from memory
		try {
			ServerUI.list_clients_model.removeElement(index);
			/*
			setChanged();
			notifyObservers("remove:"+index);
		    */
			Server.list_client_states.remove(index);
			Server.list_data.remove(index);
			Server.list_sockets.remove(index);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error disconnectClient(): " + e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
