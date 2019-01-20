

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Gui")
public class Gui extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String buttonString;
	private static final double weight = 20;
	private static final double weight2 = 10;
	private static final double weight3 = 2;
	public Keyword keyword;
	public ArrayList<Keyword> keywords;
	public WebList news;

	public Gui() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if (request.getParameter("keyword") == null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("search.jsp").forward(request, response);
			return;
		}

		keyword = new Keyword();
		keywords = new ArrayList<>();
		String input = request.getParameter("keyword");
		int m = input.indexOf(" ");
		String input1 = input;
		String input2 = "wifi";
		String text = "cafe";  
		String text2 = "wifi";
		String text3 = "MRT";
		
		buttonString = "cafe";

		if (!input.isEmpty()) {
			System.out.print(text);
			text= text2;
			while (input1.contains(" ")) {
				m = input1.indexOf(" ");
				input2 = input1.substring(m + 1, input1.length());
				input1 = input1.substring(0, m);

				keyword.addKeyword(new Keyword(input1, weight, buttonString));
				text = text + "+" + input1;
				input1 = input2;
			}
			keyword.addKeyword(new Keyword(input1, weight, buttonString));
			text = text + "+" + input1;
		}

		if (buttonString != "") {
			keyword.addKeyword(new Keyword(text2, weight2, buttonString));
			if (input.isEmpty()) {
				text = text2;
			
			}
		} else {
			keyword.addKeyword(new Keyword(text3, weight3, buttonString));
			if (input.isEmpty()) {
				text = text3;
			}
		}

		keywords = keyword.keywords;
		try {
			new HtmlMatcher(buttonString, text);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			news = new WebList(keywords);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String[] related = HtmlMatcher.relatedKeyword;
		request.setAttribute("related", related);

		String[][] result = news.sort();	
		request.setAttribute("result", result);

		request.getRequestDispatcher("googleitem.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
