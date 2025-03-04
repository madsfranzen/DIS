package Opgave13;

import java.net.*;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try (ServerSocket welcomeSocket = new ServerSocket(8081)) {
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				(new ServerThread(connectionSocket)).start();
			}
		}
	}

}
