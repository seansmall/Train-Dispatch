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
    }
 
    public void update() {
    	
    	for (Train train : sequence) {
    		if ((!train.hasArrived()) && (train.getDepatureTime() <= ticks)) {
    			if (!train.isMoving()) {
    				if (runningTrains.add(train)) {
    					numTrainsRunning++;
    				}
    				
    			}
    		}
    	}
    	
    	runningTrains.updateTrains(ticks);
    	
    	ticks++;
    }
    
    public void generateRoutes() {
    	for (Train train : sequence) {
    		graph.dijkstra(train.getSource());
    		train.setRoute(graph.getPath(train.getDestination()));
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
    		delaySum += t.getActualArrivalTime() - t.getExpectedArrivalTime();
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
