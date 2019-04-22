// Dhrumil Shah cs610 1432 prp
import java.io.File;
import java.util.ArrayList;
/*
 * @author DhrumilShah
 * To encode file in .huf format
 */

public class henc1432 {

	static String[] codes = new String[256];
	static FileWriter1432 fileWriter;
	static FileReader1432 fileReader;

	public static void main(String[] args) throws Exception {

		String fileName = null;

		if(args.length!=1) 
			throw new Error("File name should be the first argument!");

		fileName = args[0].trim();

		File file = new File(fileName);

		fileReader = new FileReader1432();

		char[] charArray  = fileReader.readFileForEncoder(file).toCharArray();

		//fileReader.close();

		int[] freq = getFrequency(charArray);

		ArrayList<Heap1432> heapArr = toFrequencyArray(freq);

		buildMinHeap(heapArr,heapArr.size());

		fileWriter = new FileWriter1432(fileName+".huf");

		fileWriter.writeFileSize(charArray.length);

		encode(heapArr);

		//fileWriter.writeFileSize(charArray.length);		

		fileWriter.writeFile(charArray,codes);	

		fileWriter.deleteFile(fileName);
		fileWriter.close();

		//System.out.println("Compression complete...");

		double bytes = new File(fileName+".huf").length();
		System.out.println("File size after compression: "+bytes+" bytes");

		int sum = 0;
		for(int i=0; i<codes.length;i++) {
			if(codes[i]!=null) {
				sum += codes[i].length() * freq[i];
			}
		}

		System.out.println("Total number of bits utilized for the encoding of the original file: "+sum);

		System.exit(0);
	} 

	public static int[] getFrequency(char[] charArray) {
		int[] frequency = new int[256];
		for(char c:charArray) frequency[c]++;
		return frequency;
	}

	public static ArrayList<Heap1432> toFrequencyArray(int[] freq) {
		ArrayList<Heap1432> alHeap= new ArrayList<Heap1432>();
		int index = 0;
		for(int i:freq) {
			if(i!=0) alHeap.add(new Heap1432((char)index,i));
			++index;
		}
		return alHeap;
	}

	public static void minDownHeap(ArrayList<Heap1432> heap,int index) {
		int right = 2*index+2;
		int left = 2*index+1;

		//Here root will be lowest of right and left.
		Heap1432 root = null;
		Heap1432 current = heap.get(index);

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

	public static void buildMinHeap(ArrayList<Heap1432> heap,int size) {
		int max = (int)Math.floor((size-1)/2);
		for(int i=max-1; i>=0; i--) 
			minDownHeap(heap,i);
	}

	public static Heap1432 extractMin(ArrayList<Heap1432> heap) throws Exception {
		//check heap size 
		if(heap.size()<1) throw new Exception("Limit Reached");
		Heap1432 first = heap.get(0);//fetch first
		heap.set(0, heap.get(heap.size()-1));//swap (first ,last)
		heap.remove(heap.size()-1);//delete last
		if(heap.size()>1)	minDownHeap(heap,0);
		return first;
	}

	public static void insertMin(ArrayList<Heap1432> heap,Heap1432 item) {
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

	public static void encode(ArrayList<Heap1432> heap) throws Exception {
		while(heap.size() > 1) {
			//remove unit last one is root 
			Heap1432 left = extractMin(heap);
			Heap1432 right = extractMin(heap);
			insertMin(heap,new Heap1432(left.freq + right.freq,left,right));
		}
		Heap1432 root = extractMin(heap);
		setHeapCodes(root);
		writeHeapToFile(root);
	}

	public static void setHeapCodes(Heap1432 root){
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

	public static void writeHeapToFile(Heap1432 root) {
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