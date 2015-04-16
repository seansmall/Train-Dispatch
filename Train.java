import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Train {

    private String sourceName;
    private String destName;
    private int expectedDepartureTime;
    private int actualDepatureTime;
    private int ID;
    private String lastStation;
	private int expectedArrivalTime;
    private int actualArrivalTime;
    private String type;
    private LinkedList<Edge> route;
    private boolean moving;
    private boolean arrived = false;
    private int distanceTraveled = 0;
    private int routeDistance;
    private int speed = 1;
    
    
    private static final boolean RUNNING = true;
    private static final boolean PARKED = false;
    
    public Train (final String source, final String dest, final int time, final int id) {
        this.sourceName = source;
        this.destName = dest;
        this.expectedDepartureTime = time;
        this.ID= id;
        moving = PARKED;
        this.route = new LinkedList<Edge>();
    }
    
    public void setRoute(final String routeString){
    	
    	final Scanner SC = new Scanner(routeString);
        SC.useDelimiter(",\\s*");
    	
    	ArrayList<Integer> distances = new ArrayList<Integer>();
    	ArrayList<String> names = new ArrayList<String>();
    	
        while (SC.hasNext()) {
            names.add(SC.next());
            distances.add(SC.nextInt());
        }
        SC.close();
        
        for (int i = 0; i < names.size() - 1; i++) {
        	Vertex one = new Vertex(names.get(i));
        	Vertex two = new Vertex(names.get(i + 1));
        	int distanceTo = distances.get(i + 1);
        	route.add(new Edge(one, two, distanceTo));
        }
        
        lastStation = route.getFirst().getVertexOne().getID();
        expectedArrivalTime = route.getLast().getWeight() / speed + expectedDepartureTime;
    }
    
	public void move() {
		distanceTraveled += speed;
		
		for (Edge e : route) {
			if (e.getWeight() <= distanceTraveled) {
				lastStation = e.getVertexTwo().getID();
			} else if (e.getWeight() > distanceTraveled) {
				break;
			}
		}
	}
    
	public void park() {
		this.moving = PARKED;
	}
	
	public void running() {
		this.moving = RUNNING;
	}

    public int getDelay () {
        return actualArrivalTime - expectedArrivalTime;
    }
    
    public int getDepatureTime () {
        return expectedDepartureTime;
    }
    
    public void setType (String t) {
        type = t;
    }
    
    public String getType() {
        return type;
    }
    
    public LinkedList<Edge> getRoute() {
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

	public String getCurrentStation() {
		return lastStation;
	}

	public void setCurrentStation(String currentStation) {
		this.lastStation = currentStation;
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

	public int getRouteDistance() {
		return routeDistance;
	}

	public void setRouteDistance(int routeDistance) {
		this.routeDistance = routeDistance;
	}
	
    public String getLastStation() {
		return lastStation;
	}

	public void setLastStation(String lastStation) {
		this.lastStation = lastStation;
	}

	public int getActualDepatureTime() {
		return actualDepatureTime;
	}

	public void setActualDepatureTime(int actualDepatureTime) {
		this.actualDepatureTime = actualDepatureTime;
	}
}
