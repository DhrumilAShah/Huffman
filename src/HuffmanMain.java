import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HuffmanMain {
	
	public static void main(String[] args) throws IOException {
		
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
		  
		  insertMin(heapArr,new Heap('z',2));
		  
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
		int right = 2*index+2;
		int left = 2*index+1;
		Heap root = null;
		Heap current = heap.get(index);
		if(right < heap.size()) {//lowest = lowest of tree 
			root = heap.get((heap.get(left)).getFrequency() < (heap.get(right)).getFrequency() ? left : right) ;
			root = (root.getFrequency() < current.getFrequency()) ? root : current;
		}else {//if flow comes here, it means that right doesnot exist//so lowest = lowest of left and current
			root = (heap.get(left).getFrequency() < current.getFrequency()) ? root : current;
		}
		int rootIndex = heap.indexOf(root);
		if(rootIndex != index){
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
	
	public static Heap extractMin(ArrayList<Heap> heap) {//check heap size 
		Heap first = heap.get(0);//fetch first
		heap.set(0, heap.get(heap.size()-1));//swap (first ,last)
		heap.remove(heap.size()-1);//delete last
		if(heap.size()>1)	minDownHeap(heap,0);
		return first;
	}
	
	public static void insertMin(ArrayList<Heap> heap,Heap item) {
		int size = heap.size();
		int parentIndex = (int)Math.floor((size-1)/2);
		heap.add(item);
		while( (heap.get(parentIndex)).getFrequency() > item.getFrequency() && size>0 ){
			heap.set(size,heap.get(parentIndex));
			size = parentIndex;
			parentIndex = (int)Math.floor((size-1)/2);
		}
		heap.set((size>0)?size:0, item);
		
	}
	
	
	
	
	
	
	
} 
