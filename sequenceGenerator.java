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
	public static final int DAY = 72000; // arbitrary time
	
	public static final String[] AMTRAK_STATIONS = {"Miami", "West Palm Beach", "Orlando", "Jacksonville", "Tallahassee",
		"Pensacola", "Mobile", "New Orleans", "Lafayette", "Houston", "San Antonio", "El Paso", "Tucson",
		"Maricopa", "Palm Springs", "Los Angeles", "Santa Barbara", "San Luis Obispo", "San Jose", "Oakland",
		"Stockton", "Fresno", "Sacramento", "Redding", "Eugene", "Portland", "Seattle", "Everett", "Spokane",
		"Minneapolis", "Milwakue", "Chicago", "Galesburg", "Omaha", "Denver", "Salt Lake City", "Kansas City",
		"Topeka", "Albuquerque", "Flagstaff", "St. Louis", "Little Rock", "Texarkana", "Dallas", "Fort Worth",
		"Memphis", "Battle Creek", "Detroit", "Indianapolis", "Cincinnati", "Cleveland", "Pittsburgh", "Harrisburg",
		"Buffalo", "Niagra Falls", "Rochester", "Syracuse", "Albany", "St. Albans", "Springfield", "Boston", "New Haven",
		"New York", "Trenton", "Philadelphia", "Baltimore", "Washington", "Charlottesville", "Lynchburg", "Greensboro",
		"Raleigh", "Columbia", "Selma", "Fayetteville", "Charleston", "Richmond", "Charlotte", "Atlanta", "Birmingham",
		"Tampa", "Savannah", "Tuscaloosa", "Jackson", "Austin", "San Diego", "San Bernardino", "Bakersfield", "Vancouver",
		"Milwaukee", "Quincy", "Oklahoma City", "Grand Rapids", "Port Huron", "Pontiac", "Toronto", "Montreal", "Brunswick",
		"Newport News", "Norfolk"};
	
	public static final String[] EUROPE_STATIONS = {"Aix-en-Provence", "Amsterdam", "Angers", "Angoulême", "Antwerpen", "Avignon",
			"Basel", "Berlin", "Bern", "Bordeaux", "Brussels", "Chambéry", "Dijon", "Dortmund", "Duisberg" ,"Ebbsfleet Int.",
			"Essen", "Frankfurt airport", "Frankfurt", "Fulda", "Genève", "Hamburg", "Hannover", "Innsbruck", "Interlake",
			"Karlsruhe", "Köln", "Lausanne", "Le Mans", "Leipzig", "Lille", "Linz", "Liège", "London", "Luxembourg City",
			"Lyon", "Mannheim", "Marne-La-Vallée", "Marseille", "Massy", "Metz", "Milano", "Montpellier", "Mulhouse", "München",
			"Nancy", "Nantes", "Nimes", "Nürnberg", "Paris", "Perpignan", "Poitiers", "Reims", "Rennes", "Rotterdam", "Saltzburg",
			"Schipol airport", "Strasbourg", "Stuttgart", "Torino", "Tours", "Valence", "Wien", "Würzburg", "Zürich"};
	
	
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
	
	public static ArrayList<Sequence> createAmtrakSequence (ArrayList<Sequence> sequence) {
		
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
	
	public static ArrayList<Sequence> createEuropeSequence (ArrayList<Sequence> sequence) {
		
		for (int i = 0; i < LENGTH; i++) {
			String s, d;
			int dt;
			s = EUROPE_STATIONS[RNG.nextInt(EUROPE_STATIONS.length)];
			d = EUROPE_STATIONS[RNG.nextInt(EUROPE_STATIONS.length)];
			dt = 1 + RNG.nextInt(DAY);
			
			Sequence element = new Sequence(s, d, dt);
			sequence.add(element);
		}
		return sequence;
	}
	
	public static ArrayList<Sequence> sortSequence (ArrayList<Sequence> sequence) {
			
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
		
		final String source = String.format("%-20.17s", "Source:");
		final String destination = String.format("%-20.17s", "Destination:");
		final String depTime = String.format("%-20.17s", "Dep. Time:");
		final String header = source + destination + depTime;
		final String underline = "——————————————————————————————————————————————————";
				
		// checks if directory name already exists
		if (!new File(dirName).exists()) {
			new File(dirName).mkdir();
		}
		  
	      File f = new File(dirName + File.separator + fileName + ".txt");

	      if(!f.exists() && !f.isDirectory()) {
	    	// saves file to directory
		      PrintWriter writer = new PrintWriter(f, "UTF-8");
				writer.println(header);
				writer.println(underline);
				
				for (int i = 0; i < sequence.size(); i++) {
					String s = String.format("%-20.17s", sequence.get(i).source + ",");
					String d = String.format("%-20.17s", sequence.get(i).destination + ",");
					String dt = String.format("%10.17s,\n", sequence.get(i).depatureTime);
					writer.println(s + d + dt);
				}
				writer.close();
	      }
	}
	
	public static void printSequence (ArrayList<Sequence> testList) {
		final String source = String.format("%-20.17s", "Source:");
		final String destination = String.format("%-20.17s", "Destination:");
		final String depTime = String.format("%-20.17s", "Dep. Time:");
		final String header = source + destination + depTime;
		final String underline = "——————————————————————————————————————————————————";
		System.out.println(header);
		System.out.println(underline);
		
		for (int i = 0; i < testList.size(); i++) {
			String s = String.format("%-20.17s", testList.get(i).source + ",");
			String d = String.format("%-20.17s", testList.get(i).destination + ",");
			String dt = String.format("%10.17s,\n", testList.get(i).depatureTime);
			System.out.print(s + d + dt);
		}
		System.out.print("\n\n");
	}
	
	public static void main (String[] args) {
		
		ArrayList<Sequence> testList = new ArrayList<>();
		createEuropeSequence(testList);
		sortSequence(testList);
		printSequence(testList);
		
//		try {
//			saveToTxt(testList, "test", "test");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
