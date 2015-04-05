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
    	
    	for (Train train : runningList) {
    		if (conflict(t, train)) {
    			return false;
    		}
    	}
    	
    	runningList.add(t);
    	return true;
    }
    
    public boolean conflict(final Train trainOne, final Train trainTwo) {
    	int[][] routeOne = trainOne.getRoute();
    	int[][] routeTwo = trainTwo.getRoute();
    	
    	if (routeOne.length != routeTwo.length) {
    		return false;
    	}
    	
    	for (int i = 0; i < routeOne.length; i++) {
    		if ((routeOne[i][0] == routeTwo[i][0]) && (routeOne[i][1] == routeTwo[i][1])) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public void updateTrains(final int time) {

    	for (Train train : runningList) {
    		
    		train.move();
    		
    		final int[][] route = train.getRoute();
    		
    		for (int i = 0 ; i < route.length; i++) {
    			if (train.getDistanceTraveled() == route[i][1]) {
    				train.setCurrentStation(route[i][0]);
    			}
    		}
    	}
    	
    	checkArrivals(time);
    }
    
    public void checkArrivals(final int time) {
    	for (Train train : runningList) {
    		if (train.getDestination() == train.getCurrentStation()) {
    			train.park();
    			train.setArrived(true);
    			train.setActualArrivalTime(time);
    			runningList.remove(train);
    		}
    	}
    }
}
