import java.io.IOException;


public class Driver {

	public static void main (String args[]) throws IOException{
		if (args.length != 1) {
			System.err.println("You broke it.");
			System.exit(1);
		}

		String ciphertextPath = args[0];
		
		char[] ciphertext = Tools.readFile(ciphertextPath);
		int size=255;
		
		//Caesar c = new Caesar(ciphertext, size);
		//c.bruteForce();
		
		Substitution s = new Substitution(ciphertext, size);
		s.crack(3);
	}
	
}
