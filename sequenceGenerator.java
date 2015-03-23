import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;


public class sequenceGenerator {
	
	private static final Random RNG =
            new Random (Long.getLong ("seed", System.nanoTime()));
	
	public static final int LENGTH = 10; // length of sequence
	public static final int GRID_SIZE = 100; // size of the grid
	public static final int DAY = 72000; // arbitrary time
	
	static class Sequence {
		int source;
		int destination;
		int depatureTime;
		
		private Sequence(final int s, final int d, final int dt) {
			 this.source = s;
			 this.destination = d;
			 this.depatureTime = dt;
	     }
	}
	
	public static ArrayList<Sequence> createSequence (ArrayList<Sequence> sequence) {
		
		for (int i = 0; i < LENGTH; i++) {
			int s, d, dt;
			s = RNG.nextInt(GRID_SIZE + 1);
			d = RNG.nextInt(GRID_SIZE + 1);
			dt = 1 + RNG.nextInt(DAY);
			
			Sequence element = new Sequence(s, d, dt);
			sequence.add(element);
		}
		return sequence;
	}
	
	public static void saveToTxt (ArrayList<Sequence> sequence,
			String fileName, String dirName) throws FileNotFoundException,
			UnsupportedEncodingException {
		
		// checks if directory name already exists
		if (!new File(dirName).exists()) {
			new File(dirName).mkdir();
		}
		  
	      File f = new File(dirName + File.separator + fileName + ".txt");

	      if(!f.exists() && !f.isDirectory()) {
	    	// saves file to directory
		      PrintWriter writer = new PrintWriter(f, "UTF-8");
				writer.println("Sour. \t Dest. \t Dep. Time");
				
				for (int i = 0; i < sequence.size(); i++) {
					int s = sequence.get(i).source;
					int d = sequence.get(i).destination;
					int dt = sequence.get(i).depatureTime;
					writer.println(s + "," + d + "," + dt + ",");
				}
				writer.close();
	      }
	}
	
	public static void main (String[] args) {
		
		ArrayList<Sequence> testList = new ArrayList<>();
		createSequence(testList);
		
		System.out.println("Sour. \t Dest. \t Dep. Time");
		
		for (int i = 0; i < testList.size(); i++) {
			System.out.print(testList.get(i).source + "\t");
			System.out.print(testList.get(i).destination + "\t");
			System.out.println(testList.get(i).depatureTime);
		}
		
	}
}
