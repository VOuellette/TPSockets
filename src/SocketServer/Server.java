package SocketServer;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
	private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<Socket> clients = new ArrayList<Socket>();
    
    private int port;
    
    public Server(int port) {
    	this.port = port;
    	
        try {
			this.serverSocket = new ServerSocket(port);
        }
        catch(Exception e) {
        	System.out.println("Cannot create server: " + e);
        }
    }
    
    public void Start() {
    	while (true) {
    		PrintWriter out = null;
    		BufferedReader in = null;
    		
            try {
            	clientSocket = serverSocket.accept();
            	
            	clients.add(clientSocket);
            	
            	System.out.println("Client " + clientSocket.getInetAddress().getHostName() + " connected");            	
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                return;
            }
            
            // new thread for a client
            new ClientThread(clientSocket).start();
        } 
    }
    

    public void Stop() {
    	try {
    		System.out.println("Closing");

    		this.serverSocket.close();    		
    		
    		System.out.println("Closed");
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    }

}
