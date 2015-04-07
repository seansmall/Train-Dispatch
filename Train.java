import java.util.LinkedList;
import java.util.Scanner;


public class Train {

    private String sourceName;
    private String destName;
    private int time;
    private int ID;
    private String currentStation;
    private int expectedArrivalTime;
    private int actualArrivalTime;
    private String type;
    private LinkedList<Station> route;
    private boolean moving;
    private boolean arrived = false;
    private int distanceTraveled = 0;
    private int speed = 1;
    
    
    private static final boolean RUNNING = true;
    private static final boolean PARKED = false;
    
    public Train (final String source, final String dest, final int time, final int id) {
        this.sourceName = source;
        this.destName = dest;
        this.time = time;
        this.ID= id;
        moving = PARKED;
        this.route = new LinkedList<Station>();
    }
    
    public void setRoute(final String routeString){
    	
    	final Scanner SC = new Scanner(routeString);
        
    	int dist;
    	String name;
    	
        while (SC.hasNext()) {
            name = SC.next();
            dist = SC.nextInt();
            
            this.route.add(new Station(name, dist));
        }
        
        SC.close();
        expectedArrivalTime = this.route.getLast().getDistance() * speed;
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
    
    public LinkedList<Station> getRoute() {
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

	public String getCurrentStation() {
		return currentStation;
	}

	public void setCurrentStation(String currentStation) {
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

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}
}
