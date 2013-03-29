import java.io.File;
import java.io.IOException;


public class Driver {

	public static void main (String args[]) throws IOException{
		if (args.length != 3) {
			System.err.println("You broke it.");
			System.exit(1);
		}

		String ciphertextPath = args[0];
		String baseLineTextPath = args[1];
		String nAsString = args[2];
		
		int n = Integer.parseInt(nAsString);
		
		int size=255;
		char[] ciphertext = Tools.readFile(ciphertextPath);
		File file = new File(baseLineTextPath);
		LoadText goneWithThePeaceMono = new LoadText(file, 1);
		LoadText goneWithThePeace = new LoadText(file, (int)Math.pow(size, n));
		
		
		
		//Caesar c = new Caesar(ciphertext, size);
		//c.bruteForce();
		
		Substitution s = new Substitution(ciphertext, size, goneWithThePeace.getFreq());
		s.crack(n, goneWithThePeaceMono.getFreq());
	}
	
}
