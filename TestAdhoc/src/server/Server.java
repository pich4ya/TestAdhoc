package server;

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

	private static Thread accept, sent, receive;
	
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
		
		this.initAcceptThread();
		//this.initSentThread();
		//this.initReceiveThread();
		this.runServer();

	}

	private void initSentThread() {
		Server.sent = new Thread(new ServerSender());
	}

	private void initReceiveThread() {
		Server.receive = new Thread(new ServerReceiver());
	}
	
	private void initAcceptThread() {
		/*
		Thread t1 = new Thread(sent);
		Thread t2 = new Thread(receive);
		t1.start();
		t2.start();
		*/
		this.initSentThread();
		this.initReceiveThread();
		Server.accept = new Thread(new ServerAccepeter());
	}
	
	private void runServer(){
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			if (this.ip.isEmpty()) {
				this.ip = InetAddress.getLocalHost().getHostAddress();
			} // otherwise, specified by user initialization
			InetAddress addr = InetAddress.getByName(this.ip);
			Server.server = new ServerSocket(this.port, 100, addr);

			
			//new Thread(accept).start();
			Server.accept.start();
			

		} catch (IOException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "Error runServer(): " + e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public void notifyUI(int index){
		setChanged();
		notifyObservers("remove:"+index);
	}

	public static void disconnectClient(Server ss, int index) {
		System.out.println("disconnectClient() "+index);
		// remove client from memory
		try {
			//UserInterface.list_clients_model.removeElement(index);
			//removeClient(index);
			ss.notifyUI(index);
		    
			Server.list_client_states.remove(index);
			Server.list_data.remove(index);
			Server.list_sockets.remove(index);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error disconnectClient(): " + e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
