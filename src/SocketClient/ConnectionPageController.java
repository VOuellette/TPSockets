package SocketClient;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ConnectionPageController extends BaseController{

    @FXML
    private Text ErrorMessage;

    @FXML
    private TextField UsernameField;

    @FXML
    private Button connectButton;

    @FXML
    private TextField serverAdressField;

    @FXML
    private TextField serverPortField;

    @FXML
    void ConnectButtonClick(ActionEvent event) throws IOException {
    	if(serverAdressField.getText().length() > 0) {
    		context.serverAdress = serverAdressField.getText();
    	}
    	else {
    		serverAdressField.setStyle("-fx-border-color: red;");
    	}
    	if(UsernameField.getText().length() > 0) {
    		context.username = UsernameField.getText();
    	}
    	else {
    		UsernameField.setStyle("-fx-border-color: red;");
    	}
    	if(serverPortField.getText().length() > 0) {
    		context.serverPort = serverPortField.getText();
    	}
    	else {
    		serverPortField.setStyle("-fx-border-color: red;");
    	}
    	
    	if(context.serverAdress.length() > 0 && context.username.length() > 0 && context.serverPort.length() > 0) {
    		try {
    			FXMLLoader loader = new FXMLLoader(SocketClient.class.getResource("MainPage.fxml"));
        		Parent parent =  loader.load();
        		MainMenuController controller = loader.getController();
        		
        		controller.initialize(stage, context);
    			ErrorMessage.setVisible(false);
    			
	    		Scene scene = new Scene(parent);
	            stage.setScene(scene);
	            stage.setTitle("Bonjour " + context.username);
	            
	            controller.ConnectSocket();
	            
            }catch(Exception e) {
            	FXMLLoader loader = new FXMLLoader(SocketClient.class.getResource("Connection_Page.fxml"));
        		Parent parent =  loader.load();
        		ConnectionPageController controller = loader.getController();
        		
        		controller.initialize(stage, context);
    			ErrorMessage.setVisible(false);
    			System.out.print(e);
	    		Scene scene = new Scene(parent);
	            stage.setScene(scene);
	            stage.setTitle("Client Application");
            	ErrorMessage.setText(e.toString());
            	ErrorMessage.setVisible(true);
            }
    	}
    	else {
        	ErrorMessage.setText("Veuillez Remplir tous les champs");
        	ErrorMessage.setVisible(true);	
    	}
    }

}
