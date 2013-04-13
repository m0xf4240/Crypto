
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
		 
		
		 
//		 int debugLength = 50;
//		 Byte[]cb = new Byte[cbytes.length];


//		 for(int i=0 ; i<cbytes.length; i++){
//			 System.out.println(cbytes[i].intValue());
//			 System.out.println((char)cbytes[i].intValue());
//			 cb[i] = cbytes[i];
//		 }
		 
//		 cbytes = new Byte[cb.length];
//		 cbytes = cb;

		 //		 for (int i=0; i<6; i++) {
		 //			 System.out.println(cbytes[i] + ".");
		 //		 }
		 
		 
		
		 ScoreText st=new ScoreText();		 
		 for (int i=1; i<=5; i++){
			 double [] cioc=new double[i]; 
			 
			 for (int k=0; k<i; k++){
				 int[] counts = new int[256]; 
				 for (int j=k; j<cbytes.length; j+=i){
					 counts[(int)cbytes[j]&0xff]++;
				 }
				 cioc[k]=st.createIndexofC(counts);
			 }
			 double sum =0;
			 for (int l=0; l<cioc.length; l++) {
				 sum += cioc[l];
			 }
			 sum /= cioc.length;
			 //cioc=st.createIndexofC(counts);
			 System.out.println("Index of Coincidence is "+sum+ " and key is "+ i);
		 }
		 
		
		 byte[] keysize = new byte[1];
		 System.out.println("Hit Enter to Continue.");
		// System.out.println("Enter Keysize to continue");
		 System.in.read();
		 
		 this.sum=st.sum;
		// int keylength=this.keywordLength(eioc, cioc);
		 System.out.println("keylength is "+(int)keysize[0]);
		 LinkedList<Byte[]> columns=this.makeCol(cbytes,(int) keysize[0]);
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
		 for(int j=0;j<leng;j++){
			 for (int i=0; i<boom.size(); i++){

				 plaintext[(j*2)+i]=boom.get(i)[j].byteValue();
				
			 }


		 }
		 System.out.println("Leaving flip");
		 return plaintext;

	 }
}

