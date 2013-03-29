import java.io.File;
import java.io.IOException;


public class Driver {

	public static void main (String args[]) throws IOException{
		if (args.length != 2) {
			System.err.println("You broke it.");
			System.exit(1);
		}
		
		String ciphertextPath = args[0];
		String nAsString = args[1];
		String monoTextPath = "new1.txt";
		String baseLineTextPath = "new"+nAsString+".txt";
		
		System.in.read();

//		String ciphertextPath = args[0];
//		String monoTextPath = args[1]; //new1.txt
//		String baseLineTextPath = args[2]; //new2.txt or new3.txt
//		String nAsString = args[3];
		
		int n = Integer.parseInt(nAsString);
		
		int size=255;
		char[] ciphertext = Tools.readFile(ciphertextPath);
		
		File file = new File(monoTextPath);
		LoadText goneWithThePeaceMono = new LoadText(file, 1);
		file = new File(baseLineTextPath);
		LoadText goneWithThePeace = new LoadText(file, (int)Math.pow(size, n));
		
		System.in.read();
		
		//Caesar c = new Caesar(ciphertext, size);
		//c.bruteForce();
		
		Substitution s = new Substitution(ciphertext, size, goneWithThePeace.getFreq());
		s.crack(n, goneWithThePeaceMono.getFreq());
	}
	
}
