package SocketEks2023;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) throws Exception, IOException {
		clearTerm();

		String response;
		String myName = "Mads Franzen";
		String ipToConnect = "10.10.137.23";

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		Socket clientSocket = new Socket(ipToConnect, 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		// Initialize connection
		outToServer.writeBytes("Hello;" + myName + '\n');

		System.out.println("Attempting to connect to " + ipToConnect + " - Awaiting response...");

		response = inFromServer.readLine();

		clearTerm();

		if (response.equals("JA")) {
			System.out.println("CONNECTIONN ESTABLISHED.");
			System.out.println("Type a message: ");
			chat(inFromUser, inFromServer, outToServer);
		} else {
			System.out.println("CONNECTION REJECTED.");
			clientSocket.close();
		}

		System.out.println("Chat has ended.");
	}

	private static void chat(BufferedReader inFromUser, BufferedReader inFromServer, DataOutputStream outToServer)
			throws IOException {
		String message;
		String response;

		System.out.println();

		while (true) {
			message = inFromUser.readLine();
			outToServer.writeBytes(message + '\n');
			System.out.println();

			if (message.equals("END")) {
				return;
			}

			response = inFromServer.readLine();
			// clearTerm();
			System.out.print(" -> Server: ");
			typewriterPrint(response, 50);

		}

	}

	public static void typewriterPrint(String text, int delay) {
		for (char c : text.toCharArray()) {
			System.out.print(c);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println();
		System.out.println();
	}

	private static void clearTerm() {
		for (int i = 0; i < 50; i++) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}

}
