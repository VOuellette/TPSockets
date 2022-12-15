package SocketServer;

import java.net.Socket;

import SocketClient.SocketPacket;
import SocketClient.PacketType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientThread extends Thread {
	
	private SocketWrapper socket;
	private Server srv;
	
	public ClientThread(SocketWrapper socket, Server srv) {
		this.socket = socket;
		this.srv = srv;
	}
	

	public void run() {
		SocketPacket packet;
		
		try {
			this.socket.out = new ObjectOutputStream(this.socket.socket.getOutputStream());		
			this.socket.in = new ObjectInputStream(this.socket.socket.getInputStream());			

			while ((packet = (SocketPacket) this.socket.in.readObject()) != null) {						
				switch(packet.packetType) {
					case REGISTER_CLIENT: srv.RegisterClient(this.socket, packet); break;
					case BROADCAST: srv.Broadcast(packet); break;
					case USER_LIST: srv.BroadcastUserList(); break;
					case DISCONNECT: srv.DisconnectClient(this.socket); return;
				}				
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
}
