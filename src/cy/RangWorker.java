package cy;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RangWorker extends Thread {

	private Context ctx;
	private List<String> numbers;

	public RangWorker(Context ctx, List<String> numbers) {
		this.ctx = ctx;
		this.numbers = numbers;
		this.start();
	}
	
	public void run() {
		ctx.signIn();
		String url;
		String rank;
		for (String num : numbers) {
			url = "http://www.bestopview.com/fengxi/" + num + ".html";
			try {
				rank = getRank(url);
				if (Integer.parseInt(rank) >= 30) {
					ctx.println("<a target='_blank' href='" + url + "'>" + num + "</a>");
					System.out.println(num + ": " + rank);
				}
			} catch (IOException e) {
				System.out.println("error: " + num);
				e.printStackTrace();
			}
		}
		ctx.signOut();
	}
	
	String getRank(String sUrl) throws IOException {
		String agent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36";
		Document doc = Jsoup.connect(sUrl).userAgent(agent).get();
		Elements es = doc.getElementsByClass("web2");
		return es.get(6).getElementsByClass("font10").get(2).text();
	}
	
}
