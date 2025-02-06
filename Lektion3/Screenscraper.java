import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Screenscraper {
	public static void main(String[] args) throws IOException {
		System.out.println("SCREEN SCRAPER");

		@SuppressWarnings("deprecation")
		URL url = new URL("https://valutakurser.dk");
		InputStreamReader r = new InputStreamReader(url.openStream());
		BufferedReader in = new BufferedReader(r);
		String str;
		while ((str = in.readLine()) != null) {
			if (str.length() > 0) {
				if (str.charAt(0) != '<' && str.charAt(str.length() - 1) != '>') {
					String [] strArr = str.split(" ");
					System.out.println(strArr[5]);
				}
			}
		}
		in.close();

	}
}
