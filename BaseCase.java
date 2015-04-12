import java.util.ArrayList;


public class BaseCase {

    private Graph graph;
    private ArrayList<Train> sequence;
    private int ticks = 0;
    private Schedule runningTrains;
    private int numTrainsRunning = 0;
    private boolean sequenceComplete = false;
    private int delaySum = 0;
    
    public BaseCase(final Graph g, final ArrayList<Train> sequence) {
    	graph = g;
    	this.sequence = sequence;
    	runningTrains = new Schedule();
    	generateRoutes();
    }
 
    public void update() {
    	
    	for (Train train : sequence) {
    		if ((!train.hasArrived()) && (train.getDepatureTime() <= ticks) && (!train.isMoving())) {
    			if (runningTrains.add(train)) {
    				System.out.println( "train " + train.getID() + " has departed at " + ticks + " expected depature time was at " + train.getDepatureTime() + "\r");
    				numTrainsRunning++;
    			}
    		}
    	}
    	
    	runningTrains.updateTrains(ticks);
    	ticks++;
    }
    
    public void generateRoutes() {
    	for (Train train : sequence) {
    		graph.dijkstra(train.getSourceName());
    		train.setRoute(graph.getPath(train.getDestName()));
    	}
    }

	public int getNumTrainsRunning() {
		return numTrainsRunning;
	}

	public void setNumTrainsRunning(int numTrainsRunning) {
		this.numTrainsRunning = numTrainsRunning;
	}
    
    public boolean sequenceComplete() {
    	for (Train t : sequence) {
    		if (!t.hasArrived()) {
    			return false;
    		}
    	}
    	
    	for (Train t : sequence) {
    		delaySum += t.getDelay();
    	}
    	
    	return true;
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
