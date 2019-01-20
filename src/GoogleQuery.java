

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;

	public GoogleQuery(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q=" + searchKeyword + "&oe=utf8&num=20";
	}

	private String fetchContent() throws IOException {
		String retVal = "";
		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		InputStream in = conn.getInputStream();
		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);

		String line = null;
		while ((line = bufReader.readLine()) != null) {
			retVal += line;
		}

		return retVal;
	}

	public HashMap<String, String> query() throws IOException {
		if (content == null) {
			content = fetchContent();
		}
		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);
  
		Elements lis = doc.select("div.g");

		for (Element li : lis) {
			try {
				Element h3 = li.select("h3.r").get(0);
				String title = h3.text();

				Element cite = li.select("cite").get(0);
				String citeUrl = cite.text();
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {

			}

		}

		return retVal;
	}

}