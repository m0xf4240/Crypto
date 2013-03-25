
public class Caesar {

	private char[] ciphertext;
	
	public void Caesar(char[] ciphertext){
		setCiphertext(ciphertext);
	}
	
	private char[] decrypt(int key){
		char[] cleartext = this.getCiphertext();
		return cleartext;
	}

	public char[] getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(char[] ciphertext) {
		this.ciphertext = ciphertext;
	}
	
}
