import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

	private static int dataBuff;
	private static int lenOfDataBuff;
	private static BufferedOutputStream os;

	public FileWriter(String filename) {
		try {
			os = new BufferedOutputStream(new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public  void writeFile(char[] charArray, String codes[]) {
		
		try {
			for(char k : charArray) {
				for(char c : codes[k].toCharArray()) {
					//System.out.println("inside writefile...");
					writeBool( (c=='1') ? true : false );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean deleteFile(String filename) {
		return new File(filename).delete();
	}


	public void writeBool(boolean bit) {
		try {
			//System.out.println("inside bool...");
			dataBuff <<= 1;
			if (bit) dataBuff |= 1;
			//System.out.println("bool-->"+dataBuff);
			if (++lenOfDataBuff == 8) writeDataBuffer();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Write char to file
	 * @param ch
	 */
	public void writeChar(char ch) {
		writeByte(ch);
	}


	private static void writeDataBuffer() {

		if(lenOfDataBuff == 0) return;
		if (lenOfDataBuff > 0) 
			dataBuff<<= 8 - lenOfDataBuff;

		try {
			dataBuff = dataBuff & 0xFF;
			System.out.println("-->"+dataBuff);
			os.write(dataBuff);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lenOfDataBuff = 0;
		dataBuff = 0;
	}


	private void writeByte(int byt) {
		if (lenOfDataBuff == 0) { 
			try {
				os.write(byt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else { // if buffer has data in it and we need to write character of 8 bits
			for (int i = 0; i < 8; i++) {
				boolean bit = ((byt >>> (7-i)) & 1) == 1;//fetch a single bit from byt
				writeBool(bit);
			}
		}
	}

	public void close() {
		writeDataBuffer();
		try {
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}





}
