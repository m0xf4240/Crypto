
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;


public class Vigenere {

	/**
	 * @param args
	 */final double eioc=0.066;
	 LinkedList<Byte[]> results;
	 double sum;
	 public Vigenere(File cipher) throws IOException{
		 BookAnalyzer ba = new BookAnalyzer();
		 
		 Byte[]cbytes=ba.analyzeToBytes(cipher);
		 Byte[]cb = new Byte[3];
		 
		 
		 for(int i=0 ; i<cbytes.length; i++){
//			 System.out.println(cbytes[i].intValue());
//			 System.out.println((char)cbytes[i].intValue());
			 cb[i/4] = cbytes[i];
		 }
		 
		 cbytes = new Byte[3];
		 cbytes = cb;

//		 for (int i=0; i<6; i++) {
//			 System.out.println(cbytes[i] + ".");
//		 }

		 ScoreText st=new ScoreText();		 
		 double cioc=st.createIndexofC(ba.analyzeByte(cipher));
		 this.sum=st.sum;
		 int keylength=this.keywordLength(eioc, cioc);
		 System.out.println("keylength is "+keylength);
		 LinkedList<Byte[]> columns=this.makeCol(cbytes, keylength);
		 this.results=columns;
	 }



	 public int keywordLength(double e, double c){
		 //System.out.println("finding Keylength");
		 //System.out.println("sum is "+this.sum+" and e is "+e +" and c is "+c);
		 double thing= ((c-(1/255))*this.sum)/(((this.sum-1)*c)-((this.sum*(1/255))+e));
		 int kl=(int) Math.ceil(thing);
		 //return kl;
		 //TODO:Temporary debugging fix
		 return 2;
	 }
	 public LinkedList<Byte[]> makeCol(Byte[] cb, int k){
		 //System.out.println("In makecol");
		 //takes in the an array of bytes from the ciphertext and the keylength
		 //returns an Linkedlist of Byte[] of each column where 
		 //the index relating to the Byte[] is the col index in a full matrix of byte values
		 //so LinkedList.remove(0)[0] would be element [0][0] in the original ciphertext matrix
		 //returns linked list indexed by column number
		 //byte[][]matrix=new byte[k][cb.length/k];
		 //
		 Byte[] first=new Byte[cb.length/2];
		 Byte[] sec=new Byte[cb.length/2];
		 int index=0;
		 for(int i=0;i<(cb.length-1);i++){
			 first[index]=cb[i];
			 sec[index]=cb[i+1];
			 index++;
			 i++;

			 //			 for(int j=0;j<k;j++){ // /k
			 //				 matrix[j][i]=cb[j+(i*2)];
			 //			 }
		 }

		 LinkedList<Byte[]> columns=new LinkedList<Byte[]>();
		 columns.add(first);
		 columns.add(sec);

		 return columns;
	 }
	 public Byte[] flip(LinkedList<Byte[]> boom){		 
		 System.out.println("In flip");
		 int leng=boom.get(0).length;
		 Byte[] plaintext = new Byte[(leng*boom.size())];
		 for (int i=0; i<boom.size(); i++){
			 for(int j=1;j<=leng;j++){
				 plaintext[leng*j+i]=boom.get(i)[j-1].byteValue();
			 }
			 
			 
		 }
		 System.out.println("Leaving flip");
		 return plaintext;

	 }
}

