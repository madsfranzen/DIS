package opgave6;

import java.util.Scanner;

public class Reader extends Thread {

	Scanner scan;
	Text text;

	public Reader(Scanner scan, Text text) {
		super();
		this.scan = scan;
		this.text = text;
	}

	public void run(){
		while(true){
			text.setText(scan.next());
		}
	}
};
