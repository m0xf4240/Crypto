
public class Caesar {

	private char[] ciphertext;
	
	public Caesar(char[] ciphertext){
		setCiphertext(ciphertext);
	}
	
	public char[] decrypt(int key){
		char[] cleartext = this.getCiphertext();
		return cleartext;
	}
	
	public void blivit(){
		System.out.println("F00B4R");
	}

	public char[] getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(char[] ciphertext) {
		this.ciphertext = ciphertext;
	}
	
}
