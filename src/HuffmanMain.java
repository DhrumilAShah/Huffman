import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HuffmanMain {
	
	public static void main(String[] args) throws Exception {
		
		File file = new File("huffmanTest"); 	    
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));  
		  int i = 0;
		  StringBuilder str = new StringBuilder();
		  while ((i = br.read())!=-1){ 
			 i = (int)((char)i & 0xFF);//0xff is 32-bit, it will mask zeros.
			 str.append((char)i);
		  } 
		  br.close();
		  
		  char[] charArray  = new String(str).toCharArray();
		  int[] freq = getFrequency(charArray);
		  
		  ArrayList<Heap> heapArr = toFrequencyArray(freq);
		  
		  buildMinHeap(heapArr,heapArr.size());
		  	  
		  //System.out.println(extractMin(heapArr));
		  
		  //insertMin(heapArr,new Heap('z',2));
		  
		  encode(heapArr);
		  
		  for(Heap  h : heapArr) System.out.println(h);

		  
	} 
	
	public static int[] getFrequency(char[] charArray) {
		int[] frequency = new int[256];
		for(char c:charArray) frequency[c]++;
		return frequency;
	}
	
	public static ArrayList<Heap> toFrequencyArray(int[] freq) {
		ArrayList<Heap> alHeap= new ArrayList<Heap>();
		int index=0;
		for(int i:freq) {
			if(i!=0) alHeap.add(new Heap((char)index,i));
			++index;
		}
		return alHeap;
	}
	
	public static void minDownHeap(ArrayList<Heap> heap,int index) {
		//System.out.println("inside minDownHeap: "+heap.size()+"--"+index);
		int right = 2*index+2;
		int left = 2*index+1;
		Heap root = null;
		Heap current = heap.get(index);
		if(right < heap.size()) {//lowest = lowest of tree 
			root = heap.get((heap.get(left)).freq < (heap.get(right)).freq ? left : right) ;
			root = (root.freq < current.freq) ? root : current;
			//System.out.println("if-->"+heap.indexOf(root));
		}else {//if flow comes here, it means that right doesnot exist//so lowest = lowest of left and current
			root = (heap.get(left).freq < current.freq) ? heap.get(left) : current;
			//System.out.println("else-->"+heap.indexOf(root));
		}
		int rootIndex = heap.indexOf(root);
		if(rootIndex != index){
			//System.out.println(heap.size()+"--"+rootIndex+"--"+index);
			//System.out.println(heap.get(rootIndex));
			//for(Heap  h : heap) System.out.println(heap.indexOf(h)+"--"+h);
			heap.set(rootIndex, current);//swap(lowest,current)
			heap.set(index,root);	
			if(rootIndex < (int)Math.floor( (heap.size()-1)/2 ) ) //check if it is last node or a subtree by current<lastnode
				minDownHeap(heap,rootIndex);
		}
	}
	
	public static void buildMinHeap(ArrayList<Heap> heap,int size) {
		int max = (int)Math.floor((size-1)/2);
		for(int i=max-1; i>=0; i--) {
			minDownHeap(heap,i);
		}
	}
	
	public static Heap extractMin(ArrayList<Heap> heap) throws Exception {//check heap size 
		//System.out.println("ExtractMin Called:"+heap.size());
		if(heap.size()<1) throw new Exception("Cannot extract more!");
		Heap first = heap.get(0);//fetch first
		heap.set(0, heap.get(heap.size()-1));//swap (first ,last)
		heap.remove(heap.size()-1);//delete last
		if(heap.size()>1)	minDownHeap(heap,0);
		return first;
	}
	
	public static void insertMin(ArrayList<Heap> heap,Heap item) {
		//System.out.println("Insert min called:"+heap.size()+"--"+item);
		int size = heap.size();
		int parentIndex = (int)Math.floor((size-1)/2);
		heap.add(item);
		while( (heap.get(parentIndex)).freq > item.freq && size>0 ){
			heap.set(size,heap.get(parentIndex));
			size = parentIndex;
			parentIndex = (int)Math.floor((size-1)/2);
		}
		heap.set((size>0)?size:0, item);
	}
	
	public static void encode(ArrayList<Heap> heap) throws Exception {
		while(heap.size()>1) {
			//System.out.println(heap.size());
			Heap left = extractMin(heap);
			Heap right = extractMin(heap);
			insertMin(heap,new Heap(left.freq + right.freq,left,right));
		}
		Heap root = extractMin(heap);
		setCodes(root);
		
	}
	
	public static void setCodes(Heap root) {
	if (!root.isLeaf) {	
			if (root.right != null) {
				root.right.code = root.right.code.concat(root.code+"1");
				setCodes(root.right);
			}
			if (root.left != null) {
				root.left.code = root.left.code.concat(root.code+"0");
				setCodes(root.left);
			}
		}
	
	}
	
	
	
	
	
	
	
} 
