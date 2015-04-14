import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;


public class Graph {

	// the graph
	private final Map<String, Vertex> graph;
	
	public Graph(final LinkedList<Edge> edges) {
		// create a new hash map of size equal to the number of edges
		// in the edge list
		graph = new HashMap<>(edges.size());
		
		for (Edge edge : edges) {
			// check each edge, if either vertex one or two in the edge has not already
			// been added to the graph, add it
			if (!graph.containsKey(edge.getVertexOne().getID())) {
				
				graph.put(edge.getVertexOne().getID(), edge.getVertexOne());
				
			} else if (!graph.containsKey(edge.getVertexTwo().getID())) {
				
				graph.put(edge.getVertexTwo().getID(), edge.getVertexTwo());
			}
		}
		
		for (Edge edge : edges) {
		    // check each edge, for vertex one add vertex two and the edge's weight to vertex one's adjacency list
			// do the same for vertex two, this graph is undirected.  To make the graph directed comment out on or the other
			// of the if blocks depending or the direction of the connections
			if (graph.containsKey(edge.getVertexOne().getID())) {
			    graph.get(edge.getVertexOne().getID()).getAdj().put(graph.get(edge.getVertexTwo().getID()), edge.getWeight());
			}		
			if (graph.containsKey(edge.getVertexTwo().getID())) {
			    graph.get(edge.getVertexTwo().getID()).getAdj().put(graph.get(edge.getVertexOne().getID()), edge.getWeight());
			}
			
		}
		//System.out.println(toString());
	}
	
	public Graph(final String file) throws FileNotFoundException {
		this(read(file));
	}
	
	public void dijkstra(final String start) {
		// check to make sure the starting vertex is in the graph
		if (!graph.containsKey(start)) {
			System.err.print("No such vertex");
			return;
		}
		// assign the starting vertex to a temp variable
		final Vertex startingVertex = graph.get(start);
		// create a new tree set to make the spanning tree in
		NavigableSet<Vertex> set = new TreeSet<>();
		
		for (Vertex vertex : graph.values()) {
			// check each vertex in the graph, when the starting vertex has been reached
			// set it's previous field to itself, otherwise set the previous field to null
			if (vertex.getID().equals(start)) {
				
				vertex.setPrev(startingVertex);
			} else {
				
				vertex.setPrev(null);
			}
			// check each vertex in the graph, when the starting vertex is reached set it's distance to 0, 
			// otherwise set the distance to the max integer value
			if (vertex.getID().equals(start)) {
				
				vertex.setDistance(0);
			} else {
				
				vertex.setDistance(Integer.MAX_VALUE);
			}
			// add each vertex to the set
			set.add(vertex);
		}	
		// call the next Dijkstra method on with the new set
		dijkstra(set);
	}
	
	public void dijkstra(NavigableSet<Vertex> set) {
		Vertex one;
		Vertex two;
		// loop until all vertices are removed from the set
		while(!set.isEmpty()) {
			// assign the first vertex in the set to one and remove it
			one = set.pollFirst();
			// make sure the first vertex has been mapped to, it must have a 
			// distance other than the max integer value
			// if the distance is max value it has not been mapped
			if (one.getDistance() == Integer.MAX_VALUE) {
				break;
			}
			
			for (Map.Entry<Vertex, Integer> vertex : one.getAdj().entrySet()) {
				// check each entry in the adjacency list of vertex one and assign them to vertex to two
				two = vertex.getKey();
				// get the alternate distance by adding the distance vertex one is from the starting vertex
				// to the weight between vertex one and vertex two
				final int altDist = one.getDistance() + vertex.getValue();
				// if the alternate distance is less than the current distance between vertex two
				// and the starting vertex remove two from the set, change the distance to the new alternate distance,
				// set it's previous field to vertex one, and re-add it to the set
				
				if (altDist < two.getDistance()) {
					
					set.remove(two);
					
					two.setDistance(altDist);
					two.setPrev(one);
					
					boolean add = set.add(two);
					// backup if two stations have the same distance one will not be added, if that happens
					// increase the distance by one and add it, then re set to the actual distance
					if (!add) {
						//System.out.println(two.getID() + " " + two.getDistance());
						
						two.setDistance(altDist + 1);
						boolean addBackup = set.add(two);
						two.setDistance(altDist);
						
						if (!addBackup) {
							System.out.println(two.getID() + " " + two.getDistance());
						}
					}
				}
			}
		}
		//System.out.println(getTree());
	}
	
	public String getPath(final String endPoint) {
		// check to make sure the destination is in the graph
		if (!graph.containsKey(endPoint)) {
			System.err.print("No such vertex");
			return "No such vertex";
		}
		// call the get path method from the destination vertex and return the string
		return graph.get(endPoint).getPath();
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for (Map.Entry<String, Vertex> e : graph.entrySet()) {
			s = s + e.getValue().toString() + "\r";
		}
		return s;
	}
	
	public String getTree() {
		String s = "";
		
		for (Map.Entry<String, Vertex> e : graph.entrySet()) {
			if (e.getValue().getPrev() != null) {
				s = s + e.getKey() + "," + e.getValue().getPrev().getID() + "," + e.getValue().getDistance() + "\r";
			} else {
				s = s + e.getKey() + "," + "null" + "," + e.getValue().getDistance() + "\r";
			}
			
		}
		return s;
	}
	
	public static LinkedList<Edge> readGDF(final File file) throws FileNotFoundException {
		
		LinkedList<Edge> edges = new LinkedList<Edge>();
		
		try {
			final Scanner SC = new Scanner(file);

			SC.nextLine();
			SC.nextLine();
			SC.nextLine();
			SC.nextLine();
			
			while (SC.hasNext()) {				
				
				Vertex vertexOne = new Vertex(SC.next().trim());

				Vertex vertexTwo= new Vertex(SC.next().trim());

				final int weight = (int)SC.nextDouble();
				
				edges.add(new Edge(vertexOne, vertexTwo, weight));
			}
			
			SC.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		
		return edges;
	}
	
    public static LinkedList<Edge> read(final String fileName) {
        
        LinkedList<Edge> edges = new LinkedList<Edge>();
        
        File file = new File(fileName);
    
        try {
            final Scanner SC = new Scanner(file);
            SC.useDelimiter(",\\s*");

            while (SC.hasNext()) {              
                
                Vertex vertexOne = new Vertex(SC.next());

                Vertex vertexTwo= new Vertex(SC.next());

                final int weight = SC.nextInt();
                
                edges.add(new Edge(vertexOne, vertexTwo, weight));
            }
            
            SC.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        
        return edges;
    }
    
    
	public static void main (final String[] args) throws FileNotFoundException {
		
		Graph test = new Graph("Amtrak System Data.txt");
		
		test.dijkstra("Boston");
		// TODO Los Angeles is not being added back into the set during Dijkstra's
		//System.out.println(test.getPath("Los Angeles"));
		
		System.out.println("Done");

	}
	
}
