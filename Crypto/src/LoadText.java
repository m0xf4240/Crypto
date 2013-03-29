import java.io.*;
import java.util.Scanner;

public class LoadText {

	private File file;
	private int spaceSize;
	private double [] abCounts;
	
	public LoadText(File file, int spaceSize){
		this.file = file;
		this.spaceSize = spaceSize;
		abCounts = new double[this.spaceSize];
		initABC();
	}
		
	private void initABC() {
		for (int i=0; i<this.abCounts.length; i++){
			this.abCounts[i]=0;
		}
		
		Scanner scanner;
		try {
			int total=0;
			scanner = new Scanner(this.file);
			for (int i=0; i<abCounts.length; i++){
				int nextInt = scanner.nextInt();
				abCounts[i]=nextInt;
				total+=nextInt;
			}
			for (int i=0; i<abCounts.length; i++){
				abCounts[i]=abCounts[i]/total;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double[] getFreq(){
		return this.abCounts;
	}
}

