import java.io.*;
import java.util.Scanner;

public class LoadText {

	private File file;
	private int spaceSize;
	private int[] abCounts;
	
	public LoadText(File file, int spaceSize){
		this.file = file;
		this.spaceSize = spaceSize;
		abCounts = new int[this.spaceSize*this.spaceSize];
		initABC();
	}
		
	private void initABC() {
		for (int i=0; i<this.abCounts.length; i++){
			this.abCounts[i]=0;
		}
		
		Scanner scanner;
		try {
			scanner = new Scanner(this.file);
			for (int i=0; i<abCounts.length; i++){
				abCounts[i]=scanner.nextInt();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] getCount(){
		return this.abCounts;
	}
}

