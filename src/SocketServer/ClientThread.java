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
			//out.println("whaddup");
			//TODO envoit de la premiere reponse du serveur contenant la liste des users connectes
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}			
		}
		catch(Exception e) {
			out.println(e);
			return;
		}
	}
}
