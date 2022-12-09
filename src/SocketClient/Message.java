package SocketClient;

public class Message {
	private String userName;
	private int option;
	private String toUser;
	private String message;
	
	public Message(String userName, int option, String toUser, String message) {
		this.setMessage(message);
		this.setOption(option);
		this.setToUser(toUser);
		this.setUserName(userName);
	}
	
	public String toString() {
		return getUserName() + "/" + getOption() + "/" + getToUser() + "/" + getMessage();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
