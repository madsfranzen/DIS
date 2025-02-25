package Opgave12;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class TCPClient {

	public static void main(String[] args) throws Exception, IOException {

		ArrayList<Person> arrStringToSend = new ArrayList<Person>();
		arrStringToSend.add(new Person(1, "Bob", "Washington"));
		arrStringToSend.add(new Person(2, "Alice", "New York"));
		arrStringToSend.add(new Person(3, "Charlie", "Los Angeles"));

		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		outToServer.writeBytes(packArrayList(arrStringToSend));

		clientSocket.close();

	}

	private static String packArrayList(ArrayList<Person> arrToSend) {
		String packedString = "";

		for (Person person : arrToSend) {
			packedString += person.getId() + "," + person.getName() + "," + person.getCity() + ";";
		}

		return packedString;
	}

	

}
