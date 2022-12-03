package SocketServer;

import java.net.Socket;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;


public class ClientThread extends Thread {
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	public ClientThread(Socket clientSocket) {
		this.socket = clientSocket;
	}
	
	public void run() {
		String inputLine;
		
		try {
			this.out =  new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			while ((inputLine = in.readLine()) != null) {
				out.println(inputLine);
			}			
		}
		catch(Exception e) {
			out.println(e);
			return;
		}
	}
}
