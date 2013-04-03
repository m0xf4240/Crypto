import java.io.*;
import java.util.*;

public final class ScoreText {
	
	public ScoreText(){
		
	}
	
	public static double score(HashMap<Integer, Integer> passedCipher, HashMap<Integer, Integer> passedEnglish){
		double score=0;
		
		HashMap<Integer, Double> cipherFrequency = createFrequencyHash(passedCipher);
		HashMap<Integer, Double> englishFrequency = createFrequencyHash(passedEnglish);
		
		//Insert code
		if (key1 == key2){
		//Insert code	
		}
		//Insert Code
		return score;
	}
	
	private static HashMap<Integer, Double> createFrequencyHash (HashMap<Integer, Integer> passedMap){
		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
		Iterator<Integer> valueIt = mapValues.iterator();
		double sum = 0;
		while (valueIt.hasNext()) {
			sum += valueIt.next();
		}
		BookAnalyzer boa = new BookAnalyzer();
		
		HashMap <Integer, Integer> quad = boa.analyze(passedMap); //add a way to do this
		HashMap <Integer, Double> quadFrequencies = new HashMap<Integer, Double>();
		for (Integer k : quad.values()){
			quadFrequencies.add(k,new Double(quad.get(k) / sum));
		}
		return quadFrequencies;
	}

}
