
public class NGram {
	
	private String s;
	private int c;
	
	public NGram(String s){
		this.setS(s);
		this.setCount(1);
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public int getCount() {
		return c;
	}

	public void setCount(int c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return "NGram [s=" + s + ", c=" + c + "]";
	}

}
