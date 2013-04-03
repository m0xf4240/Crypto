import java.io.*;
import java.util.*;


public class CountText {

	public static void main (String args[]) throws IOException{
		//Error checking
		if (args.length != 1) {
			System.err.println("Feed me.");
			System.exit(1);
		}
		File file = new File(args[0]);
		if (!file.exists()) {
			System.out.println(args[0] + " does not exist.");
			return;
		}
		if (!(file.isFile() && file.canRead())) {
			System.out.println(file.getName() + " cannot be read from.");
			return;
		}

		//Declare variables
		int spaceSize=255;
		int messageLength=0;
		byte[] abCounts = new byte[spaceSize];
		
		HashMap<Byte,Integer> count = new HashMap<Byte, Integer>();

		//FileWriter fstream = new FileWriter("out4.txt");
//		FileWriter fstream = new FileWriter("outch.txt");
//		BufferedWriter out = new BufferedWriter(fstream);
//		Scanner keyboard = new Scanner(System.in);


		//Read file and count ngrams
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			//FileInputStream fis = new FileInputStream(file);
			Byte current;
			//char second;
			//char third;
			//current = new Byte((byte)fis.read());
			current = new Byte(raf.readByte());
//			second = (char) fis.read();
			while (true) {
				if (count.isEmpty()){
					count.put(current, 1);
				} else if (count.containsKey(current)){
					count.put(current, 1);
				} else {
					count.put(current, count.get(current)+1);
				}
				//TODO: Change to second/third/fourth as needed
				current = new Byte(raf.readByte());
				System.out.println(current);
				//abCounts[current] +=1;//((int)current*spaceSize*spaceSize)+(int)second*spaceSize+(int)third]+=1;
				//current=second;
				//second=third;
				messageLength++;
			}
		} catch (EOFException f){
			for (Iterator<Map.Entry<Byte, Integer>> it = count.entrySet().iterator(); 
				     it.hasNext();)
				{
				    Map.Entry<Byte, Integer> entry = it.next();
				    Integer n = entry.getValue();
				    if(n != null) {
				    	System.out.println(n);
				    }
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Check message stats in terminal output
		System.out.println("messageLength:"+messageLength);
		System.out.println("abCounts.length:"+abCounts.length);
//		int sum=0;
		//Print counts to file
//		for (int i=0; i<abCounts.length; i++){
//			int temp=i/spaceSize;
//			sum+=abCounts[i];
//			out.write(abCounts[i]+"\n");
//		}
		//for (int i=0; i<abCounts.length; i++){
		//	if (abCounts[i]==0 	&& i!='/'  && i!=':'  && i!='~'  && i!='@'  && i!='{'  && i!='}'  && i!='['  && i!=']'  
		//						&& i!='%'  && i!='$'  && i!='\\' && i!=9 && i!=10 && i!=8 && i!=13 && i!=29  
		//						&& i!='+' && i!='<' && i!='>' && i!='^' && i!='_' && i!='`' && i!=175){
		//		out.write((char)(i)+" \n");
				
		//	}
//			if (abCounts[i]!=0){
//				System.out.println(i);
//				int temp=i/spaceSize;
//				System.out.print((char)(temp));
//				System.out.print((char)(i%spaceSize)+":");
//				System.out.println(abCounts[i]);
//				sum+=abCounts[i];
//				out.write(abCounts[i]+"\n");
//				out.write(i+":"+((char)(i%spaceSize))+ "\t"+abCounts[i]+"\n");
//			}
//			int temp=i/spaceSize;
//			sum+=abCounts[i];
//			out.write(abCounts[i]+"\n");
//			out.write(((char)(i%spaceSize))+ "\t"+abCounts[i] + "\n");
//		}
		
		//System.out.println("sum:"+sum);
		//out.close();
	}
}
