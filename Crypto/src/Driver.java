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
		
		Caesar c = new Caesar(ciphertext, size);
		
		for (int key=0; key<256; key++){
			char[] cleartext = c.decrypt(key);
			for (int i=0; i<cleartext.length; i++){
				System.out.print(cleartext[i]);
			}
			System.out.println("\n");
			
			//Press enter to continue
			System.in.read();
		}
	}
	
}
