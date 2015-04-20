import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Simulator {
    
    public static void main (final String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        
    	int numTrains = 50;
    	int ticks = 720;
    	int numTrials = 1000;
    	int numIncrements = 5;
    	int maps = 2;
    	String map;    	
    	
    	for (int k = 0; k < maps; k++) {
    		
    		numTrains = 50;
    		
    		
    		if (k == 0) {
    			map = "amtrak";
    		} else {
    			map = "europe";
    		}
    		
        	for (int i = 0; i < numIncrements; i++) {
        		
        		String baseData = "base data " + numTrains + " " + map +".csv";
        		String testData = "test data " + numTrains + " " + map +".csv";
        		
        		PrintWriter baseWriter = new PrintWriter(baseData, "UTF-8");
        		PrintWriter testWriter = new PrintWriter(testData, "UTF-8");
        		
        		sequenceFactory(map, numTrains, ticks, 50, 40, 10, numTrials);
        		
        		for (int j = 0; j < numTrials; j++) {
        			
        			String fileName = map + " " +  numTrains + " " + ticks + " ticks " + " sequence " + j + ".txt";
        			
        			Graph graph;
        			
        			if (k == 0) {
        				graph = new Graph("Amtrak System Data.txt");
        			} else {
        				graph = new Graph("Europe Map Data.txt");
        			}
        			
        	        File sequenceName = new File(fileName);
        	        ArrayList<Train> baseSequence = readSequence(sequenceName);
        			ArrayList<Train> testSequence = readSequence(sequenceName);

        	        Controller base = new Controller(graph, baseSequence);
        	        SmartController test = new SmartController(graph, testSequence);
        	        
        	        System.out.println("Base Case\r");
        	        while (!base.sequenceComplete()) {
        	           base.update();
        	        }
        	        System.out.println("\rTest Case\r");
        	        while (!test.sequenceComplete()) {
        	        	test.update();
        	        }
        			
        	        System.out.println("Base Case Delay: " + base.getDelaySum());
        	        System.out.println("Test Case Delay: " + test.getDelaySum());
        	        System.out.println("done\r\r");
        	        
        	        baseWriter.println(base.getDelaySum() + ",");
        	        testWriter.println(test.getDelaySum() + ",");
        	        // end this set of trials
        		}
            	baseWriter.close();
            	testWriter.close();
        		// increase number of trains and run again
        		numTrains += 50;
        	}
        	// change map
    	}
    	

    }
    
    public static ArrayList<Train> readSequence(final File fileName) {
        
        ArrayList<Train> seq = new ArrayList<Train>();
        
        try {
            final Scanner SC = new Scanner(fileName);
            SC.useDelimiter(",\\s*");
            SC.nextLine();
            SC.nextLine();
            
            int id = 0;
            while (SC.hasNext()) {
                String source = SC.next();
                String dest = SC.next();
                int time = SC.nextInt();
                int type = SC.nextInt();
                
                seq.add(new Train(source, dest, time, id, type));
                id++;
            }
            
            SC.close();
            return seq;
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        return seq;
    }

    public static void saveTrainInfo (ArrayList<Train> sequence,
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
        
        for (Train train : sequence) {
            String s = String.format("%-5.5s", train.getID() + ",");
            String d = String.format("%-1.3s", train.getSpeed() + ",");
            String dt = String.format("%7.7s,", train.getActualDepartureTime());
            writer.print(s + d + dt);
            writer.println(train.getRoute());
        }
        writer.close();
    }

    public static void sequenceFactory(final String map, final int numTrains, final int ticksPerDay, final int percentCargo,
    		final int percentPassenger, final int percentPriority, final int numSequences) throws FileNotFoundException, UnsupportedEncodingException {
    	
		SequenceGenerator seqGen = new SequenceGenerator(numTrains, ticksPerDay, percentCargo, percentPassenger, percentPriority);
    	
    	for (int i = 0; i < numSequences; i++) {

    		trainSequence[] list = new trainSequence[numTrains];
    		
    		String fileName = map + " " +  numTrains + " " + ticksPerDay + " ticks " + " sequence " + i;
        	
        	if (map.equals("europe")) {
        		seqGen.createEuropeSequence(list);
        	    Arrays.sort(list);
        	    seqGen.saveToTxt(list, fileName);
        	} else if (map.equals("amtrak")) {
        		seqGen.createAmtrakSequence(list);
        	    Arrays.sort(list);
        	    seqGen.saveToTxt(list, fileName);
        	}
    	}
    	
    }
}
