package Opgave13;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
	Socket connSocket;

	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
	}

	public void run() {
		try {
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			String clientRequest = inFromClient.readLine();
			System.out.println("Received from client: " + clientRequest);

			byte[] responseBody = read("/Users/madsfranzen/Documents/DIS/Lektion6/Serverskeleton/myWEB/test2.html");
			String returnString = "HTTP/1.1 200 OK\r\n";
			String contentType = "Content-Type: text/html\r\n";
			String contentLength = "Content-Length: " + responseBody.length + "\r\n";
			String close = "Connection: Close\r\n";

			outToClient.writeBytes(returnString + contentType + contentLength + close + "\r\n");
			outToClient.write(responseBody);
			outToClient.flush();
			System.out.println("Response sent to client");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				connSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	byte[] read(String aInputFileName) throws FileNotFoundException {
		// returns the content of a file in a binary array
		System.out.println("Reading in binary file named : " + aInputFileName);
		File file = new File(aInputFileName);
		System.out.println("File size: " + file.length());
		byte[] result = new byte[(int) file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));

				while (totalBytesRead < result.length) {
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
					// input.read() returns -1, 0, or more :
					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				System.out.println("Num bytes read: " + totalBytesRead);
			} finally {
				System.out.println("Closing input stream.");
				if (input != null) {
					input.close();
				}
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
