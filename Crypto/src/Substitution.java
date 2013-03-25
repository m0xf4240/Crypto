import java.io.IOException;
public class Substitution {
	private char[] ciphertext;
	private int spaceSize;

	public Substitution(char[] ciphertext, int size){
		setCiphertext(ciphertext);
		this.setSpaceSize(size);
	}

	public void bruteForce() throws IOException{
		for (int key=0; key<this.getSpaceSize(); key++){
			char[] cleartext = this.decrypt(key);
			for (int i=0; i<cleartext.length; i++){
				System.out.print(cleartext[i]);
			}
			System.out.println("\n");

			//Press enter to continue
			System.in.read();
		}
	}

	private char[] decrypt(int key){
		char [] cleartext = new char[this.getCiphertext().length];
		for (int i = 0; i < cleartext.length; i = (i + 1)) {
			cleartext[i] = (char)((this.getSpaceSize() + (this.getCiphertext()[i] - key)) % this.getSpaceSize());
		}
		return cleartext;
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

	public int[] frequency() {
		int[] counts = new int[this.getSpaceSize()];
		for (int i=0; i<this.getCiphertext().length; i++) {
			int num = (int)(getCiphertext()[i]);
			counts[num] += 1;
		}
		return counts;
	}
	
}
