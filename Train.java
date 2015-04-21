import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;


public class Train {

    private static final boolean RUNNING = true;				// constant value used to show that the train is currently moving
    private static final boolean PARKED = false;				// constant value used to show that the train is currently parked at a station
	
    private String sourceName;									// the name of the station where this train originates
    private String destName;									// the name of this trains destination
    private String lastStation;									// the last station this train passed on its route
    
    private Type type;											// this trains type, passenger, cargo, or priority
    
    private int expextedDepartureTime;							// this trains scheduled departure time
    private int actualDepartureTime;							// the time this train actually left is source station
    private int ID;												// this trains unique id
	private int expectedArrivalTime;							// the time this train is scheduled to arrive at its destination
    private int actualArrivalTime;								// the time this train actually arrived at its destination
    private int speed = 1;										// this trains speed per tick
    private int distanceTraveled = 0;							// the current distance this train has traveled
    private int waitLimit = 2;									// max number of time the train can be made to wait in favor of a higher priority train
    private int waitCount = 0;									// number of times this train has been made to delay in favor of a higher priority train
    
    private boolean moving;										// if this train is moving or parked
    private boolean arrived = false;							// if this train has arrived at its destination or not
    
    private LinkedList<Edge> route;								// this trains route
    private ArrayList<Delay> delayStations;						// list of stations this train will delay at, and until what time the train will delay
    
    private Color color;										// the trains color
    private Coordinates coordinate;								// the trains coordinates for the animation classes
    private Edge currentEdge;									// the edge the train is currently on, for the animation classes
    
    public enum Type {
    	cargo,
    	passenger,
    	priority
    };
 
    public Train (final String source, final String dest, final int time, final int id, final int type) {
        this.sourceName = source;
        this.destName = dest;
        this.expextedDepartureTime = time;
        this.ID= id;
        this.moving = PARKED;
        this.route = new LinkedList<Edge>();
        this.delayStations = new ArrayList<Delay>();
        
        if (type == 0) {
        	this.type = Type.cargo;
        } else if (type == 1) {
        	this.type = Type.passenger;
        } else if (type == 2){
        	this.type = Type.priority;
        }
    }
    // this method takes a string of station names and distances to make the
    // route this train will take 
    public void setRoute(final String routeString){
    	
    	final Scanner SC = new Scanner(routeString);
        SC.useDelimiter(",\\s*");
    	
    	ArrayList<Integer> distances = new ArrayList<Integer>();
    	ArrayList<String> names = new ArrayList<String>();
    	//System.out.println(routeString);
        while (SC.hasNext()) {
        	String name = SC.next();
            int dist = SC.nextInt();
            //System.out.println(name + ", " + dist);
        	names.add(name);
            distances.add(dist);
        }
        SC.close();
        
        for (int i = 0; i < names.size() - 1; i++) {
        	// the two stations on either end of this track segment
        	Vertex one = new Vertex(names.get(i));
        	Vertex two = new Vertex(names.get(i + 1));
        	// the distance from the source station to the end of this
        	// track segment
        	int distanceTo = distances.get(i + 1);
        	// each segment of the route is one edge
        	route.add(new Edge(one, two, distanceTo));
        }
        // set initial last station to the first station on the route
        lastStation = route.getFirst().getVertexOne().getID();
        // calculate the expected arrival time
        expectedArrivalTime = route.getLast().getWeight() / speed + expextedDepartureTime;
    }
    
	public void move() {
		// increase the distance traveled by the speed per tick
		distanceTraveled += speed;
		// check if the train passed a new station and update last station if needed
		for (Edge e : route) {
			if (e.getWeight() <= distanceTraveled) {
				lastStation = e.getVertexTwo().getID();
			} else if (e.getWeight() > distanceTraveled) {
				break;
			}
		}
	}
	
	// calculate the delay by the difference between the expected arrival time and the actual arrival time
    public int getDelay () {
        return actualArrivalTime - expectedArrivalTime;
    }
	
    public void setRandomColor () {
        Random rng = new Random();
        color = new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
    }
    
    ////////////////////////  GET / SET methods    /////////////////////////
	
    public Color getColor () {
        return new Color(color.getRGB());
    }
    
	public void park() {
		this.moving = PARKED;
	}
	
	public void running() {
		this.moving = RUNNING;
	}

    public int getDepatureTime () {
        return expextedDepartureTime;
    }
    
    public void setType (Type t) {
        type = t;
    }
    
    public Type getType() {
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
	
    public String getLastStation() {
		return lastStation;
	}

	public void setLastStation(String lastStation) {
		this.lastStation = lastStation;
	}

    public int getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(int actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

	public boolean isParked() {
		return !moving;
	}
	public int getWaitLimit() {
		return waitLimit;
	}
	public void setWaitLimit(int waitLimit) {
		this.waitLimit = waitLimit;
	}
	public int getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}
	
	public Coordinates getCoordinates() {
	       return coordinate;
	}
	
	public void setCoordinates(Coordinates a) {
	    coordinate = new Coordinates(a.getX(), a.getY());
	    }

	public Edge getCurrentEdge () {
	    return currentEdge;
	}

	public void setCurrentEdge (Edge a) {
       currentEdge = new Edge(a.getVertexOne(),a.getVertexTwo(),a.getWeight());
	}
	public ArrayList<Delay> getDelays() {
		return delayStations;
	}
	public void setDelays(ArrayList<Delay> delays) {
		this.delayStations = delays;
	}
}
