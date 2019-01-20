
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlMatcher {
	private String url;
	private String content;
	private String searchKeyword; // keyword 加上cafe.wifi.MRT....後是Google搜尋網頁時用的字串
	private String searchKeyword2; // keyword 用來查Google相關關鍵字用
	public static ArrayList<WebPage> web; // Google和搜尋找到的各個網頁
	public static String[] relatedKeyword;

	public HtmlMatcher(String kind, String searchKeyword) throws IOException {
		this.searchKeyword2 = searchKeyword;
		web = new ArrayList<>();
		{
			if (searchKeyword != "cafe") {
				this.searchKeyword = searchKeyword + "+cafe";
			}

		}
		query(); // search google
		fetchRelatedKeyword(); // 抓取google推薦關鍵字
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.url);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-TW; rv:1.9.1.2) "
				+ "Gecko/20090729 Firefox/3.5.2 GTB5 (.NET CLR 3.5.30729)");
		conn.connect();
		InputStream in = conn.getInputStream();
		InputStreamReader ir = new InputStreamReader(in, "UTF8");
		BufferedReader br = new BufferedReader(ir);

		String retVal = "";
		String line = null;

		while ((line = br.readLine()) != null) {
			retVal += line;
		}

		return retVal;
	}

	public void query() throws IOException {
		this.url = "https://www.google.com.tw/search?q=" + searchKeyword + "&oe=utf8num=15";
		this.content = fetchContent();

		Document document = Jsoup.parse(this.content);
		Elements lis = document.select("div.g");
		for (Element li : lis) {
			try {
				Element h3 = li.select("h3.r").get(0);
				String title = h3.text();

				Element cite = li.getElementsByTag("a").first();
				String citeUrl = cite.attr("href");
				citeUrl = citeUrl.substring(7, citeUrl.indexOf("&sa=U&ved"));

				web.add(new WebPage(citeUrl, title));

			} catch (IndexOutOfBoundsException e) {

			}
		}
	}

	public void fetchRelatedKeyword() throws IOException {
		this.url = "https://www.google.com.tw/search?q=" + searchKeyword2 + "cafe" + "&oe=utf8num=10";
		content = fetchContent();
		int indexOfOpen = content.indexOf("clear:");

		if (indexOfOpen == -1) {
			String[] s = new String[1];
			s[0] = "(Not found)";
			relatedKeyword = s;
		} else {
			int indexOfHtmlClose = -1;
			int indexOfHtml = -1;
			int indexOfTitleClose = -1;
			String title = "";
			indexOfHtmlClose = content.indexOf(">", indexOfOpen);

			String[] s = new String[5];
			for (int i = 0; i < 5; i++) {
				indexOfHtml = content.indexOf("a href=", indexOfHtmlClose);
				indexOfHtmlClose = content.indexOf(">", indexOfHtml);
				indexOfTitleClose = content.indexOf("<", indexOfHtmlClose);
				title = content.substring(indexOfHtmlClose + 1, indexOfTitleClose);
				int k = i + 1;
				s[i] = k + ". " + title;
			}
			relatedKeyword = s;
		}
	}
}