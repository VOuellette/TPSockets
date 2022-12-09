package SocketClient;

import javafx.stage.Stage;

public abstract class BaseController {
	protected Stage stage;
	protected Context context;
	
	public void initialize(Stage stage, Context context) {
		this.context = context;
		this.stage = stage;
	}
}
