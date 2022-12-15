package SocketServer;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import SocketClient.PacketType;
import SocketClient.SocketPacket;

public class Server {
	private ServerSocket serverSocket;
    private HashMap<String, SocketWrapper> clients = new HashMap<String, SocketWrapper>();
    
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
    		SocketWrapper socket = new SocketWrapper();
    		
            try {
            	socket.socket = serverSocket.accept();            	
            	//clients.add(socket);
            	
            	//System.out.println("Client " + clientSocket.getInetAddress().getHostName() + " connected");            	
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                return;
            }
            
            // new thread for a client
            new ClientThread(socket, this).start();
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

	public void Broadcast(SocketPacket packet) {		
    	try {    	
    		for (HashMap.Entry<String, SocketWrapper> entry : clients.entrySet()) {
    			
    			// Si on envoie un message privé, envoi seulement aux destinataires et au sender
    			if(packet.users != null && !Arrays.stream(packet.users).anyMatch(entry.getKey()::equals) && entry.getKey() != packet.username) continue;
    			
    	        entry.getValue().out.writeObject(packet);
    	        entry.getValue().out.flush();
    	    }
    	} catch(Exception e) {
    		System.out.println(e);    
    	}		
	}
	
	public void BroadcastUserList() {
		SocketPacket packet = new SocketPacket();
		StringBuilder sb = new StringBuilder();
		
		// Build clients message string
		clients.keySet().forEach((key) -> { sb.append(key + ";"); });
		
		packet.SetPacketType(PacketType.USER_LIST)
			  .SetMessage(sb.toString());
		
		Broadcast(packet);		
	}
	
	public void RegisterClient(SocketWrapper socket, SocketPacket packet) {
		socket.username = packet.username;
		clients.put(packet.username, socket);
		System.out.println(String.format("Client %s connected.", socket.username));
		
		BroadcastUserList();
	}
	
	public void DisconnectClient(SocketWrapper socket) {
		SocketPacket packet = new SocketPacket();
		packet.SetPacketType(PacketType.DISCONNECT);
		
		try {
			String username = socket.username;
			
			System.out.println(String.format("Client %s disconnected.", username));
			
			socket.out.writeObject(packet);
			
			clients.remove(username);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		BroadcastUserList();		
	}
}
