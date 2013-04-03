import java.io.*;
import java.util.*;

public final class ScoreText {
	
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

}
