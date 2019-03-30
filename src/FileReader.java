import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {

	private static int buffer;
	private static int lengthOfBuffer;
	private static BufferedInputStream br;

	public FileReader() {
		buffer = 0;
		lengthOfBuffer = 0;
		br = null;
	}

	public FileReader(String filename) {
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
				i = (int)((char)i & 0xFF);//0xff is 32-bit, it will mask zeros.
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
			if(lengthOfBuffer == 0) {
				buffer = br.read();
				lengthOfBuffer = 8;
				return (char)buffer;
			} else if (lengthOfBuffer == 8) {
				int tempBuff = buffer;
				populateBuffer();
				return (char)tempBuff;
			}else if(lengthOfBuffer > 0) { //so we need to append 8-sizeOfBuffer to new buffer and return
				int prevLen = lengthOfBuffer;
				int tempBuff = buffer;
				tempBuff = tempBuff << (8- prevLen);
				populateBuffer();
				lengthOfBuffer = prevLen;
				tempBuff = tempBuff | (buffer >>> lengthOfBuffer); // append logic
				return (char) tempBuff;
			} else { //size is -1
				return (char)-1;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return (char)-1;
		}
	}

	public boolean readBool() {
		try {
			if(lengthOfBuffer == 0) {
				buffer = br.read();
				//buffer = buffer & 0xFF;
				//System.out.println("inbool>>>>>"+(int)buffer);
				lengthOfBuffer = 8;
			}
				lengthOfBuffer--;
				//Here, n >> k shifts the k-th bit into the least significant position, 
				//and & 1 masks out everything else.
				return  ((buffer >> lengthOfBuffer) & 1) == 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	public void populateBuffer() {
		try {
			buffer = br.read();
			lengthOfBuffer = 8;
			if(buffer==-1) {
				lengthOfBuffer = -1;
			}
		} catch (IOException e) {
			e.printStackTrace();
			lengthOfBuffer = -1;
		}
		
	}

	public int getCurrentFileSize() {
		return 0;
	}

	public void close() {
		try {
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
