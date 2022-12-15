package SocketServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
	public Socket socket;
	public ObjectOutputStream out;
	public ObjectInputStream in;
	
	public String username;
	
	public SocketWrapper() {
		this.socket = null;
		this.out = null;
		this.in = null;
		this.username = null;
	}
}
