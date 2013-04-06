import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public final class BookAnalyzer {

	public BookAnalyzer(){
	}

	public HashMap<Byte,Integer> analyzeByte (File file) throws IOException{
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

	public HashMap<Short,Integer> analyze2 (File file) throws IOException{
		HashMap<Short,Integer> count = new HashMap<Short, Integer>();
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			Byte first= new Byte(raf.readByte());
			Byte second = new Byte(raf.readByte());
			short current = (short) ((first << 8) | (second & 0xFF));
			while (true) {
				if (count.isEmpty()){
					count.put(current, 1);
				} else if (! count.containsKey(current)){
					count.put(current, 1);
				} else {
					count.put(current, count.get(current)+1);
				}
				first=second;
				second = new Byte(raf.readByte());
				current = (short) ((first << 8) | (second & 0xFF));
			}
		} catch (EOFException f){
			for (Iterator<Map.Entry<Short, Integer>> it = count.entrySet().iterator(); it.hasNext();){
				Map.Entry<Short, Integer> entry = it.next();
				Integer n = entry.getValue();
				Short s = entry.getKey();
				Byte second= (byte)(s & 0xff); 
				Byte first = (byte)((s >> 8) & 0xff);
				if(n != null) {
					System.out.println(s+", "+(char)first.byteValue()+""+(char)second.byteValue()+", "+n);
				}
			}
			System.out.println("Done reading "+file.getName()+".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	public HashMap<Integer,Integer> analyze (File file, int ng) throws IOException{
		HashMap<Integer,Integer> count = new HashMap<Integer, Integer>();
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			int current;
			switch (ng){
			case 1:	current = wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), new Byte((byte) 0), new Byte(raf.readByte()));
			break;
			case 2:	current = wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), new Byte(raf.readByte()), new Byte(raf.readByte()));
			break;
			case 3:	current = wrapCurrent(new Byte((byte) 0), new Byte(raf.readByte()), new Byte(raf.readByte()), new Byte(raf.readByte()));
			break;
			case 4:	current = wrapCurrent(new Byte(raf.readByte()), new Byte(raf.readByte()), new Byte(raf.readByte()), new Byte(raf.readByte()));
			break;
			default: current=(byte)-127; //Chinese character, something bad happened.
			break;
			}

			while (true) {
				if (count.isEmpty()){
					count.put(current, 1);
				} else if (! count.containsKey(current)){
					count.put(current, 1);
				} else {
					count.put(current, count.get(current)+1);
				}
				switch (ng){
				case 1:	current=wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), new Byte((byte) 0), new Byte(raf.readByte()));
				break;
				case 2:	current=wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), unwrapCurrent(current)[3], new Byte(raf.readByte()));
				break;
				case 3:	current=wrapCurrent(new Byte((byte) 0), unwrapCurrent(current)[2], unwrapCurrent(current)[3], new Byte(raf.readByte()));
				break;
				case 4:	current=wrapCurrent(unwrapCurrent(current)[1], unwrapCurrent(current)[2], unwrapCurrent(current)[3], new Byte(raf.readByte()));
				break;
				default: current=(byte)-127; //Chinese character, something bad happened.
				break;
				}
			}
		} catch (EOFException f){
			for (Iterator<Map.Entry<Integer, Integer>> it = count.entrySet().iterator(); it.hasNext();){
				Map.Entry<Integer, Integer> entry = it.next();
				Integer n = entry.getValue();
				Integer i = entry.getKey();
				byte[] b = unwrapCurrent(i);

				Byte first = b[0]; //left-most
				Byte second= b[1]; 
				Byte third = b[2];
				Byte fourth= b[3]; //right-most
				if(n != null) {
//					System.out.println(i+", "+(char)first.byteValue()+""+(char)second.byteValue()+""+(char)third.byteValue()+""+(char)fourth.byteValue()+", "+n);
				}
			}
			System.out.println("Done reading "+file.getName()+".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	public HashMap<Integer,Integer> analyze (ArrayList<Byte> text, int ng) throws IOException{
		HashMap<Integer,Integer> count = new HashMap<Integer, Integer>();
		int current;
		Byte b1, b2, b3, b4;
		b4 = text.remove(text.size()-1);
		b3 = text.remove(text.size()-1);
		b2 = text.remove(text.size()-1);
		b1 = text.remove(text.size()-1);
		switch (ng){
		case 1:	current = wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), new Byte((byte) 0), b1);
		break;
		case 2:	current = wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), b1, b2);
		break;
		case 3:	current = wrapCurrent(new Byte((byte) 0), b1, b2, b3);
		break;
		case 4:	current = wrapCurrent(b1, b2, b3, b4);
		break;
		default: current=(byte)-127; //Chinese character, something bad happened.
		break;
		}
		while (text.size()>=1) {
			
			if (count.isEmpty()){
				count.put(current, 1);
			} else if (! count.containsKey(current)){
				count.put(current, 1);
			} else {
				count.put(current, count.get(current)+1);
			}
			switch (ng){
			case 1:	current=wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), new Byte((byte) 0), text.remove(text.size()-1));
			break;
			case 2:	current=wrapCurrent(new Byte((byte) 0), new Byte((byte) 0), text.remove(text.size()-1), unwrapCurrent(current)[0]);
			break;
			case 3:	current=wrapCurrent(new Byte((byte) 0), text.remove(text.size()-1), unwrapCurrent(current)[0], unwrapCurrent(current)[1]);
			break;
			case 4:	current=wrapCurrent(text.remove(text.size()-1), unwrapCurrent(current)[0], unwrapCurrent(current)[1], unwrapCurrent(current)[2]);
			break;
			default: current=(byte)-127; //Chinese character, something bad happened.
			break;
			}
		}
		return count;
	}
public byte[] analyzeToBytes(File cipher) throws IOException{
		
		RandomAccessFile a=new RandomAccessFile(cipher,"r");
		byte[]cbytes=new byte[(int)a.length()];
		try{
		a.read(cbytes);
		a.close();}
		catch(IOException e){
			e.printStackTrace();
		}
		return cbytes;
	}

	private Integer wrapCurrent (Byte b1, Byte b2, Byte b3, Byte b4){
		return ((0xFF & b1) << 24) | ((0xFF & b2) << 16) | ((0xFF & b3) << 8) | (0xFF & b4);
	}

	private byte[] unwrapCurrent (Integer i){
		return ByteBuffer.allocate(4).putInt(i).array();
	}
}
