import java.util.HashMap;
import java.util.Map;


public class Vertex implements Comparable<Vertex> {

	private final String ID;
	private Vertex prev;
	private int distance = Integer.MAX_VALUE;
	private final Map<Vertex, Integer> adj = new HashMap<>();
	
	public Vertex(final String id) {
		this.ID = id;
	}
	
	public String getPath() {
		
		String path = "";
		
		if (this == this.prev) {
			path = this.ID + " " + this.distance +" ";
		} else if (this.prev == null) {
			path = "unreached";
		} else {
			path = path + this.prev.getPath() + this.ID + " " + this.distance + " ";
		}
		
		return path;
	}
	
	@Override
	public int compareTo(Vertex vertex) {
		return Integer.compare(distance, vertex.getDistance());
	}

	public Vertex getPrev() {
		return prev;
	}

	public void setPrev(Vertex vertex) {
		this.prev = vertex;
	}

	public String getID() {
		return ID;
	}

	public Map<Vertex, Integer> getAdj() {
		return adj;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}	
}
