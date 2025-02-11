package opgave6;

public class Writer extends Thread {

	private Text text;

	public Writer(Text text) {
		super();
		this.text = text;
	}

	public void run() {
		while (true) {
			System.out.println(this.text.getText());
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
