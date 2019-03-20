
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

//	public Heap getLeft() {
//		return left;
//	}
//	public void setLeft(Heap left) {
//		if(left!=null) {
//			isLeaf=false;
//		}
//		this.left = left;
//	}
//	public Heap getRight() {
//		return right;
//	}
//	public void setRight(Heap right) {
//		if(right!=null) {
//			isLeaf=false;
//		}
//		this.right = right;
//	}
//	public int getFrequency() {
//		return freq;
//	}
//	public void setFrequency(int freq) {
//		this.freq = freq;
//	}
//	public char getData() {
//		return data;
//	}
//	public void setData(char data) {
//		this.data = data;
//	}
//
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
	
	@Override
	public String toString() {
		return "Frequency: "+freq+" Character:"+data ;
	}
}
