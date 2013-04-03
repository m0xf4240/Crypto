import java.io.*;
import java.util.*;

public class Driver {

	public static void main (String args[]) throws IOException{
		if (args.length != 2) {
			System.err.println("Usage error. You should run java Driver <book.txt> <ciphertext.txt>");
			System.exit(1);
		}
		File book = new File(args[0]);	
		File message = new File(args[1]);
		//Error checking
		if (!book.exists()) {
			System.out.println(args[0] + " does not exist.");
			return;
		}
		if (!(book.isFile() && book.canRead())) {
			System.out.println(book.getName() + " cannot be read from.");
			return;
		}
		if (!message.exists()) {
			System.out.println(args[1] + " does not exist.");
			return;
		}
		if (!(message.isFile() && message.canRead())) {
			System.out.println(message.getName() + " cannot be read from.");
			return;
		}
		BookAnalyzer ba = new BookAnalyzer();
		HashMap<Byte, Integer> englishLanguage = ba.analyze(book);
		HashMap<Byte, Integer> cipherText = ba.analyze(message);
		LoadCipher lt = new LoadCipher();
		ArrayList<Byte> cipher = lt.load(message);
		
		System.out.println();
//		System.in.read();
		
		//Caesar c = new Caesar(ciphertext, size);
		//c.bruteForce();
		
		Substitution s = new Substitution(cipher, cipherText, englishLanguage);
		s.crack();
	}
	
}
