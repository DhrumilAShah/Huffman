// Dhrumil Shah cs610 1432 prp
/*
 * @author DhrumilShah
 * To decode the huffman file
 */
public class hdec1432 {

	static FileWriter1432 fileWriter;
	static FileReader1432 fileReader;	

	public static void main(String[] args) {
		try {

			String fileName = null;

			if(args.length!=1) 
				throw new Error("File name should be the first argument!");
			
			fileName = args[0].trim();
			
			fileReader = new FileReader1432(fileName);
			//because fileName will have .huf at end
			fileWriter = new FileWriter1432(fileName.substring(0,fileName.length()-4).trim());
			
			decompress();
			
			fileWriter.deleteFile(fileName);

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		fileWriter.close();
		fileReader.close();
		
		System.exit(0);
	}	

	public static void decompress() {
		try {
			// remaining file size to iterate over
			int fileSize = fileReader.getCurrentFileSize();
			// read the huffmanTree
			Heap1432 root = readHeap(); 	
			for (int i = 0; i < fileSize; i++) {
				Heap1432 temp = root;
				while (!temp.isLeaf) 
					temp = ( fileReader.readBool() ) ? temp.right : temp.left;         
				fileWriter.writeChar(temp.data);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static Heap1432 readHeap() {
		//if it is a leaf return heap instance with no right and left
		//else recurse left and right is readHeap()
		return (fileReader.readBool())? new Heap1432(fileReader.readChar(), -1) : new Heap1432(-1, readHeap(), readHeap());
	}




}
