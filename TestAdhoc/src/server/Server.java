package server;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class Server extends Observable{
	public int port;
	public String ip;

	public ServerSocket server;
	public ArrayList<Socket> list_sockets;
	public ArrayList<Integer> list_client_states;
	public ArrayList<DataPackage> list_data;

	private static Runnable accept, sent, receive;

	public Server(int port) {
		this("", port); // use localhost (listening in all inf.)
	}
	
	public Server(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.list_sockets = new ArrayList<Socket>();
		this.list_client_states = new ArrayList<Integer>();
		this.list_data = new ArrayList<DataPackage>();
		
		this.initAcceptThread();
		this.initSentThread();
		this.initReceiveThread();
		this.runServer();

	}

	public void initSentThread() {
		sent = new Runnable() {
			ObjectOutputStream oos;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					for (int i = 0; i < list_sockets.size(); i++) {
						System.out.println("sent thread 1");
						try {
							oos = new ObjectOutputStream(list_sockets.get(i)
									.getOutputStream());
							int client_state = list_client_states.get(i);
							oos.writeObject(client_state);

							oos = new ObjectOutputStream(list_sockets.get(i)
									.getOutputStream());
							oos.writeObject(list_data);

							if (client_state == 1) { // Kicked by server
								disconnectClient(i);
								i--;
								System.out.println("sent thread 2");
							} else if (client_state == 2) { // Server
															// disconnected
								System.out.println("sent thread 3");
								disconnectClient(i);
								i--;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
		};
	}

	private void initReceiveThread() {
		receive = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ObjectInputStream ois;

				while (true) {
					for (int i = 0; i < list_sockets.size(); i++) {
						try {
							System.out.println("rev thread 1");
							ois = new ObjectInputStream(list_sockets.get(i)
									.getInputStream());
							int receive_state = (Integer) ois.readObject();

							ois = new ObjectInputStream(list_sockets.get(i)
									.getInputStream());
							DataPackage dp = (DataPackage) ois.readObject();

							list_data.set(i, dp);
							if (receive_state == 1) {
								System.out.println("rev thread 2");
								disconnectClient(i);
								i--;
							}
							System.out.println("rev thread 3");
						} catch (Exception e) { // Client disconnected (Client
												// didn't notify server about
												// disconnecting)
							disconnectClient(i);
							i--;
						}
					}
				}
			}
		};
	}
	
	private void initAcceptThread() {
		accept = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Thread(sent).start();
				new Thread(receive).start();
				while (true) {
					try {
						Socket socket = server.accept();

						ObjectInputStream ois = new ObjectInputStream(
								socket.getInputStream());
						String username = (String) ois.readObject();

						ObjectOutputStream oos = new ObjectOutputStream(
								socket.getOutputStream());
						oos.writeObject("Welcome to this server...");

						String hostAddr = socket.getInetAddress()
								.getHostAddress();
						String hostName = socket.getInetAddress().getHostName();

						/*
						UserInterface.list_clients_model.addElement(username + " - "
								+ hostAddr + " - " + hostName);
						*/
						setChanged();
					    notifyObservers("Add:"+username+":"+hostAddr+":"+hostName);
					      
						list_client_states.add(0);

						list_data.add(new DataPackage());
						list_sockets.add(socket);
						System.out.println("accept 1");

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Error: " + e.getMessage(), "ERROR!!",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
	}
	
	private void runServer(){
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			if (this.ip.isEmpty()) {
				this.ip = InetAddress.getLocalHost().getHostAddress();
			} // otherwise, specified by user initialization
			InetAddress addr = InetAddress.getByName(this.ip);
			server = new ServerSocket(this.port, 100, addr);

			new Thread(accept).start();

		} catch (IOException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
					"Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	

	public void disconnectClient(int index) {
		System.out.println("disconnectClient() "+index);
		// remove client from memory
		try {
			//UserInterface.list_clients_model.removeElement(index);
			setChanged();
		    notifyObservers("remove:"+index);
		    
			list_client_states.remove(index);
			list_data.remove(index);
			list_sockets.remove(index);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
