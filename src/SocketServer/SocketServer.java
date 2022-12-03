package SocketServer;


public class SocketServer {
	
	static Server server;
	
	public static void main(String[] args) {
		System.out.println("Starting server...");
		
		server = new Server(8181);
		server.Start();
	}
	
}