
import java.util.ArrayList;

public class Keyword {
	public String name;
	public double weight;
	public String kind;
	public ArrayList<Keyword> keywords;

	public Keyword() {	
		keywords = new ArrayList<>();
	}
	
	public Keyword(String name, double weight, String kind) {
		this.name = name;
		this.weight = weight;
		this.kind = kind;
	}

	public void addKeyword(Keyword k) {
		keywords.add(k);
	}
}

