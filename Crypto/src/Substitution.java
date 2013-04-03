import java.io.*;
import java.util.*;

public class Substitution {
	private LinkedHashMap<Integer,Integer> cipherHash;
	private ArrayList<Byte> cipherText; 
	private LinkedHashMap<Integer,Integer> englishHash;
	private LinkedHashMap<Integer,Integer> englishQuadHash;
	private HashMap<Integer,Integer> key;
	private ArrayList<Byte> plainText;
	private BookAnalyzer boa;
	private ScoreText st;


	public Substitution(ArrayList<Byte>cipher, HashMap<Integer,Integer> cipherHash, HashMap<Integer,Integer> englishHash, HashMap<Integer,Integer> englishQuadHash){
		this.setCipherText(cipher);		
		this.setCipherHash(sortHashMapByValuesD(cipherHash));
		this.setEnglishHash(sortHashMapByValuesD(englishHash));
		this.setEnglishQuadHash(sortHashMapByValuesD(englishQuadHash));
		this.setBoa(new BookAnalyzer());
		this.setST(new ScoreText());
	}

	public void crack() throws IOException{
		HashMap<Integer, Integer> key = mergeHashMapsIntoKey(this.getCipherHash(), this.getEnglishHash());
		List<Integer> decryptedText = decrypt(key);
		HashMap<Integer,Integer> decryptedHash = this.getBoa().analyze(this.getCipherText(), 4);
//		HashMap<Integer,Integer> decryptedHash = this.getBoa().analyze(decryptedText, 4);
		double score = this.getST().scoreSub(decryptedHash, this.getEnglishQuadHash());
		System.out.println("Score:" + score);
		//TODO: re-count new cipher text
		//TODO: keep guessing and modifying the key
	}

	public LinkedHashMap<Integer,Integer> sortHashMapByValuesD(HashMap<Integer,Integer> passedMap) {
		List<Integer> mapKeys = new ArrayList<Integer>(passedMap.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
		Collections.sort(mapValues, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		Collections.sort(mapKeys);

		LinkedHashMap<Integer,Integer> sortedMap = new LinkedHashMap<Integer,Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Integer val = valueIt.next();
			Iterator<Integer> keyIt = mapKeys.iterator();
			while (keyIt.hasNext()) {
				Integer key = keyIt.next();
				Integer comp = passedMap.get(key);

				if (val.equals(comp)){
					passedMap.remove(key);
					mapKeys.remove(key);
//					System.out.println(key+","+val);
					sortedMap.put((Integer)key, (Integer)val);
					break;
				}
			}
		}
		return sortedMap;
	}

	/**
	 * @param passedMapA will have its key values used as the keys for the new HashMap
	 * @param passedMapB will have its key values used as the values for the new HashMap
	 * @return HashMap<Byte,Byte>
	 */
	public HashMap<Integer,Integer> mergeHashMapsIntoKey(HashMap<Integer,Integer> passedMapA, HashMap<Integer,Integer> passedMapB) {
		HashMap<Integer,Integer> keyHash = new HashMap<Integer, Integer>();
		List<Integer> mapKeys = new ArrayList<Integer>(passedMapA.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(passedMapB.keySet());

		Iterator<Integer> keyIt = mapKeys.iterator();
		Iterator<Integer> valueIt = mapValues.iterator();
		while (keyIt.hasNext()) {
			Integer val = valueIt.next();
			Integer key = keyIt.next();
			keyHash.put((Integer)key, (Integer)val);
			//			System.out.println(key+","+val);

		}
		this.setKey(keyHash);
		return keyHash;
	}

	public static void shuffleMap(Map<Integer,Integer> map) {
		List<Integer> valueList = new ArrayList<Integer>(map.values());
		Collections.shuffle(valueList);
		Iterator<Integer> valueIt = valueList.iterator();
		for(Map.Entry<Integer,Integer> e : map.entrySet()) {
			e.setValue(valueIt.next());
		}
	}

	private ArrayList<Integer> decrypt(HashMap<Integer,Integer> decryptionKey){
		ArrayList<Integer> clearText = new ArrayList<Integer>(this.getCipherText().size());
		for(int i=0; i<this.getCipherText().size(); i++){
			clearText.add(i, decryptionKey.get(this.getCipherText().get(i)));
//			System.out.print((char)(int)clearText.get(i));
		}
		return clearText;
	}

	public LinkedHashMap<Integer,Integer> getCipherHash() {
		return cipherHash;
	}

	public void setCipherHash(LinkedHashMap<Integer,Integer> cipherHash) {
		this.cipherHash = cipherHash;
	}

	public ArrayList<Byte> getCipherText() {
		return cipherText;
	}

	public void setCipherText(ArrayList<Byte> cipherText) {
		this.cipherText = cipherText;
	}

	public LinkedHashMap<Integer,Integer> getEnglishHash() {
		return englishHash;
	}

	public void setEnglishHash(LinkedHashMap<Integer,Integer> englishHash) {
		this.englishHash = englishHash;
	}

	public HashMap<Integer, Integer> getKey() {
		return key;
	}

	public void setKey(HashMap<Integer, Integer> key) {
		this.key = key;
	}

	public ArrayList<Byte> getPlainText() {
		return plainText;
	}

	public void setPlainText(ArrayList<Byte> plainText) {
		this.plainText = plainText;
	}

	public BookAnalyzer getBoa() {
		return boa;
	}

	public void setBoa(BookAnalyzer boa) {
		this.boa = boa;
	}

	public ScoreText getST() {
		return st;
	}

	public void setST(ScoreText st) {
		this.st = st;
	}

	public LinkedHashMap<Integer,Integer> getEnglishQuadHash() {
		return englishQuadHash;
	}

	public void setEnglishQuadHash(LinkedHashMap<Integer,Integer> englishQuadHash) {
		this.englishQuadHash = englishQuadHash;
	}
}
