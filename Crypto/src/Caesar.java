import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Caesar {

	private char[] ciphertext;
	private int spaceSize;
	private Byte[] vig;
	private double[] english;

	public Caesar(Byte[] vigenered, File gb) throws IOException{
		this.setSpaceSize(vigenered.length);
		this.setVig(vigenered);
		System.out.println("Vig size passed:"+ this.getVig().length);
		this.setEnglish(makeEnglish(gb));
		decrypt();

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
		Byte[] v=this.getVig();
		//		Byte[]b=new Byte[v.length];
		//System.out.println("v.size: " + v.size());

		//		for(int k=0;k<v.length;k++){
		//			System.out.println(v.get(1)[1]);
		//			System.out.println("v.getk: " +v.get(k)[1] + " " + k);
		//			System.out.println("b:");
		//			for(int i=0;i<v.length;i++){
		//				System.out.println(b[i]);
		//			}
		double o=calcChi(v);

		double [] sure=new double[255];
		sure[0]=o;
		//System.out.println("Sure.length is: "+ sure.length);

		Byte[] thisone=new Byte[v.length];
		for(int i=0;i<v.length;i++){
			thisone[i]=v[i];
		}


		for(int i=1;i<sure.length;i++){
			thisone=shiftBytes(thisone);
			sure[i]=calcChi(thisone);
			//				if (i%100==0){
			//					System.out.println(i);
			//				}
			//			}
			System.out.println("Out");
			//System.in.read();
			//System.out.println("Comparing Chis in Decrypt");
		}

		int bestIndex=0;
		for(int j=1;j<sure.length;j++ ){
			if(!compareChis(sure[bestIndex],sure[j])){
				bestIndex=j;
			}
		}

		Byte[] best = shiftBy(v,bestIndex+1); //+1
		//		v.remove(k);
		//		v.add(k,check);
		//System.in.read();

		System.out.println("Setting vig");
		this.setVig(best);
	}

	public boolean compareChis(double o, double s){
		//System.out.print("*");
		if(o<s){
			return true;
			//when o is better
		}
		else
			//when s is better
			return false;

	}
	public Byte[] shiftBytes(Byte []b){
		//System.out.println("Incrementing Bytes by one");
		//System.out.println("Before: " + b[0] + b[1]);
		for(int i=0;i<b.length;i++){
			//			String th=String.valueOf(b[i]);
			//			Integer thi=Integer.getInteger(th);
			//			thi=thi+1;

			//byte temp =thi.byteValue(); //i].byteValue();
			//System.out.println("temp is: "+ temp);
			byte temp=b[i].byteValue();
			int t=(int)temp;
			t=t+1;
			temp=(byte)t;
			b[i] = Byte.valueOf(temp);
		}
		//System.out.println("After: " + b[0] + b[1]);

		//		Byte[] n=new Byte[b.length];
		//		for(int i=1;i<n.length-1;i++){
		//			n[i-1]=b[i];
		//		}
		//		n[n.length-1]=b[0];
		return b;
	}
	public Byte[] shiftBy(Byte []b, int shift){
		//		System.out.println("Incrementing Bytes by integer");
		//		System.out.println("Beforek: " + b[0] + b[1]);
		System.out.println(b.length+": shift"+shift);
		for(int i=0;i<b.length;i++){
			for (int j=0; j<shift; j++) {
				System.out.println(b[i].byteValue());
				byte temp = b[i].byteValue();
				temp++;
				b[i] = Byte.valueOf(temp);
			}
		}
		//	System.out.println("Afterk: " + b[0] + b[1]);

		//		Byte[] n=new Byte[b.length];
		//		for(int i=shift;i<n.length;i++){
		//			n[i-shift]=b[i];
		//		}
		//		for(int i=0;i<shift;i++){
		//			n[n.length-shift+i]=b[i];
		//		}
		//System.out.println("Returning Incremented Bytes");
		return b;
	}

	public double calcChi(Byte[] b){
		//		HashMap<Byte, Double> cFreq=new HashMap<Byte, Double>();
		//
		//		for(Byte c:b){
		//			if(!cFreq.containsKey(c)){
		//				cFreq.put(c,1.0);
		//			}
		//			else{
		//				cFreq.put(c, cFreq.get(c)+1);
		//			}
		//		}
		//		for (Map.Entry<Byte,Double> entry : cFreq.entrySet()) {
		//			cFreq.put(entry.getKey(), entry.setValue(entry.getValue()/cFreq.size()));		 
		//		}
		//		//System.out.println("Done Calculatuing frequncies in calcChi");
		//		double temp=0.0;
		//		for (Map.Entry<Byte,Double> entry : cFreq.entrySet()) {
		//			if(this.english.get(entry.getKey())!=null){
		//				double t1=entry.getValue();
		//				//System.out.println("entry value: "+ entry.);
		//				double t2=this.english.get(entry.getKey());
		//				//temp=temp+((Math.pow(entry.getValue()-this.english.get(entry),2))/this.english.get(entry));
		//				temp=temp+((Math.pow((t1-t2), 2))/t2);
		//			}
		//			else
		//				temp=temp+1005.99;
		//		}
		//		//System.out.println("returnign calcChi");
		//		return temp;

		int[] counts = new int[255];
		for(int i=0; i<255; i++) {
			counts[i] = 0;
		}

		//make an expected count array from warandpeace by freq

		double sum1 =0;
		int size = this.getSpaceSize();
		for(int j=0; j<26; j++) {
			sum1 = sum1 + Math.pow((counts[j] - size*this.getEnglish()[j]),2)/(size*this.getEnglish()[j]);
		}

		return sum1;
	}
	//	private char[] decrypt(int key){
	//		char [] cleartext = new char[this.getCiphertext().length];
	//		for (int i = 0; i < cleartext.length; i = (i + 1)) {
	//			cleartext[i] = (char)((this.getSpaceSize() + (this.getCiphertext()[i] - key)) % this.getSpaceSize());
	//		}
	//		return cleartext;
	//	}

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
	public Byte[] getVig() {
		return vig;
	}
	public void setVig(Byte[] vig) {
		this.vig = vig;
	}

	public double[] makeEnglish(File h) throws IOException{
		System.out.println("Making english");
		RandomAccessFile b=new RandomAccessFile(h,"r");
		byte[]rabbit=new byte[(int)b.length()];
		b.read(rabbit);
		b.close();
		double[] mFreq=new double[255];
		for(int i=0;i<rabbit.length; i++){
			mFreq[rabbit[i]]+=1;
		}
		for(double d:mFreq){
			d/=(this.getSpaceSize()*6);
		}
		
		//this.setEnglish((HashMap<Byte, Double>)mFreq);
		return mFreq;

	}
	public double[] getEnglish() {
		return english;
	}
	public void setEnglish(double[] english) {
		this.english = english;
	}
}
