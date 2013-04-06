
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;


public class Vigenere {

	/**
	 * @param args
	 */final double eioc=0.066;
	 double sum;
	 public Vigenere(File cipher) throws IOException{
		 BookAnalyzer ba = new BookAnalyzer();
		 byte[]cbytes=ba.analyzeToBytes(cipher);
		 ScoreText st=new ScoreText();
		 this.sum=st.sum;
		 double cioc=st.createIndexofC(ba.analyzeByte(cipher));
		 int keylength=this.keywordLength(eioc, cioc);
		 LinkedList<Byte[]> columns=this.makeCol(cbytes, keylength);

	 }


	 public static void main(String[] args) {
		 // TODO Auto-generated method stub

	 }
	 public int keywordLength(double e, double c){
		 double thing= ((c-(1/255))*this.sum)/(((this.sum-1)*c)-((this.sum*(1/255))+e));
		 int kl=(int) Math.ceil(thing);
		 return kl;
	 }
	 public LinkedList<Byte[]> makeCol(byte[] cb, int k){
		 //takes in the an array of bytes from the ciphertext and the keylength
		 //returns an Linkedlist of Byte[] of each column where 
		 //the index relating to the Byte[] is the col index in a full matrix of byte values
		 //so LinkedList.remove(0)[0] would be element [0][0] in the original ciphertext matrix
		 //returns linked list indexed by column number
		 byte[][]matrix=new byte[k][cb.length/k];
		 for(int i=0;i<k;i++){
			 for(int j=0;j<cb.length;j++){
				 matrix[i][j]=cb[j];
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

}

