package SocketClient;

import java.net.*;  
import java.io.*;  

public class SocketClient {
	
	private static PrintWriter out;
	private static BufferedReader in;
	private static BufferedReader stdIn;
	
	public static void main(String[] args) {
		
		Socket socket;
		
		try{  
			
			socket = new Socket("localhost", 8181);						
			
			out = new PrintWriter(socket.getOutputStream(), true); 					 // Output to Server						
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input from Server			
			stdIn = new BufferedReader(new InputStreamReader(System.in)); 			 // System IO
			
			// Listen for server output on a separate thread
			Thread threadServerOutput = new Thread(() -> {
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
			
			threadServerOutput.interrupt();
			socket.close();			
		}
		catch(Exception e){
			System.out.println(e);
		}  
	}
	
	private static void OnServerMessage(String message) {
		// message devrait normalement etre format JSON, juste string pour tests
		System.out.println("Server : " + message);
		
		// Json convert and shit
	}

}