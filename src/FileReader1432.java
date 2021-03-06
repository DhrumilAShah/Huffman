// Dhrumil Shah cs610 1432 prp
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/*
 * @author DhrumilShah
 * To read data from file
 */
public class FileReader1432 {

	private static int dataBuff;
	private static int sizeOfDataBuff;
	private static BufferedInputStream br;
	private static final int EOF = -1;

	public FileReader1432() {
		dataBuff = 0;
		sizeOfDataBuff = 0;
		br = null;
	}

	public FileReader1432(String filename) {
		try {
			br = new BufferedInputStream(new FileInputStream(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readFileForEncoder(File file) {
		BufferedInputStream br;
		try {
			br = new BufferedInputStream(new FileInputStream(file));  
			int i = 0;
			StringBuilder str = new StringBuilder();
			while ( (i = br.read())!=-1 ){ 
				//& 0xFF will make the char 8 bit
				//that is remove all the extra bits that are > 8
				i = (int)((char)i & 0xFF);
				str.append((char)i);
			} 
			br.close();
			return new String(str);  
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public char readChar(){
		try {
			if(sizeOfDataBuff == 0) {
				
				populateBuffer();
				int tempBuff = dataBuff;
				populateBuffer();
				return (char)(tempBuff & 0xFF);
				
			} else if (sizeOfDataBuff == 8) { 
				
				//means there is a whole character in buffer
				int tempBuff = dataBuff;
				populateBuffer();
				return (char)(tempBuff& 0xFF);
				
			}else if(sizeOfDataBuff > 0) {
				
				//so we need to append 8-sizeOfBuffer to new buffer and return
				int prevLen = sizeOfDataBuff;
				int tempBuff = dataBuff;
				tempBuff = tempBuff << (8- prevLen);
				populateBuffer();
				sizeOfDataBuff = prevLen;
				// append logic, first move databuffer 
				//then OR(append) it to tempBuff
				return (char)((tempBuff | (dataBuff >>> prevLen))& 0xFF);
				
			} else { 
				//size is -1, ie end of file
				dataBuff = EOF;
				sizeOfDataBuff = EOF;
				throw new Exception("End of File...!!!");
			}
		}catch(Exception e){
			e.printStackTrace();
			dataBuff = EOF;
			sizeOfDataBuff = EOF;
			return (char)EOF;
		}
	}

	public boolean readBool() {
		try {
			if(sizeOfDataBuff == 0) {
				dataBuff = br.read();
				sizeOfDataBuff = 8;
			}
			sizeOfDataBuff--;
			//Here, n >> k shifts the k-th bit into the least significant position, 
			//and & 1 masks out everything else.
			//it will return the bit which is on sizeOfDataBuff Position
			return  ((dataBuff >> sizeOfDataBuff) & 1) == 1;
		} catch (IOException e) {
			e.printStackTrace();
			dataBuff = EOF;
			sizeOfDataBuff = EOF;
		}
		return true;
	}
	//read and fill buffer
	public void populateBuffer() {
		try {
			dataBuff = br.read();
			sizeOfDataBuff = 8;
			if(dataBuff == EOF) {
				sizeOfDataBuff = EOF;
			}
		} catch (IOException e) {
			e.printStackTrace();
			dataBuff = EOF;
			sizeOfDataBuff = EOF;
		}
	}
	//while compression first bytes is file size
	public int getCurrentFileSize() {
		int size = 0;
		//int in java is 4 byte so we need to iterate 4 times
		for (int i = 0; i < 4; i++) { 
			size<<=8;//move size to left by 8 
			size = size | (readChar() & 0xFF); // append 1 byte at a time
		}
		return size;
	}
	//will close file reader
	public void close() {
		try {
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
