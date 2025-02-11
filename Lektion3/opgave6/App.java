package opgave6;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		System.out.println("OPGAVE 6:\n");

		Text text = new Text();
		Scanner scan = new Scanner(System.in);
		Reader reader = new Reader(scan, text);
		Writer writer = new Writer(text);
		
		writer.start();
		reader.start();
	}
}
