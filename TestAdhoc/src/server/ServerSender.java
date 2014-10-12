package server;

import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

public class ServerSender implements Runnable{

	ObjectOutputStream oos;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			for (int i = 0; i < Server.list_sockets.size(); i++) {
				//System.out.println("sent thread 1");
				try {
					oos = new ObjectOutputStream(Server.list_sockets.get(i)
							.getOutputStream());
					int client_state = Server.list_client_states.get(i);
					oos.writeObject(client_state);

					oos = new ObjectOutputStream(Server.list_sockets.get(i)
							.getOutputStream());
					oos.writeObject(Server.list_data);

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
					JOptionPane.showMessageDialog(null,
							"Error initSentThread(): " + e.getMessage(), "ERROR!!",
							JOptionPane.ERROR_MESSAGE);
					disconnectClient(i);
				}
			}
		}
	}

}
