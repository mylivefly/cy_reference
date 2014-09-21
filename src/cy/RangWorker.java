package cy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
		String rank, comment, cost;
		for (String num : numbers) {
			url = "http://www.bestopview.com/fengxi/" + num + ".html";
			try {
				//rank = getRank(url);
				Document doc = Jsoup.parse(get(url, "GB2312"));
				Elements web2 = doc.getElementsByClass("web2"); 
				rank = web2.get(6).getElementsByClass("font10").get(2).text();
				comment = doc.getElementsByClass("lastbgcolor").get(0).text();
				if (Integer.parseInt(rank) >= 30 && !comment.equals("停牌")) {
					cost = web2.get(3).getElementsByClass("font10").get(4).text();
					ctx.println("<a target='_blank' href='" + url + "'>" + num + "</a>");
					ctx.println(", rank: " + rank);
					ctx.println(", cost: " + cost);
					ctx.println("<br/>" + comment + "<br/>");
					System.out.println(num + ": " + rank);
				}
			} catch (Exception e) {
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
	
	
	String get(String urlStr, String encoding) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
		BufferedReader br = null;
		//System.out.println(conn.getResponseCode());
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			String line = null;
			while ((line=br.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			if (br != null)  br.close();
			if (conn != null) conn.disconnect();
		}
		return sb.toString();
	}
	
}
