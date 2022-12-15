package SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainMenuController extends BaseController{
	private Message message;
    @FXML
    private ListView<String> ChatMessagesList;

    @FXML
    private TextField chatField;

    @FXML
    private ListView<String> connectedUsersList;

    @FXML
    private Button sendButton;

    @FXML
    void SendButtonClick(ActionEvent event) {
    	SocketPacket packet = new SocketPacket();
    	String text = chatField.getText();

    	if(text.length() > 0) {    		
    		String[] params = text.split(" ");
    		String[] users;
    		
    		if(params[0].equals("/w") && params.length >= 3) {
    			StringBuilder sb = new StringBuilder();
    			
    			users = params[1].split(";");
    			/*String[] newUsers = new String[users.length];
    			
    			for(int i = 0; i < users.length; i++) newUsers[i] = users[i];
    			newUsers[users.length] = context.username;*/
    			
    			packet.SetUsers(users);
    			
    			for(int i = 2; i < params.length; i++) {
    				sb.append(params[i]);
    				sb.append(" ");
    			}
    			
    			text = sb.toString();
    		}
    		
    		packet.SetPacketType(PacketType.BROADCAST)
    			  .SetUsername(context.username)
    			  .SetMessage(text);
    		
    		chatField.setText("");
    		try {
    			context.out.writeObject(packet);    			
    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}
    }
    
    
    public void ConnectSocket() throws NumberFormatException, UnknownHostException, IOException {
    	context.socket = new Socket(context.serverAdress, Integer.parseInt(context.serverPort));	
    	
		context.out = new ObjectOutputStream(context.socket.getOutputStream()); 		// Output to Server					
		context.in = new ObjectInputStream(context.socket.getInputStream()); 			// Input from Server	

		context.stdIn = new BufferedReader(new InputStreamReader(System.in)); 		
		
		System.out.println("Socket connected. Starting Server thread.");
		
		// Listen for server output on a separate thread
		Thread threadServerOutput = new Thread(() -> {
			SocketPacket packet = new SocketPacket();
			
			// Ajout du client avec username dans la liste interne du serveur
			RegisterClient();			
			
			while(!context.socket.isClosed()) {
				try {	
					packet = (SocketPacket) context.in.readObject();
					//line = context.in.readLine();
					if(context.in != null && packet != null) OnServerMessage(packet);	
							
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		});
		threadServerOutput.start();
    }
    
    private void RegisterClient() {
    	SocketPacket packet = new SocketPacket();
    	
    	packet.SetPacketType(PacketType.REGISTER_CLIENT)
		  .SetUsername(context.username);
	
		try {
			context.out.writeObject(packet);
		} catch (IOException e) {
			System.out.println(e);
		}
    }
    
    private void OnServerMessage(SocketPacket packet) {
    	
		switch(packet.packetType)
		{
			case BROADCAST:
				Platform.runLater(() -> {
					System.out.println(packet);
					ChatMessagesList.getItems().add(String.format("%s: %s", packet.username, packet.message));					
				});
				break;
			case USER_LIST: 
				Platform.runLater(() -> {
					connectedUsersList.getItems().clear();
					for(String user : packet.message.split(";")) {
						connectedUsersList.getItems().add(user);
					}
				});				
				break;
			case DISCONNECT:
				
			try {
				context.out.close();
				context.in.close();
				context.socket.close();
				
				System.out.println("Disconnected");
				
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			default: break;
		}
		
	}
}
