import java.util.LinkedList;


public class SmartSchedule extends Schedule{
    // keep track of the trains that are currently moving, and checks for route conflicts
    // of new trains before adding them.  Once the train reaches its destination it is
    // removed and marked done.

	@Override
	public boolean add(final Train t, final int time) {
		
	     boolean addSuccessful = true;
	        
	        for (Train train : this.runningList) {
	        	// check for conflicts will all trains already in the schedule
	            if (conflict(t, train, time)) {
	            	// if there is a conflict return false
	                addSuccessful = false;
	                break;
	            }
	        }

	        // if there are no conflicts add the train to the schedule and set it to moving
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
            	// if the routes share an equal edge then there is a conflict
                if (routeOne.get(i).equals(routeTwo.get(j))) {
                    
                	System.out.println("conflict between train " + t1.getID() + " and " + t2.getID() + " at " + routeOne.get(i).toString());
                    // calculate the time that each train will first reach the conflicting edge
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
                	// calculate the time each train will leave the conflicting edge
                	int trainOneMaxTime = routeOne.get(i).getWeight() / t1.getSpeed() + time;
                	int trainTwoMaxTime = routeTwo.get(j).getWeight() / t2.getSpeed() + time;
                	// if the trains will both be on the conflicting edge at the same time set a delay
                	if ((trainOneMinTime >= trainTwoMinTime) || (trainOneMaxTime <= trainTwoMaxTime)) {
                		// if the new train is of higher priority set the old train to delay at the first
                		// station on it's conflicting edge until the higher priority train leaves the edge
                		if (t1.getType().ordinal() > t2.getType().ordinal()  || (t2.getWaitCount() < t2.getWaitLimit())) {
                			
                			t2.setWaitCount(t2.getWaitCount() + 1);
                			
                			t2.setDelayStop(routeTwo.get(j).getVertexOne().getID());
                    		t2.setDelayTime(trainOneMaxTime);
                    		
                    		//System.out.println("Train " + t1.getID() + " will delay until " + t1.getDelayTime() + " at " + t1.getDelayStop() + " station");
                    		//System.out.println("Train " + t2.getID() + " will delay until " + t2.getDelayTime() + " at " + t2.getDelayStop() + " station");
                		} else {
                			// if the new train is of equal or lower priority set it to delay at the first station on its conflicting edge
                			// until the already running train leaves the edge
                    		t1.setDelayStop(routeOne.get(i).getVertexOne().getID());
                    		t1.setDelayTime(trainTwoMaxTime);
                    		
                    		//System.out.println("Train " + t2.getID() + " will delay until " + t2.getDelayTime() + " at " + t2.getDelayStop() + " station");
                		}
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
        	// the the last station visited is the destination station park the train, set it to arrived, and set its actual arrival time
            if (train.getLastStation().equals(train.getDestName())) {
                train.park();
                train.setArrived(true);
                train.setActualArrivalTime(time + 1);
                trainsMoving--;
                System.out.println( "train " + train.getID() + " has arrived at " + train.getActualArrivalTime() + " expected arrival was at " + train.getExpectedArrivalTime());
                System.out.println("train " + train.getID() + " took route " + train.getRoute().toString() + "\r");
            }
            
        	// if the train reaches the delay station then it should stop moving until after the delay time
            // has passed
            if ((!train.hasArrived()) && (runningList.contains(train)) && (train.getDelayStop().equals(train.getLastStation()))) {
                if ((train.getDelayTime() > time) && (train.isMoving()) && (runningList.size() != trainsDelaying)) {
                    train.park();
                    trainsDelaying++;
                    trainsMoving--;
                    System.out.println(" at " + time + " train " + train.getID() + " will delay at " + train.getDelayStop() + " until " + train.getDelayTime());
                } else if ((train.getDelayTime() < time) && (train.isParked())) {
                    train.running();
                    trainsMoving++;
                    trainsDelaying--;
                    System.out.println(" at " + time + " train " + train.getID() + " resumed running from " + train.getDelayStop());
                }
            }
            
            /*
            for (int i = 0; i < runningList.size(); i++) {
            	if ((train.getDelayStop().equals(runningList.get(i).getDelayStop())) && ((train.getDelayTime() < time)) && (train.isParked() == runningList.get(i).isParked())) {
                    train.running();
                    trainsMoving++;
                    trainsDelaying--;
                    System.out.println(" at " + time + " train " + train.getID() + " resumed running from " + train.getDelayStop());
                    break;
            	}
            }
            */
            
            if (train.isMoving()) {
                train.move();
            }
        }     
    }
}
