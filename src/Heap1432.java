// Dhrumil Shah cs610 1432 prp
/*
 * @author DhrumilShah
 * The underlying node used
 */
public class Heap1432 {

	public Heap1432 left;
	public Heap1432 right;
	public int freq;
	public char data;
	public boolean isLeaf = true;
	public String code="";
	
	public Heap1432(char data, int freq) {
		this.data = data;
		this.freq = freq;
	}
	
	public Heap1432(int freq, Heap1432 left, Heap1432 right) {
		this.freq = freq;
		this.left = left;
		this.right = right;
		if(left!=null || right!=null)	isLeaf=false;
	}
	
	public Heap1432() {
		this.data=0;
		this.freq=0;
		this.code="";
	}
	
	@Override
	public String toString() {
		return "Frequency: "+freq+" Character:"+data +" IsLeaf: "+isLeaf;
	}
}
