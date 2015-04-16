import java.util.LinkedList;


public class SmartSchedule extends Schedule{
    
	public boolean add(final Train t, final int time) {
		
	     boolean addSuccessful = true;
	        
	        for (Train train : this.runningList) {
	            if (!train.hasArrived()) {
	                if (conflict(t, train, time)) {
	                    addSuccessful = false;
	                    break;
	                }
	            }
	        }

	        if (addSuccessful) {
	        	this.runningList.add(t);
	        	t.running();
	        }
	        return addSuccessful;
	}

	public boolean conflict(final Train t1, final Train t2, final int time) {
        LinkedList<Edge> routeOne = t1.getRoute();
        LinkedList<Edge> routeTwo = t2.getRoute();

        for (int i = 0; i < routeOne.size(); i++) {
            for (int j = 0; j < routeTwo.size(); j++) {
                if (routeOne.get(i).equals(routeTwo.get(j))) {
                    System.out.println("conflict between train " + t1.getID() + " and " + t2.getID() + " at " + routeOne.get(i).toString());
                	// save the index of the conflict edge and the previous edge
                	// use speed to compare the time both trains will need to use the 
                	// conflicting edge, if they are the same, set the new train to delay
                	// at the first vertex on the conflicting edge until the edge is free
                    int trainOneMinTime;
                    int trainTwoMinTime;
                    
                    if (i > 0) {
                        trainOneMinTime = routeOne.get(i - 1).getWeight() / t1.getSpeed() + time;
                    } else {
                        trainOneMinTime = 0;
                    }
                    
                    if (j > 0) {
                        trainTwoMinTime = routeTwo.get(j - 1).getWeight() / t2.getSpeed() + time;
                    } else {
                        trainTwoMinTime = 0;
                    }
                	
                	int trainOneMaxTime = routeOne.get(i).getWeight() / t1.getSpeed() + time;
                	int trainTwoMaxTime = routeTwo.get(j).getWeight() / t2.getSpeed() + time;
                	
                	if ((trainOneMinTime >= trainTwoMinTime) || (trainOneMaxTime <= trainTwoMaxTime)) {
                		t1.setDelayStop(routeOne.get(i).getVertexOne().getID());
                		t1.setDelayTime(trainTwoMaxTime);
                		System.out.println("Train " + t1.getID() + " will delay until " + t1.getDelayTime() + " at " + t1.getDelayStop() + " station");
                		return false;
                	} 
                }
            }
        }
        // return true only if the first edge on the new trains route is the conflict
        return false;
	}
	
	@Override
    public void updateTrains(final int time) {

        for (Train train : runningList) {

            if (train.isMoving()) {
                train.move();
            }
            
            if (train.getLastStation().equals(train.getDestName())) {
                train.park();
                train.setArrived(true);
                train.setActualArrivalTime(time + 1);
                System.out.println( "train " + train.getID() + " has arrived at " + train.getActualArrivalTime() + " expected arrival was at " + train.getExpectedArrivalTime());
                System.out.println("train " + train.getID() + " took route " + train.getRoute().toString() + "\r");
            }
        }       
    }
}
