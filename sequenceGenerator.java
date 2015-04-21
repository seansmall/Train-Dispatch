import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;


public class SequenceGenerator {
	
	private static final Random RNG =
            new Random (Long.getLong ("seed", System.nanoTime()));
	
	public final int length; // length of sequence (number of trains)
	public final int day;    // arbitrary time
	
	public final int passengerCheck;
	public final int cargoCheck;
	public final int priorityCheck;
	public static final int percent = 100;
	
	private static final String SOURCE = String.format("%-20.20s", "Source:");
	private static final String DESTINATION = String.format("%-20.20s", "Destination:");
	private static final String DEP_TIME = String.format("%-20.20s", "Dep. Time:");
	private static final String TYPE = String.format("%-20.20s", "Type:");
	private static final String HEADER = SOURCE + DESTINATION + DEP_TIME + TYPE;
	private static final String UNDERLINE = "——————————————————————————————————————————————————————————————";
	
	public static final String[] AMTRAK_STATIONS = {"Miami", "West Palm Beach", "Orlando", "Jacksonville", "Tallahassee",
		"Pensacola", "Mobile", "New Orleans", "Lafayette", "Houston", "San Antonio", "El Paso", "Tucson",
		"Maricopa", "Palm Springs", "Los Angeles", "Santa Barbara", "San Luis Obispo", "San Jose", "Oakland",
		"Stockton", "Fresno", "Sacramento", "Redding", "Eugene", "Portland OR", "Portland", "Seattle", "Everett", "Spokane",
		"Minneapolis", "Milwaukee", "Chicago", "Galesburg", "Omaha", "Denver", "Salt Lake City", "Kansas City",
		"Topeka", "Albuquerque", "Flagstaff", "St. Louis", "Little Rock", "Texarkana", "Dallas", "Fort Worth",
		"Memphis", "Battle Creek", "Detroit", "Indianapolis", "Cincinnati", "Cleveland", "Pittsburgh", "Harrisburg",
		"Buffalo", "Niagara Falls", "Rochester", "Syracuse", "Albany", "St. Albans", "Springfield", "Boston", "New Haven",
		"New York", "Trenton", "Philadelphia", "Baltimore", "Washington", "Charlottesville", "Lynchburg", "Greensboro",
		"Raleigh", "Columbia", "Selma", "Fayetteville", "Charleston", "Richmond", "Charlotte", "Atlanta", "Birmingham",
		"Tampa", "Savannah", "Tuscaloosa", "Jackson", "Austin", "San Diego", "San Bernardino", "Bakersfield", "Vancouver",
		"Quincy", "Oklahoma City", "Grand Rapids", "Port Huron", "Pontiac", "Toronto", "Montreal", "Brunswick",
		"Newport News", "Norfolk"};
	
	public static final String[] EUROPE_STATIONS = {"Aix-en-Provence", "Amsterdam", "Angers", "Angouleme", "Antwerpen", "Avignon",
			"Basel", "Berlin", "Bern", "Bordeaux", "Brussels", "Chambery", "Dijon", "Dortmund", "Duisberg" ,"Ebbsfleet Int.",
			"Essen", "Frankfurt airport", "Frankfurt", "Fulda", "Geneve", "Hamburg", "Hannover", "Innsbruck", "Interlake",
			"Karlsruhe", "Koln", "Lausanne", "Le Mans", "Leipzig", "Lille", "Linz", "Liege", "London", "Luxembourg City",
			"Lyon", "Mannheim", "Marne-La-Vallee", "Marseille", "Massy", "Metz", "Milano", "Montpellier", "Mulhouse", "Munchen",
			"Nancy", "Nantes", "Nimes", "Nurnberg", "Paris", "Perpignan", "Poitiers", "Reims", "Rennes", "Rotterdam", "Saltzburg",
			"Schipol airport", "Strasbourg", "Stuttgart", "Torino", "Tours", "Valence", "Wien", "Wurzburg", "Zurich"};
	
	
	public SequenceGenerator(final int trains, final int ticks, final int cargo, final int passenger, final int priority) {
		this.length = trains;
		this.priorityCheck = priority;
		this.passengerCheck = passenger + this.priorityCheck;
		this.cargoCheck = this.passengerCheck + cargo;
		this.day = ticks;
	}
	
	static class Sequence {
		String source;
		String destination;
		int depatureTime;
		int type;
		
		private Sequence(final String s, final String d, final int dt, final int type ) {
			 this.source = s;
			 this.destination = d;
			 this.depatureTime = dt;
			 this.type = type;
	     }
	}

	public void createAmtrakSequence(trainSequence[] sequence) {

	  	for (int i = 0; i < length; i++) {
	  		sequence[i] = new trainSequence();
	  		sequence[i].setSource(AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)]);
		  	sequence[i].setDestination(AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)]);
		  	sequence[i].setDepatureTime(1 + RNG.nextInt(day));
		  	
		  	int type = RNG.nextInt(percent);
		  	
		  	if (type <= priorityCheck) {
		  		type = 2;
		  	} else if (type <= passengerCheck) {
		  		type = 1;
		  	} else {
		  		type = 0;
		  	}
		  	sequence[i].setType(type);
		  	
			while (sequence[i].getSource().equals(sequence[i].getDestination())) {
				sequence[i].setDestination(AMTRAK_STATIONS[RNG.nextInt(AMTRAK_STATIONS.length)]);
			}
		}
	}
	
	public void createEuropeSequence(trainSequence[] sequence) {

	  	for (int i = 0; i < length; i++) {
	  		sequence[i] = new trainSequence();
	  		sequence[i].setSource(EUROPE_STATIONS[RNG.nextInt(EUROPE_STATIONS.length)]);
		  	sequence[i].setDestination(EUROPE_STATIONS[RNG.nextInt(EUROPE_STATIONS.length)]);
		  	sequence[i].setDepatureTime(1 + RNG.nextInt(day));
		  	
		  	int type = RNG.nextInt(percent);
		  	
		  	if (type <= priorityCheck) {
		  		type = 2;
		  	} else if (type <= passengerCheck) {
		  		type = 1;
		  	} else {
		  		type = 0;
		  	}
		  	sequence[i].setType(type);
		  	
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
				String dt = String.format("%10.20s", sequence[i].getDepatureTime() + ",");
				String t = String.format("%10.20s,\n", sequence[i].getType());
				System.out.print(s + d + dt + t);
			}
			System.out.print("\n\n");
		}
	
	public void saveToTxt (trainSequence[] sequence,
			String fileName) throws FileNotFoundException, UnsupportedEncodingException {		
		
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
			String dt = String.format("%10.20s", sequence[i].getDepatureTime() + ",");
			String t = String.format("%10.20s,\n", sequence[i].getType());
			writer.println(s + d + dt + t);
		}
		writer.close();
		// prints to console
		System.out.println("Filename: " + f);
	}
	
	public static void main (String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		SequenceGenerator seqGen = new SequenceGenerator(100, 720, 50, 40, 10);
		
		trainSequence[] list = new trainSequence[100];
		seqGen.createAmtrakSequence(list);
	    Arrays.sort(list);
	    printTrainSequence(list);
	    seqGen.saveToTxt(list, "amtrak");
	    
	    seqGen.createEuropeSequence(list);
	    Arrays.sort(list);
	    printTrainSequence(list);
	    seqGen.saveToTxt(list, "europe");
	}
}
