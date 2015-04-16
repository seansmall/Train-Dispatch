import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;


public class SequenceGenerator {
	
	private static final Random RNG =
            new Random (Long.getLong ("seed", System.nanoTime()));
	
	public static final int LENGTH = 100; // length of sequence (number of trains)
	public static final int DAY = 720; // arbitrary time
	
	private static final String SOURCE = String.format("%-20.20s", "Source:");
	private static final String DESTINATION = String.format("%-20.20s", "Destination:");
	private static final String DEP_TIME = String.format("%-20.20s", "Dep. Time:");
	private static final String HEADER = SOURCE + DESTINATION + DEP_TIME;
	private static final String UNDERLINE = "——————————————————————————————————————————————————";
	
	public static final String[] AMTRAK_STATIONS = {"Miami", "West Palm Beach", "Orlando", "Jacksonville", "Tallahassee",
		"Pensacola", "Mobile", "New Orleans", "Lafayette", "Houston", "San Antonio", "El Paso", "Tucson",
		"Maricopa", "Palm Springs", "Los Angeles", "Santa Barbara", "San Luis Obispo", "San Jose", "Oakland",
		"Stockton", "Fresno", "Sacramento", "Redding", "Eugene", "Portland", "Seattle", "Everett", "Spokane",
		"Minneapolis", "Milwaukee", "Chicago", "Galesburg", "Omaha", "Denver", "Salt Lake City", "Kansas City",
		"Topeka", "Albuquerque", "Flagstaff", "St. Louis", "Little Rock", "Texarkana", "Dallas", "Fort Worth",
		"Memphis", "Battle Creek", "Detroit", "Indianapolis", "Cincinnati", "Cleveland", "Pittsburgh", "Harrisburg",
		"Buffalo", "Niagara Falls", "Rochester", "Syracuse", "Albany", "St. Albans", "Springfield", "Boston", "New Haven",
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
	
	static class trainSequence implements Comparable<Object>{
		private String source;
		private String destination;
		private int depatureTime;

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
		    this.source = source;
		}

		public String getDestination() {
		    return destination;
		}

		public void setDestination(String destination) {
		    this.destination = destination;
		}

		public int getDepatureTime() {
		    return depatureTime;
		}

		public void setDepatureTime(int depatureTime) {
			this.depatureTime = depatureTime;
		}

		public int compareTo(Object anotherTrain) throws ClassCastException {
			if (!(anotherTrain instanceof trainSequence))
				throw new ClassCastException("A trainSequence object expected.");
			int anotherTrainDepTime = ((trainSequence) anotherTrain).getDepatureTime();  
			return this.depatureTime - anotherTrainDepTime;    
		}
	}
	
	public static void createAmtrakSequence(trainSequence[] sequence) {

	  	for (int i = 0; i < LENGTH; i++) {
	  		sequence[i] = new trainSequence();
	  		sequence[i].setSource(AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)]);
		  	sequence[i].setDestination(AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)]);
		  	sequence[i].setDepatureTime(1 + RNG.nextInt(DAY));
			while (sequence[i].getSource().equals(sequence[i].getDestination())) {
				sequence[i].setDestination(AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)]);
			}
		}
	}
	
	public static void createEuropeSequence(trainSequence[] sequence) {

	  	for (int i = 0; i < LENGTH; i++) {
	  		sequence[i] = new trainSequence();
	  		sequence[i].setSource(EUROPE_STATIONS[RNG.nextInt(EUROPE_STATIONS.length)]);
		  	sequence[i].setDestination(EUROPE_STATIONS[RNG.nextInt(EUROPE_STATIONS.length)]);
		  	sequence[i].setDepatureTime(1 + RNG.nextInt(DAY));
			while (sequence[i].getSource().equals(sequence[i].getDestination())) {
				sequence[i].setDestination(EUROPE_STATIONS[RNG.nextInt(EUROPE_STATIONS.length)]);
			}
		}
	}
	
	public static void printTrainSequence (trainSequence[] sequence) {
			
			System.out.println(HEADER);
			System.out.println(UNDERLINE);
			
			for (int i = 0; i < sequence.length; i++) {
				String s = String.format("%-20.20s", sequence[i].getSource() + ",");
				String d = String.format("%-20.20s", sequence[i].getDestination() + ",");
				String dt = String.format("%10.20s,\n", sequence[i].getDepatureTime());
				System.out.print(s + d + dt);
			}
			System.out.print("\n\n");
		}
	
	public static void saveToTxt (trainSequence[] sequence,
			String fileName) throws FileNotFoundException,
			UnsupportedEncodingException {		
		
		// checks if filename already exists
		// and adds an index if it does
		File f = new File(fileName + ".txt");
		String newName;
		if(f.exists() && !f.isDirectory()) {
			int count = 1;
			do {
				newName = fileName + "(" + count + ")";
				f = new File(newName + ".txt");
				count++;
			} while (f.exists() && !f.isDirectory());
		}
		// saves file to directory
		PrintWriter writer = new PrintWriter(f, "UTF-8");
		writer.println(HEADER);
		writer.println(UNDERLINE);
		
		for (int i = 0; i < sequence.length; i++) {
			String s = String.format("%-20.20s", sequence[i].getSource() + ",");
			String d = String.format("%-20.20s", sequence[i].getDestination() + ",");
			String dt = String.format("%10.20s,\n", sequence[i].getDepatureTime());
			writer.println(s + d + dt);
		}
		writer.close();
		// prints to console
		System.out.println("Filename: " + f);
	}
	
	public static void main (String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		trainSequence[] list = new trainSequence[LENGTH];
		createAmtrakSequence(list);
	    Arrays.sort(list);
	    printTrainSequence(list);
	    saveToTxt(list, "sequence");
	}
}
