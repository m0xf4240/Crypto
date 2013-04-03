import java.util.ArrayList;
import java.util.List;


public class Comments {

	public void decrypt(){
		double[] nGramFreq = findNGramsFreq(1, getCiphertext());
		char[] guess = randomGuess(getSpaceSize());
		//guess = smartGuess(guess, nGramFreq, englishMonos);
		char[] clearText = decrypt(getCiphertext(), guess);
		nGramFreq = findNGramsFreq(nGramSize, clearText);
		double fit = score(nGramFreq);
		System.out.println("Fit:"+fit);
		System.in.read();
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

			
			
			private char[] changeGuess(char[] guess, double fit) {
				for (int x=0; x<250; x++){
					if (fit<x){
						break;
					}
					else{
						swap((int)(Math.random()*255), (int)(Math.random()*255), guess);	
					}
				}
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
					if (nGramFreq[i]>0 && this.getStandardText()[i]==0){
						sqSum += 100;
					}
					sqSum += Math.pow(nGramFreq[i]-this.getStandardText()[i], 4);
				}
				return Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(sqSum))));
			}
		
			private char[] randomGuess(int size) {
				char[] guess = new char[size];
				for (int i=0; i<size; i++){		// works
					guess[i] = (char)((i)%size);
				}
				List<Character> g = new ArrayList<Character>();
				for(char ch : guess){
					g.add(ch);
				}
				//Collections.shuffle(g);
				int i=0;
				for(Character ch : g) {			// appears to work
					guess[i++] = ch;
				}
				return guess;
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
	}
