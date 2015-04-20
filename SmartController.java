import java.util.ArrayList;


public class SmartController extends Controller {

	private SmartSchedule runningTrains;						// smart schedule of running trains
	
	public SmartController(Graph g, ArrayList<Train> sequence) {
		super(g, sequence);
		this.runningTrains = new SmartSchedule();
	}
	
	@Override
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
                    System.out.println(numTrainsRunning);
                }
            // the train has arrived remove it from the schedule and decrement the running train count
            } else if (train.hasArrived() && (runningTrains.getRunningList().contains(train))) {
                runningTrains.remove(train);
                numTrainsRunning--;
                //System.out.println(numTrainsRunning);
            }
           // System.out.println(runningTrains.getTrainsDelaying()  + " , " + runningTrains.getTrainsMoving());
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
}
