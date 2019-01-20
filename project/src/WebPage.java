

import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
	public String url;
	public String name;
	public KeywordCounter counter;  
	public double score;

	public WebPage(String url, String name) {
		this.url = url;
		this.name = name;
		this.counter = new KeywordCounter(url);
	}

	public void setScore(ArrayList<Keyword> keywords) {
		this.score = 0;

		try {
			for (Keyword k : keywords) {
				if (url.length() > 50) {
					this.score += counter.countKeyword(k.name) * k.weight * 8;
					if (url.contains("wifi")) {
						this.score *= 0.5;
					} else if (url.contains("MRT")) {
						this.score *= 0.6;
					}	else if (url.contains("power outlet")) {
							this.score *= 0.7;
					}
							else if (url.contains("Taipei")) {
								this.score *= 0.8;
					
					}
				} else {
					this.score += counter.countKeyword(k.name) * k.weight;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return name + " [" + score + "]\n" + url + "\n";
	}
}
