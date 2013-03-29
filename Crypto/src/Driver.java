import java.io.File;
import java.io.IOException;


public class Driver {

	public static void main (String args[]) throws IOException{
		if (args.length != 2) {
			System.err.println("You broke it.");
			System.exit(1);
		}

		String ciphertextPath = args[0];
		String baseLineTextPath = args[1];
		
		int size=255;
		char[] ciphertext = Tools.readFile(ciphertextPath);
		File file = new File(baseLineTextPath);
		LoadText goneWithThePeace = new LoadText(file, size);
		
		
		
		//Caesar c = new Caesar(ciphertext, size);
		//c.bruteForce();
		
		Substitution s = new Substitution(ciphertext, size, goneWithThePeace.getFreq());
		s.crack(3);
	}
	
}
