import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;


public class Graph {

    private final Map<Integer, Vertex> graph;
    
    public Graph(final LinkedList<Edge> edges) {
        
        graph = new HashMap<>(edges.size());
        
        for (Edge edge : edges) {
            if (!graph.containsKey(edge.getVertexOne().getID())) {
                
                graph.put(edge.getVertexOne().getVertexNumber(), edge.getVertexOne());
                
            } else if (!graph.containsKey(edge.getVertexTwo().getID())) {
                
                graph.put(edge.getVertexTwo().getVertexNumber(), edge.getVertexTwo());
            }
        }
        
        for (Edge edge : edges) {
            
            graph.get(edge.getVertexOne().getVertexNumber()).getAdj().put(graph.get(edge.getVertexTwo().getVertexNumber()), edge.getWeight());
            
            graph.get(edge.getVertexTwo().getVertexNumber()).getAdj().put(graph.get(edge.getVertexOne().getVertexNumber()), edge.getWeight());
        }
    }
    
    public void dijkstra(final int start) {
        
        if (!graph.containsKey(start)) {
            System.err.print("No such vertex");
            return;
        }
        
        final Vertex startingVertex = graph.get(start);
        NavigableSet<Vertex> set = new TreeSet<>();
        
        for (Vertex vertex : graph.values()) {
            
            if (vertex.getVertexNumber() == start) {
                
                vertex.setPrev(startingVertex);
            } else {
                
                vertex.setPrev(null);
            }
            
            if (vertex.getVertexNumber() == start) {
                
                vertex.setDistance(0);
            } else {
                
                vertex.setDistance(Integer.MAX_VALUE);
            }
            
            set.add(vertex);

        }
        dijkstra(set);
    }
    
    public void dijkstra(NavigableSet<Vertex> set) {
        Vertex one;
        Vertex two;
        
        while(!set.isEmpty()) {
            
            one = set.pollFirst();
            
            if (one.getDistance() == Integer.MAX_VALUE) {
                break;
            }
            
            for (Map.Entry<Vertex, Integer> vertex : one.getAdj().entrySet()) {
                
                two = vertex.getKey();
                
                final int altDist = one.getDistance() + vertex.getValue();
                
                if (altDist < two.getDistance()) {
                    set.remove(two);
                    two.setDistance(altDist);
                    two.setPrev(one);
                    set.add(two);
                }
            }
        }
    }
    
    public LinkedList<int[]> getPath(final int endPoint) {
        
        if (!graph.containsKey(endPoint)) {
            System.err.print("No such vertex");
        }
        
        return graph.get(endPoint).getPath();
    }
    
    //public static void main (final String[] args) throws FileNotFoundException {

        //Graph test = new Graph("TestFile");
        
       // test.dijkstra(3);
        
       // System.out.println(test.getPath(5));
        
       // test.dijkstra(1);
        
       // System.out.println(test.getPath(5));
   // }
}
