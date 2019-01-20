
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class KeywordCounter {    ///算次數
	private String url;
	private String content;

	public KeywordCounter(String url) {
		this.url = url;
	}

	private String fetchContent() throws IOException {
		String retVal = "";

		try {
			URL url = new URL(this.url);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0(Macintosh;U;Intel Mac OS X 10.4; en-US;rv:1.9.2.2)Gecko/20100316 Firefox/3.6.2");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				retVal += line;
			}
			return retVal;

		} catch (IOException e) {
			retVal = "  ";
			return retVal;

		} catch (IllegalArgumentException e) {
			retVal = "  ";
			return retVal;
		}

	}

	public int countKeyword(String keyword) throws IOException {
		if (content == null) {
			content = fetchContent();
		}

		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int freq = 0;
		int num = content.indexOf(keyword, 0);
		while (num >= 0) {
			num = content.indexOf(keyword, num + keyword.length());
			freq++;
		}
		return freq;
	}
}
