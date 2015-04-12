import java.util.LinkedList;


public class Schedule {
    // keep track of the trains that are currently moving, and checks for route conflicts
    // of new trains before adding them.  Once the train reaches its destination it is
    // removed and marked done.
    
    private LinkedList<Train> runningList;

	public Schedule() {
    	runningList = new LinkedList<Train>();
    }
    
    public boolean add(final Train t) {
    	
    	boolean addSuccessful = true;
    	
    	for (Train train : runningList) {
    		if (conflict(t, train)) {
    			addSuccessful = false;
    			break;
    		}
    	}
    	
    	runningList.add(t);
    	t.running();
    	return addSuccessful;
    }

    public boolean conflict(final Train trainOne, final Train trainTwo) {
    	LinkedList<Edge> routeOne = trainOne.getRoute();
    	LinkedList<Edge> routeTwo = trainTwo.getRoute();
    	
    	boolean conflict = false;

		for (Edge edgeOne : routeOne) {
			for (Edge edgeTwo : routeTwo) {
				if (edgeOne.equals(edgeTwo)) {
					conflict = true;
					System.out.println("conflit between train " + trainOne.getID() + " and " + trainTwo.getID() + " at " + edgeOne.toString());
				}
			}
		}
    	return conflict;
    }
    
    public void updateTrains(final int time) {

    	for (Train train : runningList) {
    		
    		train.move();
    		
    		if (train.getLastStation().equals(train.getDestName())) {
       			train.park();
    			train.setArrived(true);
    			train.setActualArrivalTime(time);
    			runningList.remove(train);
    			System.out.println( "train " + train.getID() + " has arrived at " + train.getActualArrivalTime() + " expected arrival was at " + train.getExpectedArrivalTime());
    			System.out.println("train " + train.getID() + " took route " + train.getRoute().toString() + "\r");
    		}
    	}    	
    }
    
    public LinkedList<Train> getRunningList() {
		return runningList;
	}

	public void setRunningList(LinkedList<Train> runningList) {
		this.runningList = runningList;
	}
}
