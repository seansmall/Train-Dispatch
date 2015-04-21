import java.util.Collections;
import java.util.LinkedList;


public class SmartSchedule extends Schedule{
    // keep track of the trains that are currently moving, and checks for route conflicts
    // of new trains before adding them.  Once the train reaches its destination it is
    // removed and marked done.

	@Override
    public boolean add(final Train t, final int time) {

        for (Train train : runningList) {
        	// check for conflicts will all trains already in the schedule
            conflict(t, train, time);
        }

        runningList.add(t);
        t.running();
        return true;

    }
	
	@Override
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
                        for (Delay delay : t1.getDelays()) {
                        	if (trainOneMinTime > delay.getTime()) {
                        		trainOneMinTime += delay.getTime();
                        	}
                        }
                    } else {
                        trainOneMinTime = 0;
                    }
                    
                    if (j > 0) {
                        trainTwoMinTime = routeTwo.get(j - 1).getWeight() / t2.getSpeed() + time;
                        for (Delay delay : t2.getDelays()) {
                        	if (trainTwoMinTime > delay.getTime()) {
                        		trainTwoMinTime += delay.getTime();
                        	}
                        }
                    } else {
                        trainTwoMinTime = 0;
                    }
                    
                    
                    // calculate the time each train will leave the conflicting edge
                    int trainOneMaxTime;
                    int trainTwoMaxTime;
                	
                    if (i > 0) {
                    	trainOneMaxTime = (routeOne.get(i).getWeight() - routeOne.get(i - 1).getWeight())/ t1.getSpeed() + time + trainOneMinTime;
                    } else {
                    	trainOneMaxTime = routeOne.get(i).getWeight() / t1.getSpeed() + time;
                    }
                	
                    if (j > 0) {
                    	trainTwoMaxTime = (routeTwo.get(j).getWeight() - routeTwo.get(j - 1).getWeight())/ t2.getSpeed() + time + trainTwoMinTime;
                    } else {
                    	trainTwoMaxTime = routeTwo.get(j).getWeight() / t2.getSpeed() + time;
                    }

                	// if the trains will both be on the conflicting edge at the same time set a delay
                	if ((trainOneMinTime >= trainTwoMinTime) || (trainOneMaxTime <= trainTwoMaxTime)) {
                		
                		// if the new train is of higher priority set the old train to delay at the first
                		// station on it's conflicting edge until the higher priority train leaves the edge
                		if (t1.getType().ordinal() > t2.getType().ordinal()  || (t2.getWaitCount() < t2.getWaitLimit())) {
                			
                			t2.setWaitCount(t2.getWaitCount() + 1);
                			
                			t2.getDelays().add(new Delay(routeTwo.get(j).getVertexOne().getID(), trainOneMaxTime));
                        	Collections.sort(t2.getDelays());
                    		
                        	//System.out.println("Train " + t1.getID() + " will delay until " + t1.getDelayTime() + " at " + t1.getDelayStop() + " station");
                    		//System.out.println("Train " + t2.getID() + " will delay until " + t2.getDelayTime() + " at " + t2.getDelayStop() + " station");
                		} else {
                			
                			// if the new train is of equal or lower priority set it to delay at the first station on its conflicting edge
                			// until the already running train leaves the edge
                    		t1.getDelays().add(new Delay(routeOne.get(i).getVertexOne().getID(), trainTwoMaxTime));
                    		Collections.sort(t1.getDelays());
                    		
                    		//System.out.println("Train " + t2.getID() + " will delay until " + t2.getDelayTime() + " at " + t2.getDelayStop() + " station");
                		}
                		return false;
                	}
                }
            }
        }
        return false;
	}
}
