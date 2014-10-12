package server;

import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

public class ServerReceiver implements Runnable {


	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream ois;

		while (true) {
			//System.out.println("rev thread -1");
			for (int i = 0; i < Server.list_sockets.size(); i++) {
				System.out.println("rev thread 0");
				try {
					System.out.println("rev thread 1");
					ois = new ObjectInputStream(Server.list_sockets.get(i)
							.getInputStream());
					int receive_state = (Integer) ois.readObject();

					ois = new ObjectInputStream(Server.list_sockets.get(i)
							.getInputStream());
					DataPackage dp = (DataPackage) ois.readObject();

					Server.list_data.set(i, dp);
					if (receive_state == 1) {
						System.out.println("rev thread 2");
						Server.disconnectClient(this, i);
						i--;
					}
					System.out.println("rev thread 3");
				} catch (Exception e) { // Client disconnected (Client
										// didn't notify server about
										// disconnecting)
					JOptionPane.showMessageDialog(null,
							"Error initReceiveThread(): " + e.getMessage(), "ERROR!!",
							JOptionPane.ERROR_MESSAGE);
					disconnectClient(i);
					i--;
				}
			}
		}
	}

}
