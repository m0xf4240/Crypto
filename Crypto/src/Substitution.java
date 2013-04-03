import java.io.*;
import java.util.*;

public class Substitution {
	private LinkedHashMap<Byte,Integer> cipherHash;
	private ArrayList<Byte> cipherText; 
	private LinkedHashMap<Byte,Integer> englishHash;
	private HashMap<Byte,Byte> key;
	private ArrayList<Byte> plainText;


	public Substitution(ArrayList<Byte>cipher, HashMap<Byte,Integer> cipherHash, HashMap<Byte,Integer> englishHash){
		this.setCipherText(cipher);		
		this.setCipherHash(sortHashMapByValuesD(cipherHash));
		this.setEnglishHash(sortHashMapByValuesD(englishHash));
	}

	public void crack() throws IOException{
		HashMap<Byte, Byte> key = mergeHashMapsIntoKey(this.getCipherHash(), this.getEnglishHash());
		decrypt(key);
		//TODO: keep guessing and modifying the key
	}

	public LinkedHashMap<Byte,Integer> sortHashMapByValuesD(HashMap<Byte,Integer> passedMap) {
		List<Byte> mapKeys = new ArrayList<Byte>(passedMap.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
		Collections.sort(mapValues, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});

		LinkedHashMap<Byte,Integer> sortedMap = new LinkedHashMap<Byte,Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Integer val = valueIt.next();
			Iterator<Byte> keyIt = mapKeys.iterator();
			while (keyIt.hasNext()) {
				Byte key = keyIt.next();
				Integer comp = passedMap.get(key);

				if (val.equals(comp)){
					passedMap.remove(key);
					mapKeys.remove(key);
					//					System.out.println(key+","+val);
					sortedMap.put((Byte)key, (Integer)val);
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
	public HashMap<Byte,Byte> mergeHashMapsIntoKey(HashMap<Byte,Integer> passedMapA, HashMap<Byte,Integer> passedMapB) {
		HashMap<Byte,Byte> keyHash = new HashMap<Byte, Byte>();
		List<Byte> mapKeys = new ArrayList<Byte>(passedMapA.keySet());
		List<Byte> mapValues = new ArrayList<Byte>(passedMapB.keySet());

		Iterator<Byte> keyIt = mapKeys.iterator();
		Iterator<Byte> valueIt = mapValues.iterator();
		while (keyIt.hasNext()) {
			Byte val = valueIt.next();
			Byte key = keyIt.next();
			keyHash.put((Byte)key, (Byte)val);
			//			System.out.println(key+","+val);

		}
		this.setKey(keyHash);
		return keyHash;
	}

	public static void shuffleMap(Map<Byte,Integer> map) {
		List<Integer> valueList = new ArrayList<Integer>(map.values());
		Collections.shuffle(valueList);
		Iterator<Integer> valueIt = valueList.iterator();
		for(Map.Entry<Byte,Integer> e : map.entrySet()) {
			e.setValue(valueIt.next());
		}
	}

	private ArrayList<Byte> decrypt(HashMap<Byte,Byte> decryptionKey){
		for(int i=0; i<this.getCipherText().size(); i++){
			this.getCipherText().set(i, decryptionKey.get(this.getCipherText().get(i)));
			System.out.print((char)(int)this.getCipherText().get(i));
		}
		//This doesn't really have to return
		return this.getCipherText();
	}

	public LinkedHashMap<Byte, Integer> getCipherHash() {
		return cipherHash;
	}

	public void setCipherHash(LinkedHashMap<Byte, Integer> cipherHash) {
		this.cipherHash = cipherHash;
	}

	public ArrayList<Byte> getCipherText() {
		return cipherText;
	}

	public void setCipherText(ArrayList<Byte> cipherText) {
		this.cipherText = cipherText;
	}

	public LinkedHashMap<Byte, Integer> getEnglishHash() {
		return englishHash;
	}

	public void setEnglishHash(LinkedHashMap<Byte, Integer> englishHash) {
		this.englishHash = englishHash;
	}

	public HashMap<Byte, Byte> getKey() {
		return key;
	}

	public void setKey(HashMap<Byte, Byte> key) {
		this.key = key;
	}

	public ArrayList<Byte> getPlainText() {
		return plainText;
	}

	public void setPlainText(ArrayList<Byte> plainText) {
		this.plainText = plainText;
	}
}
