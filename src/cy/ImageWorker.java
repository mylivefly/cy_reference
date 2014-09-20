package cy;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageWorker {

	static String urlBase = "http://www.gpcxw.com/gpcx-img/superview/";
	static String urlTail = ".gif";
	
	public static void main(String[] args) {
		try {
			execute("C:/downloads/caoying/list.txt", "0912", "C:/downloads/caoying/images");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void execute(String listFile, String dateStr, String outputDir) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(listFile));
			String line;
			while ((line=br.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0) continue;
				try {
					getImage(line, dateStr, outputDir);
				} catch (Exception e) {
					System.out.println("error: " + line);
					e.printStackTrace();
				}
			}
		} finally {
			if (br != null) br.close();
		}
	}

	private static void getImage(String number, String dateStr, String outputDir) throws IOException {
		FileOutputStream fos = null;
		String urlStr = urlBase + dateStr + "/" + number + urlTail;
		URL url = new URL(urlStr);
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");  
			conn.setConnectTimeout(5 * 1000);  
			InputStream inStream = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			fos = new FileOutputStream(outputDir + "/" + number + ".gif");
			while( (len=inStream.read(buffer)) != -1 ){  
				fos.write(buffer, 0, len);  
			}  
			inStream.close();
		} finally {
			if (fos != null) fos.close();
			if (conn != null) conn.disconnect();
		}
	}
	
	
}
