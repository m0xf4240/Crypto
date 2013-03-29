import java.io.IOException;
import java.util.*;

public class Substitution {
	private char[] ciphertext;
	private int[] inttext;
	private int spaceSize;
	private double[] standardText;
	private int[] standardTextHighFreq;

	public Substitution(char[] ciphertext, int size, double[] standardText){
		this.setCiphertext(ciphertext);
		//		this.makeIntText();
		this.setSpaceSize(size);
		this.setStandardText(standardText);
		this.setStandardTextHighFreq(findLargestCounts(standardText, 10));
	}

	public void crack(int nGramSize) throws IOException{
		double[] nGramFreq = findNGramsFreq(nGramSize, getCiphertext());
		char[] guess = randomGuess(getSpaceSize());
		guess = smartGuess(guess, nGramFreq);
		char[] clearText = decrypt(getCiphertext(), guess);
		nGramFreq = findNGramsFreq(nGramSize, clearText);
		double fit = score(nGramFreq);
		int counter = 0;
		while (counter<1000) {
			char[] nextGuess = changeGuess(guess);
			clearText = decrypt(getCiphertext(), nextGuess);
			nGramFreq = findNGramsFreq(nGramSize, clearText);
			double nextFit = score(nGramFreq);
			if(nextFit<fit) {
				guess = nextGuess;
				fit = nextFit;
				counter = 0;
			}
			counter++;
		}
		for(int i=0; i<guess.length; i++){
			System.out.print(guess[i]);
		}
	}


	private char[] changeGuess(char[] guess) {
		int a = (int)(Math.random()*255);
		int b = (int)(Math.random()*255);

		char temp = guess[a];
		guess[a]=guess[b];
		guess[b]=temp;

		return guess;
	}

	private double score(double[] nGramFreq) {		// not sure about this
		int sqSum = 0;
		for (int i=0; i<nGramFreq.length; i++){
			sqSum += Math.pow(nGramFreq[i]-this.getStandardText()[i], 2);
		}
		return Math.sqrt(sqSum);
	}

	private char[] randomGuess(int size) {
		char[] guess = new char[size];
		for (int i=0; i<size; i++){		// works
			guess[i] = (char)((i)%size);
		}
		System.out.println(guess);
		List<Character> g = new ArrayList<Character>();
		for(char ch : guess){
			g.add(ch);
		}
		Collections.shuffle(g);
		int i=0;
		for(Character ch : g) {			// appears to work
			guess[i++] = ch;
		}
		return guess;
	}

	private char[] smartGuess(char[] guessSpace, double[] nGramCounts) {
		
//		double[] largest = new double[10];
//		int[] largestIndecies = new int[largest.length];
//
//		for (int i=0; i<largest.length; i++){
//			largest[i]=0;
//			largestIndecies[i]=0;
//		}
//
//		for (int i=0; i<nGramCounts.length; i++){
//			for (int j=0; j<largest.length; j++){
//				if (largest[j]>=nGramCounts[i]){
//					break;
//				}
//				if (j!=0){
//					largest[j-1]=largest[j];
//					largestIndecies[j-1]=largestIndecies[j];
//				}
//				largest[j]=nGramCounts[i];
//				largestIndecies[j]=i;
//			}
//		}	
		
		int[] largestIndecies = findLargestCounts(nGramCounts, 10);
		
		int[] highestEnglishFreq = this.getStandardTextHighFreq();

		for (int i=largestIndecies.length-1; i>=0; i--){
			char temp = guessSpace[largestIndecies[i]];
		}
		
		int max =0;
		int index =0;
		for (int j=0; j<getCiphertext().length; j++) {
			if (getCiphertext()[j] > max) {
				index = j;
			}
		}
		//		char temp = guess[index];
		//		guess[index]=guess[(int)4];
		//		guess[(int)' ']=temp;




		System.out.println(guess);
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

	private double[] findNGramsFreq(int n, char[] cleartext){
		double[] freq = new double[(int) Math.pow(this.getSpaceSize(),n)];

		for (int i=0; i<freq.length; i++){
			freq[i]=0;
		}
		int total=0;
		for(int i = 0; i<cleartext.length-(n-1); i++){
			char first = (cleartext[i]);
			char second = (cleartext[i+1]);
			char third;
			if (n==2){
				freq[first * (int)Math.pow(this.getSpaceSize(),n-1) + second] +=1;
			}
			else if (n==3){
				third = (cleartext[i+2]);
				freq[first * (int)Math.pow(this.getSpaceSize(),n-1) + second*(int)Math.pow(this.getSpaceSize(),n-2) + third] +=1;
			}

			total++;
		}
		for (int i=0; i<freq.length; i++){
			freq[i]=freq[i] / total;
		}		

		return freq;
	}
	
	private int[] findLargestCounts(double[] nGramCounts, int howMany){
		double[] largest = new double[howMany];
		int[] largestIndecies = new int[largest.length];

		for (int i=0; i<largest.length; i++){
			largest[i]=0;
			largestIndecies[i]=0;
		}

		for (int i=0; i<nGramCounts.length; i++){
			for (int j=0; j<largest.length; j++){
				if (largest[j]>=nGramCounts[i]){
					break;
				}
				if (j!=0){
					largest[j-1]=largest[j];
					largestIndecies[j-1]=largestIndecies[j];
				}
				largest[j]=nGramCounts[i];
				largestIndecies[j]=i;
			}
		}
		
		return largestIndecies;
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

	public double[] getStandardText() {
		return standardText;
	}

	public void setStandardText(double[] standardText) {
		this.standardText = standardText;
	}

	public int[] getStandardTextHighFreq() {
		return standardTextHighFreq;
	}

	public void setStandardTextHighFreq(int[] standardTextHighFreq) {
		this.standardTextHighFreq = standardTextHighFreq;
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
