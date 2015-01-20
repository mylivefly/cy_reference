package cy;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import util.ConnPool;

public class Context {

	private long startTime = System.currentTimeMillis();
	private int threadCount =  0;
	private PrintWriter pw = null;
	private ConnPool connPool;
	
	public Context(String outFile) throws IOException {
		pw = new PrintWriter(outFile);
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		pw.println("</head>");
		pw.println("<body>");
		
		connPool = new ConnPool("com.mysql.jdbc.Driver", 
				"jdbc:mysql://localhost:3306/stockdev", 
				"root", 
				"1234", 
				10);
	}
	
	public void signIn() {
		threadCount++;
	}
	
	public void signOut() {
		threadCount--;
		if (threadCount == 0) {
			pw.println("</body>");
			pw.println("</html>");
			pw.flush();
			pw.close();
			long end = System.currentTimeMillis();
			System.out.println("duration: " + (end-startTime)/1000 + "s");
		}
	}
	
	public void println(String s) {
		pw.println(s);
		pw.flush();
	}
	
	public Connection getConnection() {
		return connPool.getConnection();
	}
	
	public void releaseConnection(Connection conn) {
		connPool.releaseConnection(conn);
	}
	
}
