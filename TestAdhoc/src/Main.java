import gui.UserInterface;
import server.Server;


public class Main {

	public static void main(String[] args) {
		Server serv = new Server("127.0.0.1", 14423);
		UserInterface ui = new UserInterface(serv);
		serv.addObserver(ui);
	}

}
