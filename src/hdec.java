public class hdec {

	static FileWriter fileWriter;
	static FileReader fileReader;	

	public static void main(String[] args) {
		try {

			String fileName = null;

			if(args.length!=1) 
				throw new Error("File name should be the first argument!");
			
			fileName = args[0].trim();
			
			fileReader = new FileReader(fileName);
			fileWriter = new FileWriter(fileName.substring(0,fileName.length()-4).trim());
			
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
			// read huffmanTree
			Heap root = readHeap(); 	
			// number of bytes to write
			int fileSize = fileReader.getCurrentFileSize();

			for (int i = 0; i < fileSize; i++) {
				Heap temp = root;
				while (!temp.isLeaf) 
					temp = ( fileReader.readBool() ) ? temp.right : temp.left;         
				fileWriter.writeChar(temp.data);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static Heap readHeap() {
		boolean isLeaf = fileReader.readBool();
		return (isLeaf)? new Heap(fileReader.readChar(), -1) : new Heap(-1, readHeap(), readHeap());
	}




}
