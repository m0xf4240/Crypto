import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;


public class Caesar {

	private char[] ciphertext;
	private int spaceSize;
	private LinkedList<Byte[]> vig;
	private HashMap<Byte,Double> english;

	public Caesar(LinkedList<Byte[]> vigenered, File gb) throws IOException{
		this.setSpaceSize(vigenered.get(0).length);
		this.setVig(vigenered);

		this.setEnglish(makeEnglish(gb));
		decrypt();
		//at 1:24AM sum is 212677.0 and e is 0.066 and c is -2.1828126877357703E-11
		//so the keylength ends up being thought to be 1....not sure if problem or silly thing too tired
		//tired lexie code looks like ridiculousness so I will no to sleew
		//do we want to be analyzing this in bytes or chars?
		//ArrayList<CharBuffer> ciphercol=this.toChars();


	}

	//	public void bruteForce() throws IOException{
	//		for (int key=0; key<this.getSpaceSize(); key++){
	//			char[] cleartext = this.decrypt(key);
	////			for (int i=0; i<cleartext.length; i++){
	////				System.out.print(cleartext[i]);
	//////			}
	////			System.out.println("\n");
	////
	////			//Press enter to continue
	////			System.in.read();
	//		}
	//	}

	public void decrypt() throws IOException{
		System.out.println("In decrypt");
		LinkedList<Byte[]> v=this.getVig();
		System.out.println("vig:");
		for(int i=0;i<this.getVig().get(1).length;i++){
			//System.out.println(this.getVig().getFirst()[i]);
			System.out.println(this.getVig().get(1)[i]);
		}
		//double[] shiftChis= new double[v.size()];
		//ListIterator<Byte[]> ls=v.listIterator();
		//for(Byte[] b:v){	
		Byte[]b=new Byte[v.getFirst().length];
		System.out.println("v.size: " + v.size());

		for(int k=0;k<v.size();k++){
			b=v.get(k);
			System.out.println(v.get(1)[1]);
			System.out.println("v.getk: " +v.get(k)[1] + " " + k);
			System.out.println("b:");
			for(int i=0;i<b.length;i++){
				System.out.println(b[i]);
			}
			double o=calcChi(b);
			double [] sure=new double[b.length];
			sure[0]=o;
			System.out.println("Sure.length is: "+ sure.length);
			Byte[] thisone=new Byte[b.length];

			for(int i=0;i<b.length;i++){
				thisone[i]=b[i];
			}
			for(int i=1;i<b.length;i++){
				thisone=shiftBytes(thisone);
				sure[i]=calcChi(thisone);
			}
			int bestIndex=0;
			System.out.println("Comparing Chis in Decrypt");
			for(int i=1;i<sure.length;i++ ){
				if(!compareChis(sure[bestIndex],sure[i])){
					bestIndex=i;
				}
			}
			
			Byte[] check = shiftBy(b,bestIndex);
			v.remove(k);
			v.add(k,check);
			System.in.read();

		}
		System.out.println("Setting vig");
		this.setVig(v);

	}
	public boolean compareChis(double o, double s){
		System.out.print("*");
		if(o<s){
			return true;
			//when o is better
		}
		else
			//when s is better
			return false;

	}
	public Byte[] shiftBytes(Byte []b){
		System.out.println("Shifting Bytes by one");
		Byte[] n=new Byte[b.length];
		for(int i=1;i<n.length-1;i++){
			n[i-1]=b[i];
		}
		n[n.length-1]=b[0];
		return n;
	}
	public Byte[] shiftBy(Byte []b, int shift){
		System.out.println("Shifting bytes by integer");
		Byte[] n=new Byte[b.length];
		for(int i=shift;i<n.length;i++){
			n[i-shift]=b[i];
		}
		for(int i=0;i<shift;i++){
			n[n.length-shift+i]=b[i];
		}
		System.out.println("Returning Shifted Bytes");
		return n;
	}

	public double calcChi(Byte[] b){
		HashMap<Byte, Double> cFreq=new HashMap<Byte, Double>();

		for(Byte c:b){
			if(!cFreq.containsKey(c)){
				cFreq.put(c,1.0);
			}
			else{
				cFreq.put(c, cFreq.get(c)+1);
			}
		}
		for (Map.Entry<Byte,Double> entry : cFreq.entrySet()) {
			cFreq.put(entry.getKey(), entry.setValue(entry.getValue()/cFreq.size()));		 
		}
		System.out.println("Done Calculatuing frequncies in calcChi");
		double temp=0.0;
		for (Map.Entry<Byte,Double> entry : cFreq.entrySet()) {
			if(this.english.get(entry.getKey())!=null){
				double t1=entry.getValue();
				//System.out.println("entry value: "+ entry.);
				double t2=this.english.get(entry.getKey());
				//temp=temp+((Math.pow(entry.getValue()-this.english.get(entry),2))/this.english.get(entry));
				temp=temp+((Math.pow((t1-t2), 2))/t2);
			}
			else
				temp=temp+1005.99;
		}
		System.out.println("returnign calcChi");
		return temp;
	}
	//	private char[] decrypt(int key){
	//		char [] cleartext = new char[this.getCiphertext().length];
	//		for (int i = 0; i < cleartext.length; i = (i + 1)) {
	//			cleartext[i] = (char)((this.getSpaceSize() + (this.getCiphertext()[i] - key)) % this.getSpaceSize());
	//		}
	//		return cleartext;
	//	}
	public ArrayList<CharBuffer> toChars(){
		//making byte columns into character columns
		byte[] temp=new byte[this.spaceSize];
		ArrayList<CharBuffer> columns= new ArrayList<CharBuffer>();

		for(int j=0;j<this.getVig().size();j++){
			for(int i=0;i<this.spaceSize;i++){
				temp[i]=this.getVig().get(j)[i].byteValue();
			}
			CharBuffer cBuffer = ByteBuffer.wrap(temp).asCharBuffer();
			columns.add(cBuffer);
		}
		//now every column of bytes from the cipher text is a CharBuffer in columns
		return columns;	
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
	public LinkedList<Byte[]> getVig() {
		return vig;
	}
	public void setVig(LinkedList<Byte[]> vig) {
		this.vig = vig;
	}

	public HashMap<Byte, Double> makeEnglish(File h) throws IOException{
		System.out.println("Making english");
		RandomAccessFile b=new RandomAccessFile(h,"r");
		byte[]rabbit=new byte[(int)b.length()];
		b.read(rabbit);
		b.close();
		Map<Byte, Double> mFreq=new HashMap<Byte,Double>(255);
		Byte[] btoB=new Byte[rabbit.length];
		for (int i=0;i<rabbit.length;i++){
			btoB[i]=Byte.valueOf(rabbit[i]);
		}
		for(Byte y:btoB){
			if(!mFreq.containsKey(y)){
				mFreq.put(y, 1.0);
			}
			else{
				mFreq.put(y, (mFreq.get(y)+1));
			}
		}
		for (Map.Entry<Byte,Double> entry : mFreq.entrySet()) {
			mFreq.put(entry.getKey(), entry.setValue(entry.getValue()/mFreq.size()));		 
		}
		//this.setEnglish((HashMap<Byte, Double>)mFreq);
		return (HashMap<Byte, Double>)mFreq;

	}
	public HashMap<Byte,Double> getEnglish() {
		return english;
	}
	public void setEnglish(HashMap<Byte,Double> english) {
		this.english = english;
	}
}
