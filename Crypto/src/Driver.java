import java.io.*;
import java.util.*;
//test again
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
		Vigenere v=new Vigenere(message);
		System.out.println("Vig made");
		Caesar c=new Caesar(v.results,book);
		System.out.println("Cae made");
		LinkedList<Byte[]> thign=v.flip(c.getVig());
		
		for(int i=0;i<thign.size();i++){
			System.out.print(thign.get(i).toString());
				}
		
		
		
		
//		BookAnalyzer ba = new BookAnalyzer();
//		HashMap<Byte, Integer> englishMono = ba.analyzeByte(book);
//		HashMap<Integer, Integer> englishQuad = ba.analyze(book,4);
//		
//		HashMap<Integer, Integer> cipherText = ba.analyze(message,1);
//		
//		HashMap<Integer, Integer> test = ba.analyze(new File("warandpeace.txt"), 4);
////		HashMap<Short, Integer> englishBigrams = ba.analyzeBigrams(book);
//		LoadCipher lt = new LoadCipher();
//		ArrayList<Byte> cipher = lt.load(message);
		
		System.out.println();
//		System.in.read();

		//Caesar c = new Caesar(ciphertext, size);
		//c.bruteForce();
		
		Substitution s = new Substitution(book, message);
		s.crack();
	}
	
}
