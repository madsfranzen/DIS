package Lektion4.SocketEks2023;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	static String name;

	public static void main(String[] args) throws Exception {
		clearTerm();
		System.out.println("Waiting for connection...");

		try (ServerSocket welcomSocket = new ServerSocket(6789)) {
			while (true) {
				Socket connectionSocket = welcomSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));

				String inFromClientString = inFromClient.readLine();

				String prefix = inFromClientString.split(";")[0];
				name = inFromClientString.split(";")[1];

				if (prefix.equals("Hello") && !name.isEmpty()) {
					chat(connectionSocket, name, inFromClient);
				}
			}
		}
	}

	public static void chat(Socket connectionSocket, String name, BufferedReader inFromClient) throws IOException {
		clearTerm();
		System.out.println(
				connectionSocket.getRemoteSocketAddress() + " " + name + " wants to connect. Accept? (JA / NEJ)");

		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String message;
		String response = inFromUser.readLine();
		outToClient.writeBytes(response + "\n");

		if (response.equals("JA")) {
			clearTerm();

			while (true) {
				message = inFromClient.readLine();

				System.out.print(" -> " + name + ": ");
				typewriterPrint(message, 50);

				if (message.equals("End")) {
					outToClient.flush();
					outToClient.close();
					connectionSocket.close();

					System.out.println("Socket closed. Program still running. Awaiting new connection...");
					return;
				}

				response = inFromUser.readLine();
				outToClient.writeBytes(response + "\n");
				System.out.println();
			}
		}
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
}
