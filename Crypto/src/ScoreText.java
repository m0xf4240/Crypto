//import java.io.*; 
import java.util.*;

public final class ScoreText {
	double sum;
	public ScoreText(){

	}

	public double scoreSub(HashMap<Integer, Integer> passedCipher, HashMap<Integer, Integer> passedEnglish){
		double score=0;

		HashMap<Integer, Double> cipherFrequency = createFrequencyHash(passedCipher);
		HashMap<Integer, Double> englishFrequency = createFrequencyHash(passedEnglish);

		List<Integer> cipherValues = new ArrayList<Integer>(cipherFrequency.keySet());
		List<Integer> englishValues = new ArrayList<Integer>(englishFrequency.keySet());

		Iterator<Integer> cipherIt = cipherValues.iterator();

		while(cipherIt.hasNext()){
			Integer cipherKey = cipherIt.next();
			//			System.out.println("cipher:"+cipherKey);
			Iterator<Integer> englishIt = englishValues.iterator();
			//			System.out.println("TEST OUTER");
			while(englishIt.hasNext()){
				//				System.out.println("TEST INNER");
				Integer englishKey = englishIt.next();
				//				System.out.println("english:"+englishKey);
				if (cipherKey.equals(englishKey)){
					score += Math.pow(cipherFrequency.get(cipherKey)-englishFrequency.get(englishKey),2);
					//					System.out.print(score+":");
					break;
				}
			}
		}
		//		System.out.println();
		return Math.log10(score);
	}

	private HashMap<Integer, Double> createFrequencyHash (HashMap<Integer, Integer> passedMap){
		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
		Iterator<Integer> valueIt = mapValues.iterator();
		double sum=0;
		while (valueIt.hasNext()) {
			sum += valueIt.next();
		}
		HashMap <Integer,Double> quadFrequencies = new HashMap<Integer, Double>();
		for (Integer k : passedMap.keySet()){
			quadFrequencies.put(k,new Double(passedMap.get(k) / sum));
		}
		return quadFrequencies;
	}

	public Double createIndexofC (HashMap<Byte, Integer> passedMap){
		//		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
		//		Iterator<Integer> valueIt = mapValues.iterator();
		//		this.sum=0;
		//		while (valueIt.hasNext()) {
		//			this.sum += valueIt.next();
		//		}
		//this.sum=passedMap.size();

		for (Integer value : passedMap.values()) {
			this.sum= this.sum + value; // Can also be done by total += value;
		}
		System.out.println("Total size of text is k="+this.sum);
		Double index=0.0;
		double sum = 0;
		for (Byte mlKey : passedMap.keySet()){
			Double ml = new Double(passedMap.get(mlKey)); // / this.sum);
			sum+=ml;
			System.out.print(","+ml);
			index += (ml*(ml-1))/(this.sum*(this.sum - 1));
		}
		//index /= (this.sum*(this.sum - 1));
		return index;
	}
	
	/**
	 * @param cipherFraction is a Byte[] of counts for every kth element of the ciphertext
	 */
	public double createIndexofC (int[] cipherFraction){
		double index=0.0;
		double sum = 0;
		for (int i=0; i<cipherFraction.length; i++) {
			this.sum= this.sum + cipherFraction[i]; // Can also be done by total += value;
		}
		
		for (int i=0; i<cipherFraction.length; i++){
			Double ml = new Double(cipherFraction[i]); // / this.sum);
			sum+=ml;
			System.out.print(","+ml);
			index += (ml*(ml-1))/(this.sum*(this.sum - 1));
		}
		return index;
	}

}
