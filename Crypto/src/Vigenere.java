
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
		 byte[]cbytes=ba.analyzeToBytes(cipher);
		 
		 for (int i=0; i<6; i++) {
			 System.out.println(cbytes[i] + ".");
		 }
		 
		 ScoreText st=new ScoreText();		 
		 double cioc=st.createIndexofC(ba.analyzeByte(cipher));
		 this.sum=st.sum;
		 int keylength=this.keywordLength(eioc, cioc);
		 System.out.println("keylength is "+keylength);
		 LinkedList<Byte[]> columns=this.makeCol(cbytes, keylength);
		 this.results=columns;
	 }


	 public static void main(String[] args) {
		 // TODO Auto-generated method stub

	 }
	 public int keywordLength(double e, double c){
		 System.out.println("finding Keylength");
		 System.out.println("sum is "+this.sum+" and e is "+e +" and c is "+c);
		 double thing= ((c-(1/255))*this.sum)/(((this.sum-1)*c)-((this.sum*(1/255))+e));
		 int kl=(int) Math.ceil(thing);
		 return kl;
	 }
	 public LinkedList<Byte[]> makeCol(byte[] cb, int k){
		System.out.println("In makecol");
		 //takes in the an array of bytes from the ciphertext and the keylength
		 //returns an Linkedlist of Byte[] of each column where 
		 //the index relating to the Byte[] is the col index in a full matrix of byte values
		 //so LinkedList.remove(0)[0] would be element [0][0] in the original ciphertext matrix
		 //returns linked list indexed by column number
		 byte[][]matrix=new byte[k][cb.length/k];
		 for(int i=0;i<(cb.length/k);i++){
			 for(int j=0;j<k;j++){ // /k
				 matrix[j][i]=cb[j+(i*2)];
			 }
		 }

		 LinkedList<Byte[]> columns=new LinkedList<Byte[]>();
		 for(int i=0;i<cb.length/k;i++){
			 Byte[] cols=new Byte[k];
			 for(int row = 0; row < k; row++)
			 {
				 cols[row] = matrix[row][i];
			 }
			 columns.add(i, cols);
		 }
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
		 
		 return plaintext;
	 }

}
