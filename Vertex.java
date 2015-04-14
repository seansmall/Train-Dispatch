import java.util.HashMap;
import java.util.Map;


public class Vertex implements Comparable<Vertex> {

	private final String ID;						// the unique ID (name) of this vertex
	private Vertex prev;							// used when making spanning trees, it is the vertex directly previous to this one
	private int distance = Integer.MAX_VALUE;				// the distance between this vertex and the previous one, calculated in the graph class
	private final Map<Vertex, Integer> adj = new HashMap<>();		// the adjacency list of all vertices directly connected to this vertex
	
	public Vertex(final String id) {
		this.ID = id;
	}
	
	public String getPath() {
	// this method returns the path from this vertex back through all vertices
	// connected by the previous field
		String path = "";
		// if this vertex has itself list as previous it is the root vertex 
		// assign the id and distance to the path string
		if (this == this.prev) {
			path = this.ID + "," + this.distance +",";
		// if previous is null then this vertex cannot be reached
		} else if (this.prev == null) {
			path = "unreached";
		// call get path on all previous vertices recursively and save to the path string
		} else {
			path = path + this.prev.getPath() + this.ID + "," + this.distance + ",";
		}
		// return the path
		return path;
	}
	
	@Override
	public int compareTo(Vertex vertex) {
		// compare vertices by the distance between them
		return Integer.compare(distance, vertex.getDistance());
	}

		@Override
	public String toString() {
		// assign this vertex id and distance to the return string s
		String s = this.ID + "," + 0 + "\r";

		// return s
		return s;
	}
	
	public String toAdjString() {
		// assign this vertex id and distance to the return string s
		String s = this.ID + "," + 0 + "\r";
		
		// add all of the adjacent vertices and their distances to the return string s
		for (Map.Entry<Vertex, Integer> e : adj.entrySet()) {
			s = s + e.getKey().getID() + "," +e.getValue() + "\r";
		}
		// return s
		return s;
	}
	
	public boolean equals(Vertex v) {
		return (v.getID().equals(ID));
	}
	
	//////////////   Get/Set methods  ////////////////////
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
