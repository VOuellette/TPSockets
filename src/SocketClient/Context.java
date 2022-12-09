package SocketClient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Context {
	public String username;
	public String serverAdress;
	public String serverPort;
	public List<String> connectedUsers;
	public Socket socket;
	public PrintWriter out;
	public BufferedReader in;
	public BufferedReader stdIn;
	
	public Context() {
		username = "";
		serverAdress = "";
		serverPort = "";
		connectedUsers = new ArrayList<String>();
		socket = null;
		out = null;
		in = null;
		stdIn = null;
	}
}
