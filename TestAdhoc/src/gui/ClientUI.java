package gui;

import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.swing.JOptionPane;

public class ClientUI implements Observer{
	
	public Socket socket;
	public int port;
	public String ip;
	
	public ClientUI(int port) {
		this.port = port;
		try{
			String local;
			local = InetAddress.getLocalHost().getHostAddress()+":"+this.port;
			this.ip = (String) JOptionPane.showInputDialog(null, "IP: ","info",JOptionPane.INFORMATION_MESSAGE,null,null,local);
			
			this.port = Integer.parseInt(ip.substring(ip.indexOf(":") + 1));
			this.ip = ip.substring(0, ip.indexOf(":"));
			this.ip = "127.0.0.1";
			System.out.println("Debug: ip="+this.ip+", port="+this.port);
			this.socket = new Socket(ip, port); 
			
			String username = System.getProperty("user.name");
			username = (String) JOptionPane.showInputDialog(null, "Username: ","info",JOptionPane.INFORMATION_MESSAGE,null,null,username);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(username);
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			String response = (String) ois.readObject();
			
			JOptionPane.showMessageDialog(null, "Message: "+response, "Response", JOptionPane.INFORMATION_MESSAGE);
			
		//}catch (UnknownHostException uhe){
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, "Error: "+e.getMessage(),"ERROR!",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
