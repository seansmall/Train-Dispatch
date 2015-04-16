import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {
    
    public static void main (final String[] args) throws FileNotFoundException {
        
        Graph graph = new Graph("Amtrak System Data.txt");
        File sequenceName = new File("sequence.txt");
        ArrayList<Train> baseSequence = readSequence(sequenceName);
		ArrayList<Train> testSequence = readSequence(sequenceName);

        Controller base = new Controller(graph, baseSequence);
        SmartController test = new SmartController(graph, testSequence);
        
        System.out.println("Base Case\r");
        while (!base.sequenceComplete()) {
           base.updateTrainsRunning();
           base.update();
        }
        System.out.println("\rTest Case\r");
        while (!test.sequenceComplete()) {
            test.updateTrainsRunning();
        	test.update();
        }
        
        System.out.println("Base Case Delay: " + base.getDelaySum());
        System.out.println("Test Case Delay: " + test.getDelaySum());
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
