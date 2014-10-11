package server;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Server {
	public static int port;
	public static String ip;
	
	public static ServerSocket server;
	public static ArrayList<Socket> list_sockets;
	public static ArrayList<Integer> list_client_states;
	public static ArrayList<DataPackage> list_data;
	
	public static JFrame frame;
	public static JPanel content;
	public static JPanel panel1;
	public static JPanel panel2;
	public static JPanel panel3;
	
	public static JButton btn_disconnect;
	public static JList<?> list_clients;
	public static DefaultListModel<?> list_clients_model;
	
	public Server(int port){
		this("", port);
	}
	
	public Server(String ip, int port){
		list_sockets = new ArrayList<Socket>();
		try{
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			if(ip.isEmpty()){
				Server.ip = InetAddress.getLocalHost().getHostAddress();
			}else{ 
				Server.ip = ip;
			}
			ServerSocket ss = new ServerSocket();
		}catch(IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
			JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(),"Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		btn_disconnect = new JButton();
		btn_disconnect.setText("Disconnect");
		btn_disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selected = list_clients.getSelectedIndex();
				if(selected!=-1){
					
				}
			}
		});
		list_clients_model = new DefaultListModel();
		list_clients = new JList(list_clients_model);
		list_clients.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(e.getValueIsAdjusting()){
					System.out.println(list_clients.getSelectedIndex());
				}
			}
		});
		frame = new JFrame();
		frame.setTitle("Server - "+Server.ip);
		
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
		
		panel1.setLayout(new GridLayout(1,1,1,1));
		panel1.add(btn_disconnect);
		
		panel2.add(new JLabel(Server.ip));
		
		panel3.setLayout(new BorderLayout(1, 1));
		panel3.add(panel1, BorderLayout.NORTH);
		panel3.add(new JScrollPane(list_clients), BorderLayout.CENTER);
		panel3.add(panel2, BorderLayout.SOUTH);
		
		content.setLayout(new GridLayout(1,1,1,1));
		content.add(panel3);
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		frame.setContentPane(content);
		frame.pack();
		frame.setSize(350, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
	}
}
