package SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
    	message.setMessage(chatField.getText());
    	chatField.setText("");
    	if(message.getMessage().length() > 0) {
    		ChatMessagesList.getItems().add(message.getMessage());
    		context.out.println(message.toString());
    	}
    }
    
    public void ConnectSocket() throws NumberFormatException, UnknownHostException, IOException {
    	message = new Message(context.username, 0, "", "");
    	context.socket = new Socket(context.serverAdress, Integer.parseInt(context.serverPort));						
		
		context.out = new PrintWriter(context.socket.getOutputStream(), true); 					 // Output to Server						
		context.in = new BufferedReader(new InputStreamReader(context.socket.getInputStream())); // Input from Server			
		context.stdIn = new BufferedReader(new InputStreamReader(System.in)); 			 // System IO
		
		// Listen for server output on a separate thread
		Thread threadServerOutput = new Thread(() -> {
			String line;
			while(!context.socket.isClosed()) {					
				try {						
					if(context.in != null && (line = context.in.readLine()) != null) OnServerMessage(line);	
							
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		});
		threadServerOutput.start();
    }
    
    /*@FXML
    public void initialize() {
    	ChatMessagesList = new ListView<String>();
    }
    /*@FXML
    public void initialize() throws NumberFormatException, UnknownHostException, IOException {
    	
    	context.socket = new Socket(context.serverAdress, Integer.parseInt(context.serverPort));						
		
		out = new PrintWriter(context.socket.getOutputStream(), true); 					 // Output to Server						
		in = new BufferedReader(new InputStreamReader(context.socket.getInputStream())); // Input from Server			
		stdIn = new BufferedReader(new InputStreamReader(System.in)); 			 // System IO
		
		// Listen for server output on a separate thread
		/*Thread threadServerOutput = new Thread(() -> {
			String line;
			while(!socket.isClosed()) {					
				try {						
					if(in != null && (line = in.readLine()) != null) OnServerMessage(line);	
					
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		});
		threadServerOutput.start();
		
		
		// Wait for client input
		String userInput;
		while ((userInput = stdIn.readLine()).compareTo("exit") != 0) {
			out.println(userInput);
		}
		
		//threadServerOutput.interrupt();
		socket.close();
    }*/
    
    public void OnServerMessage(String message) {
		// message devrait normalement etre format JSON, juste string pour tests
		System.out.println("Server : " + message);
		
		// Json convert and shit
	}

}
