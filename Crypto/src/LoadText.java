import java.io.*;
import java.util.Scanner;

public class LoadText {

	public static void main (String args[]) throws IOException{
		//Error checking
		if (args.length != 1) {
			System.err.println("Feed me.");
			System.exit(1);
		}
		File file = new File(args[0]);

		//Declare variables
		int spaceSize=255;
		int[] abCounts = new int[spaceSize*spaceSize];

		//Initialize count array to 0
		for (int i=0; i<abCounts.length; i++){
			abCounts[i]=0;
		}

		Scanner scanner = new Scanner(file);
		for (int i=0; i<abCounts.length; i++){
			abCounts[i]=scanner.nextInt();
		}

		for (int i=0; i<abCounts.length; i++){
			if (abCounts[i]!=0){
				int temp=i/spaceSize;
				System.out.print((char)(temp));
				System.out.print((char)(i%spaceSize)+":");
				System.out.println(abCounts[i]);
			}
		}

	}
}

