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
    	LinkedList<Station> routeOne = trainOne.getRoute();
    	LinkedList<Station> routeTwo = trainTwo.getRoute();
    	
    	if (routeOne.size() != routeTwo.size()) {
    		return false;
    	}
    	
    	for (int i = 0; i < routeOne.size(); i++) {
    		if ((routeOne.get(i).getName().equals(routeTwo.get(i).getName()) &&
    		        (routeOne.get(i).getDistance() == routeTwo.get(i).getDistance()))) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public void updateTrains(final int time) {

    	for (Train train : runningList) {
    		
    		train.move();
    		
    		LinkedList<Station> route = train.getRoute();
    		
    		for (int i = 0 ; i < route.size(); i++) {
    			if (train.getDistanceTraveled() == route.get(i).getDistance()) {
    				train.setCurrentStation(route.get(i).getName());
    			}
    		}
    	}
    	
    	checkArrivals(time);
    }
    
    public void checkArrivals(final int time) {
    	for (Train train : runningList) {
    		if (train.getDestName().equals(train.getCurrentStation())) {
    			train.park();
    			train.setArrived(true);
    			train.setActualArrivalTime(time);
    			runningList.remove(train);
    		}
    	}
    }
}
