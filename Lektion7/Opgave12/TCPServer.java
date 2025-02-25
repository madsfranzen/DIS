package Opgave12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {

	public static void main(String[] args) throws Exception {

		String arrStringFromClient;
		@SuppressWarnings("resource")
		ServerSocket welcomSocket = new ServerSocket(6789);

		while (true) {
			Socket connectionSocket = welcomSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			arrStringFromClient = inFromClient.readLine();
			connectionSocket.close();

			ArrayList<Person> arrayListFromClient = unpackArrString(arrStringFromClient);

			System.out.println(arrayListFromClient.toString());
		}
	}

	private static ArrayList<Person> unpackArrString(String arrStringFromClient) {
		ArrayList<Person> arrayListFromClient = new ArrayList<Person>();

		String[] persons = arrStringFromClient.split(";");
		for (String person : persons) {
			String[] personData = person.split(",");
			arrayListFromClient.add(new Person(Integer.parseInt(personData[0]), personData[1], personData[2]));
		}

		return arrayListFromClient;
	}

}

