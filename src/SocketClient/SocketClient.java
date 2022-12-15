package SocketClient;

import java.io.IOException;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SocketClient extends Application {
	public static void main(String[] args) {
		
		launch(args);
	}
	
	public Context context;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(SocketClient.class.getResource("Connection_Page.fxml"));
		Parent parent = loader.load();
		ConnectionPageController controller = loader.getController();
		context = new Context();
		controller.initialize(primaryStage, context);
		
		Scene scene = new Scene(parent, 600, 400);
		primaryStage.setTitle("Client application");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	@Override
	public void stop() throws IOException {
		SocketPacket packet = new SocketPacket();
		if(context.socket != null) {
			packet.SetPacketType(PacketType.DISCONNECT);
			context.out.writeObject(packet);
		}
	}

}