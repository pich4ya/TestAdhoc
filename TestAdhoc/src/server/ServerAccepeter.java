package server;

import gui.ServerUI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerAccepeter implements Runnable {


	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			try {
				Socket socket = Server.server.accept();
				Server.sent = new Thread(new ServerSender());
				Server.receive = new Thread(new ServerReceiver());
				Server.sent.start();
				Server.receive.start();
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				String username = (String) ois.readObject();

				ObjectOutputStream oos = new ObjectOutputStream(
						socket.getOutputStream());
				oos.writeObject("Welcome to this server...");

				String hostAddr = socket.getInetAddress()
						.getHostAddress();
				String hostName = socket.getInetAddress().getHostName();

				
				ServerUI.list_clients_model.addElement(username + " - "
						+ hostAddr + " - " + hostName);
				/*
				setChanged();
			    notifyObservers("Add:"+username+":"+hostAddr+":"+hostName);
			      */
			    Server.list_client_states.add(0);

				Server.list_data.add(new DataPackage());
				Server.list_sockets.add(socket);
				System.out.println("accept 1");

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Error initAcceptThread(): " + e.getMessage(), "ERROR!!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}
