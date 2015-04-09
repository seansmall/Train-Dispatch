import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {
	
	public static void main (final String[] args) throws FileNotFoundException {
		
		Graph graph = new Graph("Amtrak System Data.txt");
		File sequenceName = new File("test.txt");
		ArrayList<Train> sequence = readSequence(sequenceName);

		BaseCase base = new BaseCase(graph, sequence);
		
		//while (!base.sequenceComplete()) {
		//	base.update();
		//}
		
		//System.out.println(base.getDelaySum());
		
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
	    	    
	    		seq.add(new Train(source, dest, time, id));
	    		id++;
	    	}
	    	
	    	SC.close();
	    	return seq;
    	} catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    	return seq;
    }
}
