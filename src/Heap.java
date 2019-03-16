
public class Heap {

	private Heap left;
	private Heap right;
	private int freq;
	private char data;
	
	public Heap(char data, int freq) {
		this.data = data;
		this.freq = freq;
	}
	
	public Heap() {
		this.data=0;
		this.freq=0;
	}

	public Heap getLeft() {
		return left;
	}
	public void setLeft(Heap left) {
		this.left = left;
	}
	public Heap getRight() {
		return right;
	}
	public void setRight(Heap right) {
		this.right = right;
	}
	public int getFrequency() {
		return freq;
	}
	public void setFrequency(int freq) {
		this.freq = freq;
	}
	public char getData() {
		return data;
	}
	public void setData(char data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Character: "+data+" Frequency: "+freq;
	}
	
	
}
