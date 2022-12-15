package SocketClient;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ListView;

public class Context {
	public String username;
	public String serverAdress;
	public String serverPort;
	public List<String> connectedUsers;
	public Socket socket;
	public ObjectOutputStream out;
	public ObjectInputStream in;
	
	public Context() {
		username = "";
		serverAdress = "";
		serverPort = "";
		connectedUsers = new ArrayList<String>();
		socket = null;
		out = null;
		in = null;
	}
}
