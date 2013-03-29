import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AddCountFiles {

	public static void main (String args[]) throws IOException{

		if (args.length != 2) {
			System.err.println("Feed me.");
			System.exit(1);
		}
		File war = new File(args[0]);
		File wind = new File(args[1]);
		
		BufferedReader warReader = null;
		BufferedReader windReader = null;
		
		Scanner warScanner = new Scanner(war);
		Scanner windScanner = new Scanner(wind);
		
		FileWriter fstream = new FileWriter("Frequencies.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		
		try {
			String sCurrentLine;
			int[] counts = new int[255*255];
			int where = 0;

			warReader = new BufferedReader(new FileReader(war));
			windReader = new BufferedReader(new FileReader(wind));
 
			for (int i=0; i<counts.length; i++){
				counts[i] = warScanner.nextInt() + windScanner.nextInt();
			}

			for (int i=0; i<counts.length; i++){
				out.write(counts[i]+"\n");
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.close();
		
	}
	
}
