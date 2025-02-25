package EkstraOpgave;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class NavneServer {
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws Exception {
		HashMap<String, String> h = new HashMap<>();
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(7777);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			clientSentence = inFromClient.readLine();
			switch (clientSentence.substring(0, 1)) {
				case "i": {
					h.put(clientSentence.substring(2), connectionSocket.getInetAddress().toString());
					outToClient.writeBytes("IP oprettet" + "\n");
					break;
				}
				case "s": {
					String svar = h.get(clientSentence.substring(2));
					outToClient.writeBytes(svar + "\n");
					break;
				}
				case "a": {
					for (var element : h.entrySet()) {
						outToClient.writeBytes(element.getKey() + ":" + element.getValue() + "       ");
					}
					;
					outToClient.writeBytes("\n");
					break;
				}
				default:
					outToClient.writeBytes("Ukendt kommando" + "\n");

			}
		}
	}

}
