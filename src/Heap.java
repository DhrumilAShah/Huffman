
public class Heap {

	public Heap left;
	public Heap right;
	public int freq;
	public char data;
	public boolean isLeaf = true;
	public String code="";
	
	public Heap(char data, int freq) {
		this.data = data;
		this.freq = freq;
	}
	
	public Heap(int freq, Heap left, Heap right) {
		this.freq = freq;
		this.left = left;
		this.right = right;
		if(left!=null || right!=null) {
			isLeaf=false;
		}
	}
	
	public Heap() {
		this.data=0;
		this.freq=0;
		this.code="";
	}
	
	@Override
	public String toString() {
		return "Frequency: "+freq+" Character:"+data ;
	}
}
