import java.io.File;

public class hdec {
	
	
	static String[] codes = new String[256];
	static FileWriter fileWriter;
	static FileReader fileReader;
	
	
	public static void main(String[] args) {
		

		//File file = new File("s.txt.huf");
		//String fileName = file.getName();

		fileReader = new FileReader("s.txt.huf");
		
		//char[] charArray  = null; //fileReader.readFile(file).toCharArray();
		
		//System.out.println(charArray.length);
//		for(char c:charArray) {
//			System.out.print(c);
//		}
		
//		for(int i=0; i<10; i++) {
//			System.out.println(fileReader.readBool());
//		}
		
		decompress();
	}
	
	
    public static void decompress() {
    	try {

        // read in Huffman tree from input stream
        Heap root = readHeap(); 

        // number of bytes to write
        int length = 2;//BinaryStdIn.readInt();

        // decode using the Huffman tree
        for (int i = 0; i < length; i++) {
            Heap x = root;
            while (!x.isLeaf) {
            	boolean b = fileReader.readBool();
            	System.out.println(b);
                x = ( b ) ? x.right : x.left;
            }
            System.out.println(x.data);
            //BinaryStdOut.write(x.data, 8);
        }
        fileReader.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    
    private static Heap readHeap() {
        boolean isLeaf = fileReader.readBool();
        //System.out.println("-->"+isLeaf);
        return (isLeaf)? new Heap(fileReader.readChar(), -1) : new Heap(-1, readHeap(), readHeap());
    }
	
	
	

}
