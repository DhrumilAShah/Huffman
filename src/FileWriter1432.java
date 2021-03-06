// Dhrumil Shah cs610 1432 prp
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/*
 * @author DhrumilShah
 * To write data into file
 */
public class FileWriter1432 {

	private static int dataBuff;
	private static int sizeOfDataBuff;
	private static BufferedOutputStream optStream;

	public FileWriter1432(String filename) {
		try {
			optStream = new BufferedOutputStream(new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void writeFile(char[] charArray, String codes[]) {

		try {
			for(char k : charArray)
				for(char c : codes[k].toCharArray()) 
					writeBool( (c=='1') ? true : false );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean deleteFile(String filename) {
		return new File(filename).delete();
	}


	public void writeBool(boolean bit) {
		try {
			//shifft all elements to left by 1 
			dataBuff <<= 1;
			//append 1 in bit
			if (bit) dataBuff |= 1;
			//increment size of buffer and check 
			//if size of buff = 8 then write 1 byte
			if (++sizeOfDataBuff == 8) writeDataBuffer();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void writeChar(char ch) throws Exception {
		if (ch < 0 || ch >= 256)	throw new Exception("Only 8-bit char acepted: " + ch);
		writeByte(ch);
	}


	private static boolean writeDataBuffer() {

		if(sizeOfDataBuff == 0) return false;
		if (sizeOfDataBuff > 0) 
			dataBuff<<= 8 - sizeOfDataBuff;
		try {
			dataBuff = dataBuff & 0xFF;
			optStream.write(dataBuff);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		sizeOfDataBuff = 0;
		dataBuff = 0;
		return true;
	}


	private void writeByte(int byt) {
		if (sizeOfDataBuff == 0) { 
			try {
				optStream.write(byt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else { 
			// if flow comes here it means, we need to write a character
			// if buffer has data in it and we need to write character of 8 bits
			for (int i = 0; i < 8; i++) {
				//fetch a single bit from byt
				//write bit by bit, >>> will move to right and append 0 
				//& 1 will remove all the elements except the last one
				writeBool( ((byt >>> (8-i-1)) & 1) == 1 );
			}
		}
	}

	public void writeFileSize(int size) {
		for(int i=24; i >= 0; i = i-8) 
			writeByte((size >>> i) & 0xff);
	}
	//to close write buffer
	public void close() {
		writeDataBuffer();
		try {
			optStream.flush();
			optStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
