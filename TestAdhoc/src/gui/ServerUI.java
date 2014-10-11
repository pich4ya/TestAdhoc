package gui;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import server.DataPackage;
import server.Server;

public class ServerUI implements Observer{
	private Server srv;

	public static JFrame frame;
	public static JPanel content;
	public static JPanel panel1;
	public static JPanel panel2;
	public static JPanel panel3;

	public static JButton btn_disconnect;
	public static JList<String> list_clients;
	public static DefaultListModel<String> list_clients_model;

	public ServerUI(Server s) {
		this.srv = s;
		this.makeUI();
	}

	private void makeUI() {
		
		ArrayList<Socket> list_sockets = this.srv.list_sockets;
		ArrayList<Integer> list_client_states = this.srv.list_client_states;
		ArrayList<DataPackage> list_data = this.srv.list_data;
		
		btn_disconnect = new JButton();
		btn_disconnect.setText("Disconnect");
		btn_disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selected = list_clients.getSelectedIndex();
				if (selected != -1) {
					try {
						list_client_states.set(selected, 1); // got kick
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Error!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		list_clients_model = new DefaultListModel<String>();
		list_clients = new JList<String>(list_clients_model);
		list_clients.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (e.getValueIsAdjusting()) {
					System.out.println(list_clients.getSelectedIndex());
				}
			}
		});
		frame = new JFrame();
		frame.setTitle("Server - " + srv.ip);

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// close all connections before existing program
				while (list_sockets.size() != 0) {
					try {
						for (int i = 0; i < list_client_states.size(); i++) {
							list_client_states.set(i, 2); // srv close
						}
					} catch (Exception ex2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null,
								"Error!! " + ex2.getMessage(), "ERROR!!",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		content = new JPanel();

		panel1.setLayout(new GridLayout(1, 1, 1, 1));
		panel1.add(btn_disconnect);

		panel2.add(new JLabel(srv.ip));

		panel3.setLayout(new BorderLayout(1, 1));
		panel3.add(panel1, BorderLayout.NORTH);
		panel3.add(new JScrollPane(list_clients), BorderLayout.CENTER);
		panel3.add(panel2, BorderLayout.SOUTH);

		content.setLayout(new GridLayout(1, 1, 1, 1));
		content.add(panel3);
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		frame.setContentPane(content);
		frame.pack();
		frame.setSize(350, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("observable updated!1");
		// TODO Auto-generated method stub
		//if(o == srv){
			System.out.println("observable updated!2");
			String input = (String) arg;
			if(input.startsWith("Add:")){
				System.out.println("observable updated!3");
				String updated[] = input.split(":");
				list_clients_model.addElement(updated[1] + " - "
						+ updated[2] + " - " + updated[3]);
			}else if(input.startsWith("remove:")){
				String update[] = input.split(":");
				int index = Integer.parseInt(update[1]);
				list_clients_model.removeElement(index);
			}
		//}
	}
}
