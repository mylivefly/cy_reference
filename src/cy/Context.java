package cy;

import java.io.IOException;
import java.io.PrintWriter;

public class Context {

	private int threadCount =  0;
	private PrintWriter pw = null;
	
	public Context(String outFile) throws IOException {
		pw = new PrintWriter(outFile);
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		pw.println("</head>");
		pw.println("<body>");
	}
	
	public void signIn() {
		threadCount++;
	}
	
	public void signOut() {
		threadCount--;
		if (threadCount == 0) {
			pw.println("</body>");
			pw.println("</html>");
			pw.close();
		}
	}
	
	public void println(String s) {
		pw.println(s);
		pw.flush();
	}
	
}
