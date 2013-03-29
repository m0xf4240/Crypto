import java.io.IOException;
import java.util.*;

public class Substitution {
	private char[] ciphertext;
	private int[] inttext;
	private int spaceSize;
	private int[] standardText;

	public Substitution(char[] ciphertext, int size, int[] standardText){
		this.setCiphertext(ciphertext);
//		this.makeIntText();
		this.setSpaceSize(size);
		this.setStandardText(standardText);
	}

	public void crack(int nGramSize) throws IOException{
		char[] guess = randomGuess(getSpaceSize());
		char[] clearText = decrypt(getCiphertext(), guess);
//		TODO: Remove next line
		nGramSize = 3;
		ArrayList<NGram> nGramCounts = findNGrams(nGramSize, clearText);
		double fit = score(nGramCounts);
		int counter = 0;
		while (counter<1000) {
			char[] nextGuess = changeGuess(guess);
			clearText = decrypt(getCiphertext(), nextGuess);
			nGramCounts = findNGrams(nGramSize, clearText);
			double nextFit = score(nGramCounts);
			if(nextFit>fit) {
				guess = nextGuess;
				fit = nextFit;
				counter = 0;
				System.out.println("hi"+clearText);	//not getting here
			}
			counter++;
			for(int i=0; i<guess.length; i++){
				System.out.print(guess[i]);
			}
			System.out.println();
		}
		// clearText contains the best decryption found
		//System.in.read();
	}

	
	private char[] changeGuess(char[] guess) {
		char[] g = new char[guess.length];
		for(int i=0; i<guess.length; i++) {
			g[i] = guess[i];
		}
		char a = (char)(Math.random()*255);
		char b = (char)(Math.random()*255);
		for(int i=0; i<g.length; i++) {		// swaps two random spots in g
			if(g[i] ==a) {
				g[i] = b;
			}
			if(g[i] ==b) {
				g[i] = a;
			}
		}
		return g;
	}

	private double score(ArrayList<NGram> nGramCounts) {		// not sure about this
		// TODO Auto-generated method stub
		return 0;
	}

	private char[] randomGuess(int size) {
		char[] guess = new char[size];
		for (int i=0; i<size; i++){		// works
			guess[i] = (char)((i)%size);
		}
		//System.out.println(guess);
		List<Character> g = new ArrayList<Character>();
		for(char ch : guess){
			g.add(ch);
		}
		//Collections.shuffle(g);
		int i=0;
		for(Character ch : g) {			// appears to work
			guess[i++] = ch;
		}
		
		//System.out.println(guess);
		return guess;
	}

	private char[] decrypt(char[] cipher, char[] guess){
		char[] clear = new char[cipher.length];
		for(int i=0; i<cipher.length; i++){
			for (int j=0; j<guess.length; j++){
				if (cipher[i]==guess[j]){
					clear[i]=(char)j;
					System.out.print(clear[i]);
					break;
				}
			}
		}
		System.out.println();
		return clear;
	}
	
	private ArrayList<NGram> findNGrams(int n, char[] cleartext){
		ArrayList<NGram> counts = new ArrayList<NGram>(0);
		System.out.println("count size " + counts.size());
		
		for(int i = 0; i<cleartext.length-(n-1); i++){
			String s = ""+(cleartext[i]);
			for (int j=1; j<n; j++){
				s = s+cleartext[i+j];
			}
			boolean foundMatch=false;
			for (int k=0; k<counts.size(); k++){
				if (s.equals(counts.get(k).getS())){
					counts.get(k).setCount(counts.get(k).getCount()+1);
					foundMatch=true;
					break;
				}
			}
			if (!foundMatch){
				counts.add(new NGram(s));
			}
		}
		counts.trimToSize();
		return counts;
	}
	
//	private void makeIntText(){
//		int[] i = new int[this.getSpaceSize()];
//		for (int j=0; j<this.getCiphertext().length; j++){
//			i[j]= (int)this.getCiphertext()[j];
//		}
//		setInttext(i);
//	}

	public char[] getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(char[] ciphertext) {
		this.ciphertext = ciphertext;
	}
	
	public int[] getInttext() {
		return inttext;
	}

	public void setInttext(int[] inttext) {
		this.inttext = inttext;
	}

	public int getSpaceSize() {
		return spaceSize;
	}

	public void setSpaceSize(int spaceSize) {
		this.spaceSize = spaceSize;
	}

	public int[] getStandardText() {
		return standardText;
	}

	public void setStandardText(int[] standardText) {
		this.standardText = standardText;
	}

	public int[] frequency() {
		int[] counts = new int[this.getSpaceSize()];
		for (int i=0; i<this.getCiphertext().length; i++) {
			int num = (int)(getCiphertext()[i]);
			counts[num] += 1;
		}
		return counts;
	}
	
}
