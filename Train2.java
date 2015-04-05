
public class Train {

    private int source, destination, time, ID;
    private String type;
    private int[][] route;
    
    public Train (int s, int d, int t, int id) {
        source = s;
        destination = d;
        time = d;import java.util.LinkedList;


public class Train {

    private int source;
    private int destination;
    private int time;
    private int ID;
    private int currentStation;
    private int expectedArrivalTime;
    private int actualArrivalTime;
    private String type;
    private int[][] route;
    private boolean moving;
    private boolean arrived = false;
    private int distanceTraveled = 0;
    private int speed = 1;
    
    
    private static final boolean RUNNING = true;
    private static final boolean PARKED = false;
    
    public Train (int s, int d, int t, int id) {
        source = s;
        destination = d;
        time = d;
        ID= id;
        moving = PARKED;
        currentStation = s;
    }
    
    public int getSource () {
        return source;
    }
    
    public int getDestination () {
        return destination;
    }
    
    public int getDepatureTime () {
        return time;
    }
    
    public int getDelay (int actualtime) {
        return actualtime - time;
    }
    
    public void setType (String t) {
        type = t;
    }
    
    public String getType() {
        return type;
    }
    
    public void setRoute(final LinkedList<int[]> route){
        
        this.route = new int[route.size()][2];
        
        int i = 0;
        for (int[] station : route) {
            this.route[i][0] = station[0];
            this.route[i][1] = station[1];
        }
        
        expectedArrivalTime = this.route[this.route.length - 1][1] * speed;
    }
    
    public int[][] getRoute() {
        return route;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
    
	public void park() {
		this.moving = PARKED;
	}
	
	public void running() {
		this.moving = RUNNING;
	}

	public int getCurrentStation() {
		return currentStation;
	}

	public void setCurrentStation(int currentStation) {
		this.currentStation = currentStation;
	}

	public boolean hasArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public int getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(int distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}
	
	public void move() {
		distanceTraveled += speed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getExpectedArrivalTime() {
		return expectedArrivalTime;
	}

	public void setExpectedArrivalTime(int expectedArrivalTime) {
		this.expectedArrivalTime = expectedArrivalTime;
	}

	public int getActualArrivalTime() {
		return actualArrivalTime;
	}

	public void setActualArrivalTime(int actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}
}
        ID= id;
    }
    
    public int getSource () {
        return source;
    }
    
    public int getDestination () {
        return destination;
    }
    
    public int getProposedTime () {
        return time;
    }
    
    public int getDelay (int actualtime) {
        return actualtime - time;
    }
    
    public void setType (String t) {
        type = t;
    }
    
    public String getType() {
        return type;
    }
    
    public void setRoute(final int[] stations, final int[] distance){
        
        route = new int[stations.length][2];
        
        for (int i = 0; i < route.length; i++) {
            route[i][0] = stations[i];
            route[i][1] = distance[i];
        }
    }
    
    public int[][] getRoute() {
        return route;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }
}
