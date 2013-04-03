import java.io.*;
import java.util.*;


public final class BookAnalyzer {
	
	public BookAnalyzer(){
	}

	public HashMap<Byte,Integer> analyze (File file) throws IOException{
		HashMap<Byte,Integer> count = new HashMap<Byte, Integer>();
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			Byte current;
			current = new Byte(raf.readByte());
			while (true) {
				if (count.isEmpty()){
					count.put(current, 1);
				} else if (! count.containsKey(current)){
					count.put(current, 1);
				} else {
					count.put(current, count.get(current)+1);
				}
				current = new Byte(raf.readByte());
			}
		} catch (EOFException f){
			for (Iterator<Map.Entry<Byte, Integer>> it = count.entrySet().iterator(); it.hasNext();){
				Map.Entry<Byte, Integer> entry = it.next();
				Integer n = entry.getValue();
				Byte b = entry.getKey();
				if(n != null) {
					//System.out.println(b+", "+n);
				}
			}
			System.out.println("Done reading "+file.getName()+".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}
