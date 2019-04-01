import java.io.File;
import java.util.ArrayList;

public class henc {

	static String[] codes = new String[256];
	static FileWriter fileWriter;
	static FileReader fileReader;

	public static void main(String[] args) throws Exception {
		
		String fileName = null;

		if(args.length!=1) 
			throw new Error("File name should be the first argument!");
		
		fileName = args[0].trim();
		
		File file = new File(fileName);

		fileReader = new FileReader();

		char[] charArray  = fileReader.readFileForEncoder(file).toCharArray();
		
		//fileReader.close();

		int[] freq = getFrequency(charArray);

		ArrayList<Heap> heapArr = toFrequencyArray(freq);

		buildMinHeap(heapArr,heapArr.size());

		fileWriter = new FileWriter(fileName+".huf");

		encode(heapArr);
		
		fileWriter.writeFileSize(charArray.length);		

		fileWriter.writeFile(charArray,codes);	

		fileWriter.deleteFile(fileName);
		fileWriter.close();
		
		System.out.println("Compression complete...");
		
		System.exit(0);
	} 

	public static int[] getFrequency(char[] charArray) {
		int[] frequency = new int[256];
		for(char c:charArray) frequency[c]++;
		return frequency;
	}

	public static ArrayList<Heap> toFrequencyArray(int[] freq) {
		ArrayList<Heap> alHeap= new ArrayList<Heap>();
		int index = 0;
		for(int i:freq) {
			if(i!=0) alHeap.add(new Heap((char)index,i));
			++index;
		}
		return alHeap;
	}

	public static void minDownHeap(ArrayList<Heap> heap,int index) {
		int right = 2*index+2;
		int left = 2*index+1;
		
		//Here root will be lowest of right and left.
		Heap root = null;
		Heap current = heap.get(index);
		
		if(right < heap.size()) {
			//root = lowest of three tree 
			root = heap.get((heap.get(left)).freq <= (heap.get(right)).freq ? left : right);
			root = (root.freq <= current.freq) ? root : current;
		}else {
			//if flow comes here, it means that right doesnot exist
			//so root = lowest of left and current
			root = (heap.get(left).freq <= current.freq) ? heap.get(left) : current;
		}
		
		int rootIndex = heap.indexOf(root);
		
		if(rootIndex != index){
			//if flow come here it means that root is not lowest
			//so we need to set root at top
			heap.set(rootIndex, current); //swap(root,current)
			heap.set(index,root);
			
			//check if it is last node or a subtree by current<lastnode
			if(rootIndex < (int)Math.floor( (heap.size()-1)/2 ) ) 
				minDownHeap(heap,rootIndex);
		}
		
	}

	public static void buildMinHeap(ArrayList<Heap> heap,int size) {
		int max = (int)Math.floor((size-1)/2);
		for(int i=max-1; i>=0; i--) 
			minDownHeap(heap,i);
	}

	public static Heap extractMin(ArrayList<Heap> heap) throws Exception {
		//check heap size 
		if(heap.size()<1) throw new Exception("Limit Reached");
		Heap first = heap.get(0);//fetch first
		heap.set(0, heap.get(heap.size()-1));//swap (first ,last)
		heap.remove(heap.size()-1);//delete last
		if(heap.size()>1)	minDownHeap(heap,0);
		return first;
	}

	public static void insertMin(ArrayList<Heap> heap,Heap item) {
		int size = heap.size();
		int parentIndex = (int)Math.floor((size-1)/2);
		//add item to last
		heap.add(item);
		
		while( (heap.get(parentIndex)).freq > item.freq && size > 0 ){
			//loop until parents frequency is greater then its child
			//swap with parent
			heap.set(size,heap.get(parentIndex));
			size = parentIndex;
			parentIndex = (int)Math.floor((size-1)/2);
		}
		
		heap.set((size > 0) ? size : 0, item);
	}

	public static void encode(ArrayList<Heap> heap) throws Exception {
		while(heap.size() > 1) {
			//remove unit last one is root 
			Heap left = extractMin(heap);
			Heap right = extractMin(heap);
			insertMin(heap,new Heap(left.freq + right.freq,left,right));
		}
		Heap root = extractMin(heap);
		setHeapCodes(root);
		writeHeapToFile(root);
	}

	public static void setHeapCodes(Heap root){
		//is root is a leaf then only it will have a character
		if (!root.isLeaf) {	
			if (root.left != null) {
				root.left.code = root.left.code.concat(root.code+"0");
				setHeapCodes(root.left);
			}
			
			if (root.right != null){
				root.right.code = root.right.code.concat(root.code+"1");
				setHeapCodes(root.right);
			}	
		} else {
			codes[(int)root.data] = root.code;
		}
	}

	public static void writeHeapToFile(Heap root) {
		try {
			if (root.isLeaf) {
				fileWriter.writeBool(true);
				fileWriter.writeChar(root.data);
			}else {
				fileWriter.writeBool(false);
				writeHeapToFile(root.left);
				writeHeapToFile(root.right);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}