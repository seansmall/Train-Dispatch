import java.util.ArrayList;


public class SmartController extends Controller {

	private SmartSchedule runningTrains;
	
	public SmartController(Graph g, ArrayList<Train> sequence) {
		super(g, sequence);
		this.runningTrains = new SmartSchedule();
	}
	
	@Override
    public void update() {
        
        for (Train train : sequence) {
            if ((!train.hasArrived()) && (train.getDepatureTime() <= ticks) && (!train.isMoving()) && (!runningTrains.getRunningList().contains(train))) {
                if (runningTrains.add(train, ticks)) {
                    //TODO fix departure time with a delay
                    System.out.println( "train " + train.getID() + " has departed at " + ticks + " expected depature time was at " + train.getDepatureTime() + "\r");
                    numTrainsRunning++;
                    train.setActualDepartureTime(ticks);
                }
            }
            // if the train reaches the delay station then it should stop moving until after the delay time
            // has passed
            if ((!train.hasArrived()) && (runningTrains.getRunningList().contains(train))) {
                if ((train.getDelayStop().equals(train.getLastStation())) && (train.getDelayTime() > ticks)) {
                    train.park();
                    //System.out.println(" at " + ticks + " train " + train.getID() + " will delay at " + train.getDelayStop() + " until " + train.getDelayTime());
                } else if ((train.getDelayStop().equals(train.getLastStation())) && (train.getDelayTime() < ticks)) {
                    train.running();
                    //System.out.println(" at " + ticks + " train " + train.getID() + " resumed running from " + train.getDelayStop());
                }
            }
        }
        runningTrains.updateTrains(ticks);
        ticks++;
    }

	@Override
    public void updateTrainsRunning() {
        for (Train train : sequence) {
            if ((!train.hasArrived()) && (train.getDepatureTime() <= ticks) && (!train.isMoving())) {
                if (runningTrains.add(train)) {
                    System.out.println( "train " + train.getID() + " has departed at " + ticks + " expected depature time was at " + train.getDepatureTime() + "\r");
                    numTrainsRunning++;
                    train.setActualDepartureTime(ticks);
                }
            } else if (train.hasArrived()) {
                runningTrains.remove(train);
                numTrainsRunning--;
            }
        }
    }
}
