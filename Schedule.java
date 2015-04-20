import java.util.LinkedList;


public class Schedule {
    // keep track of the trains that are currently moving, and checks for route conflicts
    // of new trains before adding them.  Once the train reaches its destination it is
    // removed and marked done.
    
    protected LinkedList<Train> runningList;						// the list of trains currently in the schedule
    protected int trainsMoving = 0;									// number of trains moving on the map
    protected int trainsDelaying = 0;									// number of trains being delayed

    public Schedule() {
        runningList = new LinkedList<Train>();
    }
    
    public boolean add(final Train t, final int time) {
        
        boolean addSuccessful = true;
        
        for (Train train : runningList) {
        	// check for conflicts will all trains already in the schedule
            if (conflict(t, train, time)) {
            	// if there is a conflict return false
                addSuccessful = false;
                break;
            }
        }

        // if there are no conflicts add the train to the schedule and set it to moving
        if (addSuccessful) {
            runningList.add(t);
            t.running();
        }
        return addSuccessful;
    }
    
    // remove a train from the schedule
    public boolean remove(Train t) {
        return runningList.remove(t);
    }

    public boolean conflict(final Train t1, final Train t2, final int time) {
        LinkedList<Edge> routeOne = t1.getRoute();				// train one's route
        LinkedList<Edge> routeTwo = t2.getRoute();				// train two's route
        
        for (int i = 0; i < routeOne.size(); i++) {
        	
            for (int j = 0; j < routeTwo.size(); j++) {
            	// if the routes share an equal edge then there is a conflict
                if (routeOne.get(i).equals(routeTwo.get(j))) {
                	
                    //System.out.println("conflict between train " + t1.getID() + " and " + t2.getID() + " at " + routeOne.get(i).toString());
                	// the the new train is not of higher priority return true for a conflict
                	if ((t1.getType().ordinal() <= t2.getType().ordinal()) || (t2.getWaitCount() >= t2.getWaitLimit())) {
                		return true;
                	}

                	// if the new train is of higher priority set the currently running train to stop at the next station and
                	// wait until the higher priority train has completed its route, set the new train to wait at its source
                	// until the currently running train reaches the next station
            		t1.setDelayStop(t1.getSourceName());
            		t1.setDelayTime((routeTwo.get(j).getWeight() - t2.getDistanceTraveled()) / t2.getSpeed() + time);
            		
            		t2.setDelayStop(routeTwo.get(j).getVertexOne().getID());
            		t2.setDelayTime((routeOne.getLast().getWeight() / t1.getSpeed()) + t1.getDelayTime());
            		
            		t2.setWaitCount(t2.getWaitCount() + 1);
            		
            		//System.out.println("Train " + t1.getID() + " will delay until " + t1.getDelayTime() + " at " + t1.getDelayStop() + " station");
            		//System.out.println("Train " + t2.getID() + " will delay until " + t2.getDelayTime() + " at " + t2.getDelayStop() + " station");
            		return false;
                }
            }
        }
        return false;
    }
    
    public void updateTrains(final int time) {

        for (Train train : runningList) {
        	// the the last station visited is the destination station park the train, set it to arrived, and set its actual arrival time
            if (train.getLastStation().equals(train.getDestName())) {
                train.park();
                train.setArrived(true);
                trainsMoving--;
                train.setActualArrivalTime(time + 1);
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
            }
            
            
            // if the train is not parked for a delay call move
            if (train.isMoving()) {
                train.move();
            }
        } 
    }
    
    ////////////////////////     GET / SET   ///////////////////
    
    public LinkedList<Train> getRunningList() {
        return runningList;
    }

    public void setRunningList(LinkedList<Train> runningList) {
        this.runningList = runningList;
    }

	public int getTrainsMoving() {
		return trainsMoving;
	}

	public void setTrainsMoving(int trainsMoving) {
		this.trainsMoving = trainsMoving;
	}

	public int getTrainsDelaying() {
		return trainsDelaying;
	}

	public void setTrainsDelaying(int trainsDelaying) {
		this.trainsDelaying = trainsDelaying;
	}
}
