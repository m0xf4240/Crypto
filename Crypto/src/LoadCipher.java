import java.io.*;
import java.util.*;

public class LoadCipher {

	public LoadCipher(){
	}
		
	public ArrayList<Byte> load(File file) {
		ArrayList<Byte> message= new ArrayList<Byte>();
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			int index=0;
			while (true) {
				message.add(index++, new Byte(raf.readByte()));
			}
		} catch (EOFException f){
			System.out.println("Done loading "+file.getName()+".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		message.trimToSize();
		return message;
	}
}

