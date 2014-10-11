package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.swing.*;

public class Server {
	public static int port;
	public static String ip;
	
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
		if(ip.isEmpty()){
			//Server.ip = InetAddress.getLocalHost().getHostAddress();
		}
		try{
			//this.ip ;
			ServerSocket ss = new ServerSocket();
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(),"Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
