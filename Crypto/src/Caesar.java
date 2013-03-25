
public class Caesar {

	private char[] ciphertext;
	private int spaceSize;

	public Caesar(char[] ciphertext, int size){
		setCiphertext(ciphertext);
		this.setSpaceSize(size);
	}
	
	public char[] decrypt(int key){
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
}
