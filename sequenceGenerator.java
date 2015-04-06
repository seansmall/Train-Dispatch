import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;


public class SequenceGenerator {
	
	private static final Random RNG =
            new Random (Long.getLong ("seed", System.nanoTime()));
	
	public static final int LENGTH = 10; // length of sequence
	public static final int GRID_SIZE = 100; // size of the grid
	public static final int DAY = 72000; // arbitrary time
	public static final String[] AMTRAK_STATIONS = {"Miami", "West Palm Beach", "Orlando", "Jacksonville", "Tallahassee",
		"Pensacola", "Mobile", "New Orleans", "Lafayette", "Houston", "San Antonio", "El Paso", "Tucson",
		"Maricopa", "Palm Springs", "Los Angeles", "Santa Barbara", "San Luis Obispo", "San Jose", "Oakland",
		"Stockton", "Fresno", "Sacramento", "Redding", "Eugene", "Portland", "Seattle", "Everett", "Spokane",
		"Minneapolis", "Milwakue", "Chicago", "Galesburg", "Omaha", "Denver", "Salt Lake City", "Kansas City",
		"Topeka", "Albuquerque", "Flagstaff", "St. Louis", "Little Rock", "Texarkana", "Dallas", "Fort Worth",
		"Memphis", "Battle Creek", "Detroit", "Indianapolis", "Cincinnati", "Cleveland", "Pittsburgh", "Harrisburg",
		"Buffalo", "Niagra Falls", "Rochester", "Syracuse", "Albany", "St. Albans", "Springfield", "Boston", "New Haven",
		"New York", "Trenton", "Philadelphia", "Baltimore", "Washington, DC", "Charlottesville", "Lynchburg", "Greensboro",
		"Raleigh", "Columbia, SC", "Selma", "Fayetteville", "Charleston", "Richmond", "Charlotte", "Atlanta", "Birmingham",
		"Tampa", "Savannah", "Tuscaloosa", "Jackson", "Austin", "San Diego", "San Bernardino", "Bakersfield", "Vancouver",
		"Milwaukee", "Quincy", "Oklahoma City", "Grand Rapids", "Port Huron", "Pontiac", "Toronto", "Montreal", "Brunswick",
		"Newport News", "Norfolk"};
	
	
	static class Sequence {
		String source;
		String destination;
		int depatureTime;
		
		private Sequence(final String s, final String d, final int dt) {
			 this.source = s;
			 this.destination = d;
			 this.depatureTime = dt;
	     }
	}
	
	public static ArrayList<Sequence> createSequence (ArrayList<Sequence> sequence) {
		
		for (int i = 0; i < LENGTH; i++) {
			String s, d;
			int dt;
			s = AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)];
			d = AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)];
			dt = 1 + RNG.nextInt(DAY);
			
			Sequence element = new Sequence(s, d, dt);
			sequence.add(element);
		}
		return sequence;
	}
	
	public static ArrayList<Sequence> sortSequence(ArrayList<Sequence> sequence) {
			
			// sorts the sequence
		      for (int i = 0; i < sequence.size(); i++) {
		            int min = i;
		            for (int j = i + 1; j < sequence.size(); j++) {
		                if (sequence.get(j).depatureTime < sequence.get(min).depatureTime) {
		                    min = j;
		                }
		            }
		            if (min != i) {
		            	final Sequence temp = sequence.get(i);
		                sequence.set(i, sequence.get(min));
		                sequence.set(min, temp);
		            }
		        }
			return sequence;
	}
	
	public static void saveToTxt (ArrayList<Sequence> sequence,
			String fileName, String dirName) throws FileNotFoundException,
			UnsupportedEncodingException {
		
		//TODO: Make output look nicer
		
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
					String s = sequence.get(i).source;
					String d = sequence.get(i).destination;
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
		sortSequence(testList);
		System.out.println();
		System.out.println("Sour. \t Dest. \t Dep. Time");
		
		for (int i = 0; i < testList.size(); i++) {
			System.out.print(testList.get(i).source + "\t");
			System.out.print(testList.get(i).destination + "\t");
			System.out.println(testList.get(i).depatureTime);
		}
		
		
	}
}
