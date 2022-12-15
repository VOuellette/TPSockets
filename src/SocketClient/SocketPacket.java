package SocketClient;
import java.io.Serializable;

public class SocketPacket implements Serializable {
	private static final long serialVersionUID = 1L;
	public PacketType packetType;
	public String username;
	public String message;
	public String[] users;
	
	public SocketPacket() {}
	
	public SocketPacket SetPacketType(PacketType type) {
		this.packetType = type;
		return this;
	}
	
	public SocketPacket SetUsername(String username) {
		this.username = username;
		return this;
	}
	
	public SocketPacket SetMessage(String message) {
		this.message = message;
		return this;
	}
	
	public void SetUsers(String[] users) {
		this.users = users;
	}
	
	public PacketType GetPacketType() {
		return this.packetType;
	}
	
	public String toString() {
		return String.format("%s | %s | %s", this.packetType, this.username, this.message);
		
	}
}
