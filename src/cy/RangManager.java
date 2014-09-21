package cy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RangManager {

	public static void main(String[] args) {
		try {
			execute("C:/downloads/caoying/list2.txt", "C:/downloads/caoying/rank.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void execute(String inFile, String outFile) throws IOException {
		BufferedReader br = null;
		Context ctx = new Context(outFile);
		int threadCount = 10;
		ArrayList[] numLists = new ArrayList[threadCount];
		for (int i=0; i<threadCount; i++) {
			numLists[i] = new ArrayList<String>();
		}
		try {
			br = new BufferedReader(new FileReader(inFile));
			String line;
			int count = 0;
			while ((line=br.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0) continue;
				numLists[count%threadCount].add(line);
				count++;
			}
			for (int i=0; i<threadCount; i++) {
				new RangWorker(ctx, numLists[i]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
