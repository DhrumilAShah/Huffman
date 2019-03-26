import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileReader {

	
	public String readFile(File file) {
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
	
}
