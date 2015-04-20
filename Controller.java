import java.util.ArrayList;


public class Controller {

    private Graph graph;									// graph of the railway network
    protected ArrayList<Train> sequence;					// the sequence of trains for this simulation
    protected int ticks = 0;								// the current time
    private Schedule runningTrains;							// the train scheduler
    protected int numTrainsRunning = 0;						// the number of train currently running
    private boolean sequenceComplete = false;				// has the sequence been completed
    private int delaySum = 0;								// the total delay of all trains
    
    public Controller(final Graph g, final ArrayList<Train> sequence) {
        graph = g;
        this.sequence = sequence;
        runningTrains = new Schedule();
        generateRoutes();
    }
    
    public void update() {

        for (Train train : sequence) {
        	// check if the current train is valid for departure
            if ((!train.hasArrived()) && (train.getDepatureTime() <= ticks) && (!runningTrains.getRunningList().contains(train))) {
            	// try to add the train to the schedule
                if (runningTrains.add(train, ticks)) {
                	// if successful increment the running train count and set the actual departure time
                    System.out.println( "train " + train.getID() + " has departed at " + ticks + " expected depature time was at " + train.getDepatureTime() + "\r");
                    numTrainsRunning++;
                    runningTrains.setTrainsMoving(runningTrains.getTrainsMoving() + 1);
                    train.setActualDepartureTime(ticks);
                    //System.out.println(numTrainsRunning);
                }
            // the train has arrived remove it from the schedule and decrement the running train count
            } else if ((train.hasArrived()) && (runningTrains.getRunningList().contains(train))) {
                runningTrains.remove(train);
                numTrainsRunning--;
                //System.out.println(numTrainsRunning);
            }
            //System.out.println(runningTrains.getTrainsDelaying()  + " , " + runningTrains.getTrainsMoving());
            // re-calculate delays in case of dead lock
            if ((runningTrains.getRunningList().size() == runningTrains.getTrainsDelaying()) && (runningTrains.getRunningList().contains(train))) {
            	runningTrains.getRunningList().clear();
            	numTrainsRunning = 0;
            	runningTrains.setTrainsDelaying(0);
            	runningTrains.setTrainsMoving(0);
            	if (runningTrains.add(train, ticks)) {
            		 runningTrains.setTrainsMoving(runningTrains.getTrainsMoving() + 1);
            		 numTrainsRunning++;
            	}
            }
        }
        // update the schedule
        runningTrains.updateTrains(ticks);
        // increment time
        ticks++;
    }
    
    public void generateRoutes() {
    	// get all the routes for all trains in the sequence
        for (Train train : sequence) {
        	// find the shortest path from the soure station to all others
            graph.dijkstra(train.getSourceName());
            // get the path to the destination
            String path = graph.getPath(train.getDestName());
            //if (path.equals("No such vertex")) {
            //    path = graph.getPath(train.getDestName());
            //}
            // set the trains route
            train.setRoute(path);
        }
    }

    // check if all trains have reached their destinations
    public boolean sequenceComplete() {
        for (Train t : sequence) {
            if (!t.hasArrived()) {
                return false;
            }
        }
        // if  the sequence is complete calculate the total delay
        for (Train t : sequence) {
            delaySum += t.getDelay();
        }
        
        return true;
    }
    
    ///////////////    GET / SET methods    //////////////////
    
    public int getNumTrainsRunning() {
        return numTrainsRunning;
    }

    public void setNumTrainsRunning(int numTrainsRunning) {
        this.numTrainsRunning = numTrainsRunning;
    }

    public boolean isSequenceComplete() {
        return sequenceComplete;
    }

    public void setSequenceComplete(boolean sequenceComplete) {
        this.sequenceComplete = sequenceComplete;
    }

    public int getDelaySum() {
        return delaySum;
    }

    public void setDelaySum(int delaySum) {
        this.delaySum = delaySum;
    }
}
