package cy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ListWorker {

	public static void main(String[] args) {
		try {
			//execute("C:/downloads/caoying/list2.txt", "C:/downloads/caoying/rank.html");
			String url = "http://www.bestopview.com/fengxi/000333.html";
			System.out.println(getRank(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void execute(String inFile, String outFile) throws IOException {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(outFile);
			br = new BufferedReader(new FileReader(inFile));
			String line;
			String rank;
			String url;
			pw.println("<html>");
			pw.println("<body>");
			while ((line=br.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0) continue;
				url = "http://www.bestopview.com/fengxi/" + line + ".html";
				try {
					rank = getRank(url);
					if (Integer.parseInt(rank) >= 30) {
						pw.println("<a target='_blank' href='" + url + "'>" + line + "</a>");
						System.out.println(line);
					}
				} catch (Exception e) {
					System.out.println("error: " + line);
					e.printStackTrace();
				}
			}
			pw.println("</body>");
			pw.println("</html>");
			pw.flush();
		} finally {
			if (br != null) br.close();
			if (pw != null) pw.close();
		}
	}
	
	static String getRank(String sUrl) throws IOException {
		//String agent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36";
		//Document doc = Jsoup.connect(sUrl).userAgent(agent).get();
		Document doc = Jsoup.parse(get(sUrl, "GB2312"));
		Elements es2 = doc.getElementsByClass("lastbgcolor");
		System.out.println(es2.get(0).text());
		Elements es = doc.getElementsByClass("web2");
		return es.get(6).getElementsByClass("font10").get(2).text();
	}
	
	static String get(String urlStr, String encoding) throws IOException {
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
