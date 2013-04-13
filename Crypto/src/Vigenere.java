
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

	 public Vigenere(File cipher, File english) throws IOException{
		 BookAnalyzer ba = new BookAnalyzer();



		 Byte[]cbytes=ba.analyzeToBytes(cipher);
		 for(int i=0;i<30;i++){
			 System.out.println(cbytes[i]&0xff);
		 }
		 System.in.read();



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
		 for (int i=1; i<=30; i++){
			 double [] cioc=new double[i]; 

			 for (int k=0; k<i; k++){
				 int[] counts = new int[256]; 
				 for (int j=k; j<cbytes.length; j+=i){
					 //					 System.out.print(","+((int)cbytes[j]&0xff));
					 //					 System.out.print(","+(char)((int)cbytes[j]&0xff));
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


		 //byte[] keysize = new byte[1];
		 //System.out.println("Hit Enter to Continue.");
		 // System.out.println("Enter Keysize to continue");
		// System.in.read();

		 this.sum=st.sum;
		 // int keylength=this.keywordLength(eioc, cioc);
		 //System.out.println("keylength is "+(int)keysize[0]);

		 int i=6;
		 Byte [] col=new Byte[(cbytes.length/6)]; 
		 for (int k=0; k<i; k++){
			 int index = 0;
			 for (int j=k; j<cbytes.length; j+=i){
				 //					 System.out.print(","+((int)cbytes[j]&0xff));
				 //					 System.out.print(","+(char)((int)cbytes[j]&0xff));
				 //System.out.println(index + " " + col.length + " " + j);
				 if (index>=col.length){														//we are cutting off the last few bytes that don't %6 in the cipher
					 break;
				 }
				 col[index++]= cbytes[j];
			 }
			 Caesar caesar = new Caesar(col, english);
			 Byte[] shifted = caesar.getVig();
			 index=0;
			 for (int j=k; j<cbytes.length; j+=i){
				 //					 System.out.print(","+((int)cbytes[j]&0xff));
				 //					 System.out.print(","+(char)((int)cbytes[j]&0xff));
				 if (index>=shifted.length){													//we are cutting off the last few bytes that don't %6 in the cipher
					 break;
				 }
				 cbytes[j]= shifted[index++];
			 }
		 }
		 
		 for (int l=0; l<cbytes.length; l++) {
			 System.out.print((char)cbytes[l].intValue());
		 }


		 //		 LinkedList<Byte[]> columns=this.makeCol(cbytes,(int) keysize[0]);
		 //		 this.results=columns;
	 }



	 public int keywordLength(double e, double c){
		 //System.out.println("finding Keylength");
		 //System.out.println("sum is "+this.sum+" and e is "+e +" and c is "+c);
		 double thing= ((c-(1/255))*this.sum)/(((this.sum-1)*c)-((this.sum*(1/255))+e));
		 int kl=(int) Math.ceil(thing);
		 //return kl;
		 //TODO:Temporary debugging fix
		 return 6;
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
		 Byte[] first=new Byte[cb.length/6];
		 Byte[] sec=new Byte[cb.length/6];
		 Byte[] thr=new Byte[cb.length/6];
		 Byte[] fr=new Byte[cb.length/6];
		 Byte[] fif=new Byte[cb.length/6];
		 Byte[] six=new Byte[cb.length/6];
		 int index=0;
		 for(int i=0;i<(cb.length-1);i+=6){
			 first[index]=cb[i];
			 sec[index]=cb[i+1];
			 thr[index]=cb[i+2];
			 fr[index]=cb[i+3];
			 fif[index]=cb[i+4];
			 six[index]=cb[i+5];
			 index++;



		 }

		 LinkedList<Byte[]> columns=new LinkedList<Byte[]>();
		 columns.add(first);
		 columns.add(sec);
		 columns.add(thr);
		 columns.add(fr);
		 columns.add(fif);
		 columns.add(six);


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

