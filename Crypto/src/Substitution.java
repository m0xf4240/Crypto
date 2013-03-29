import java.io.IOException;
import java.util.*;

public class Substitution {
	private char[] ciphertext;
	private int spaceSize;
	private double[] standardText;

	public Substitution(char[] ciphertext, int size, double[] standardText){
		this.setCiphertext(ciphertext);
		this.setSpaceSize(size);
		this.setStandardText(standardText);
	}

	public void crack(int nGramSize, double[] englishMonos) throws IOException{
		double[] nGramFreq = findNGramsFreq(1, getCiphertext());
		char[] guess = randomGuess(getSpaceSize());
		guess = smartGuess(guess, nGramFreq, englishMonos);
		char[] clearText = decrypt(getCiphertext(), guess);
		nGramFreq = findNGramsFreq(nGramSize, clearText);
		double fit = score(nGramFreq);
		int counter = 0;
		while (fit>1) {
			char[] nextGuess = changeGuess(guess, fit);
			clearText = decrypt(getCiphertext(), nextGuess);
			nGramFreq = findNGramsFreq(nGramSize, clearText);
			double nextFit = score(nGramFreq);
			maybePrint(clearText, nextFit, fit);
			if(nextFit<fit) {
				guess = nextGuess;
				fit = nextFit;
				counter = 0;
				System.out.println("score: "+fit);
			}
			
			counter++;
		}
		for(int i=0; i<guess.length; i++){
			System.out.print(guess[i]);
		}
	}


	private char[] changeGuess(char[] guess, double fit) {
//		int a = (int)(Math.random()*255);
//		int b = (int)(Math.random()*255);
		for (int x=0; x<250; x++){
			if (fit<x){
				break;
			}
			else{
				swap((int)(Math.random()*255), (int)(Math.random()*255), guess);	
			}
		}
//		char temp = guess[a];
//		guess[a]=guess[b];
//		guess[b]=temp;
		return guess;
	}
	
	private char[] swap(int a, int b, char[] guess){
		char temp = guess[a];
		guess[a]=guess[b];
		guess[b]=temp;
		return guess;
	}

	private double score(double[] nGramFreq) {		// not sure about this
		double sqSum = 0;
		for (int i=0; i<nGramFreq.length; i++){
			//Assume there are no weird control characters, and this is plain english
//			if (this.getStandardText()[i]==0 	&& i!='/'  && i!=':'  && i!='~'  && i!='@'  && i!='{'  && i!='}'  && i!='['  && i!=']'  
//					&& i!='%'  && i!='$'  && i!='\\' && i!=9 && i!=10 && i!=8 && i!=13 && i!=29  
//					&& i!='+' && i!='<' && i!='>' && i!='^' && i!='_' && i!='`' && i!=175){
//			if (i==29670){
//				System.out.println();
//			}
			if (nGramFreq[i]>0 && this.getStandardText()[i]==0){
				sqSum += 100;
			}
			
			sqSum += Math.pow(nGramFreq[i]-this.getStandardText()[i], 4);
		}
		//System.out.println("score: "+Math.sqrt(sqSum));
		return Math.sqrt( Math.sqrt( Math.sqrt(sqSum)));
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
		Collections.shuffle(g);
		int i=0;
		for(Character ch : g) {			// appears to work
			guess[i++] = ch;
		}
		return guess;
	}

	private char[] smartGuess(char[] guess, double[] monoGramFreq, double[] englishMonos) {
		int[] cipherLargestFreqIndecies = findLargestFreqs(monoGramFreq, 10);
		int[] largestEnglishFreq = findLargestFreqs(englishMonos, 10);

		for (int i=cipherLargestFreqIndecies.length-1; i>=0; i--){
			char temp = guess[largestEnglishFreq[i]];
			guess[largestEnglishFreq[i]]=guess[cipherLargestFreqIndecies[i]];
			guess[cipherLargestFreqIndecies[i]]=temp;
		}
		return guess;
	}

	private char[] decrypt(char[] cipher, char[] guess){
		char[] clear = new char[cipher.length];
		for(int i=0; i<cipher.length; i++){
			for (int j=0; j<guess.length; j++){
				if (cipher[i]==guess[j]){
					clear[i]=(char)j;
					break;
				}
			}
		}
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
			char second;
			char third;
			if (n==1){
				freq[first] +=1;
			}
			else if (n==2){
				second = (cleartext[i+1]);
				freq[first * (int)Math.pow(this.getSpaceSize(),n-1) + second] +=1;
			}
			else if (n==3){
				second = (cleartext[i+1]);
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
	
	private int[] findLargestFreqs(double[] nGramCounts, int howMany){
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
	
	private void maybePrint(char[] text, double thisFit, double bestFit){
		if (thisFit<bestFit){
			for (int i=0; i<text.length; i++){
				System.out.print(text[i]);
			}
			System.out.println();
		}
		else{
			System.out.print('.');
		}
	}

	public char[] getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(char[] ciphertext) {
		this.ciphertext = ciphertext;
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
}
