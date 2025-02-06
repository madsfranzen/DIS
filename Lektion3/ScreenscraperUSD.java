import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ScreenscraperUSD {
	public static void main(String[] args) throws IOException {
		System.out.println("SCREENSCRAPER USD\n");

		@SuppressWarnings("deprecation")
		URL url = new URL("https://valutakurser.dk");
		InputStreamReader r = new InputStreamReader(url.openStream());
		BufferedReader in = new BufferedReader(r);
		String str;
		while ((str = in.readLine()) != null) {
			String[] strArr = str.split("<");
			for (String subStr : strArr) {
				if (subStr.contains("currencyNameContainer")) {
					System.out.printf("%-25s", subStr.substring(54));
				}
				if (subStr.contains("actualValueContainer")) {
					System.out.print(" : ");
					System.out.print(subStr.substring(53, subStr.length()));
					System.out.println();
				}
			}
		}
		in.close();
	}
}
