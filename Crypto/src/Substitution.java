import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Substitution {
	private LinkedHashMap<Integer,Integer> cipherMono;
	private LinkedHashMap<Integer,Integer> cipherQuad;
	private LinkedHashMap<Integer,Integer> englishMono;
	private LinkedHashMap<Integer,Integer> englishQuad;
	private ArrayList<Byte> cipherText; 
	private ArrayList<Byte> plainText;
	private HashMap<Integer,Integer> key;
	private BookAnalyzer boa;
	private ScoreText st;

	public Substitution(File book, File message) throws IOException{
		BookAnalyzer ba = new BookAnalyzer();
		this.setBoa(ba);
		this.setCipherMono(sortHashMapByValuesD(ba.analyze(message,1)));
		this.setCipherQuad(sortHashMapByValuesD(ba.analyze(message,4)));

		this.setEnglishMono(sortHashMapByValuesD(ba.analyze(book,1)));
		this.setEnglishQuad(sortHashMapByValuesD(ba.analyze(book,4)));

		LoadCipher lt = new LoadCipher();		
		this.setCipherText(lt.load(message));

		this.setST(new ScoreText());
	}

	public void crack() throws IOException{
		HashMap<Integer, Integer> key = mergeHashMapsIntoKey(this.getCipherMono(), this.getEnglishMono());
		//		HashMap<Integer, Integer> quadKey = mergeHashMapsIntoKey(this.getCipherQuad(), this.getEnglishQuad());
		System.out.println(key.toString());
		ArrayList<Byte> decryptedText = decrypt(key);
		//		HashMap<Integer,Integer> decryptedHash = this.getBoa().analyze(this.getCipherText(), 4);
		HashMap<Integer,Integer> decryptedHash = this.getBoa().analyze(decryptedText, 4);
		double score = this.getST().scoreSub(decryptedHash, this.getEnglishQuad());

		int counter=0;
		while (true){
			HashMap<Integer, Integer> newKey = shuffleMap(key);
			//			System.out.println("c:"+newKey.toString());
			//			System.out.println("=========================");
			ArrayList<Byte> newDecryptedText = decrypt(newKey);
			ArrayList<Byte> backup = new ArrayList<Byte>(0);
			for (int i=0; i< newDecryptedText.size(); i++){
				backup.add(i, newDecryptedText.get(i));
			}
			//			System.out.println();

			HashMap<Integer,Integer> newDecryptedHash = this.getBoa().analyze(newDecryptedText, 4);
			double newScore = this.getST().scoreSub(newDecryptedHash, this.getEnglishQuad());
			if(newScore>score){	
				//				for (int i=0; i< backup.size(); i++){
				//					System.out.print((char)backup.get(i).intValue());
				//				}
				//				System.out.println("\nScore:" + score);
				//				System.out.println("Improve?");
				//				System.in.read();

				key=newKey;
				score=newScore;
			}
			if (counter>100){
				//				System.out.println("1000 mark.");
				for (int i=0; i< backup.size(); i++){
					System.out.print((char)backup.get(i).intValue());
				}
				System.out.println("\nScore:" + score);
				//				System.out.println("Improve?");
				//				System.in.read();
				counter=0;
				System.out.println("==================================================");
			}
			counter++;
			//			System.out.println("==================================================");
		}



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
		//		System.out.println(mapKeys.toString());
		//		System.out.println(mapValues.toString());
		Iterator<Integer> keyIt = mapKeys.iterator();
		Iterator<Integer> valueIt = mapValues.iterator();
		while (keyIt.hasNext()) {
			//			System.out.print(keyIt.hasNext()+",");
			//			System.out.println(valueIt.hasNext());
			Integer key = keyIt.next();
			//			System.out.print(key+",");

			Integer val = valueIt.next();
			//			System.out.println(val);

			keyHash.put((Integer)key, (Integer)val);
			//			System.out.println(key+","+val);

		}
		this.setKey(keyHash);
		return keyHash;
	}

	public HashMap<Integer, Integer> shuffleMap(HashMap<Integer,Integer> map) {
		HashMap<Integer,Integer> newMap= new HashMap<Integer, Integer>(map);

		List<Integer> mapKeys = new ArrayList<Integer>(map.keySet());
		int a = (int)(Math.random()*mapKeys.size());
		int b = (int)(Math.random()*mapKeys.size());

		swap(new Integer(mapKeys.get(a)),new Integer(mapKeys.get(b)), newMap);

		return newMap; 
	}

	private HashMap<Integer,Integer> swap(Integer a, Integer b, HashMap<Integer,Integer> map){
		Integer temp = map.get(a);
		//		System.out.println("values:"+a+","+b);
		//		System.out.println("0:"+map.toString());
		map.put(a, map.get(b));
		//		System.out.println("a:"+map.toString());
		map.put(b,temp);
		//		System.out.println("b:"+map.toString());
		return map;
	}

	private ArrayList<Byte> decrypt(HashMap<Integer,Integer> decryptionKey){
		ArrayList<Byte> clearText = new ArrayList<Byte>(this.getCipherText().size());
		for(int i=0; i<this.getCipherText().size(); i++){
			//Get the next element in the cipher text
			Byte current = this.getCipherText().get(i);
			//convert this Byte element into its Integer value: <0,0,0,current>
			Integer decryptKey = ((0xFF & new Byte((byte)0)) << 24) | ((0xFF & new Byte((byte)0)) << 16) | ((0xFF & new Byte((byte)0)) << 8) | (0xFF & current);
			//get the new value
			Integer decryptValue = decryptionKey.get(decryptKey);
			Byte guess;
			if(decryptValue.equals(null)){
				guess=(byte)-5;
			} else{
				//convert this Integer back into a Byte
				guess = ByteBuffer.allocate(4).putInt(decryptValue).array()[3];
			}
			//add this Byte into the clear text
			clearText.add(i, guess);
			//			System.out.print((char)(int)clearText.get(i));
		}

		return clearText;
	}

	public ArrayList<Byte> getCipherText() {
		return cipherText;
	}

	public void setCipherText(ArrayList<Byte> cipherText) {
		this.cipherText = cipherText;
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

	public LinkedHashMap<Integer, Integer> getCipherMono() {
		return cipherMono;
	}

	public void setCipherMono(LinkedHashMap<Integer, Integer> cipherMono) {
		this.cipherMono = cipherMono;
	}

	public LinkedHashMap<Integer, Integer> getCipherQuad() {
		return cipherQuad;
	}

	public void setCipherQuad(LinkedHashMap<Integer, Integer> cipherQuad) {
		this.cipherQuad = cipherQuad;
	}

	public LinkedHashMap<Integer, Integer> getEnglishMono() {
		return englishMono;
	}

	public void setEnglishMono(LinkedHashMap<Integer, Integer> englishMono) {
		this.englishMono = englishMono;
	}

	public LinkedHashMap<Integer, Integer> getEnglishQuad() {
		return englishQuad;
	}

	public void setEnglishQuad(LinkedHashMap<Integer, Integer> englishQuad) {
		this.englishQuad = englishQuad;
	}
}
