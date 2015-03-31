import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;


public class Graph {

    private final Map<String, Vertex> graph;
    
    public Graph(final LinkedList<Edge> edges) {
        
        graph = new HashMap<>(edges.size());
        
        for (Edge edge : edges) {
            if (!graph.containsKey(edge.getVertexOne().getID())) {
                
                graph.put(edge.getVertexOne().getID(), edge.getVertexOne());
                
            } else if (!graph.containsKey(edge.getVertexTwo().getID())) {
                
                graph.put(edge.getVertexTwo().getID(), edge.getVertexTwo());
            }
        }
        
        for (Edge edge : edges) {
            
            graph.get(edge.getVertexOne().getID()).getAdj().put(graph.get(edge.getVertexTwo().getID()), edge.getWeight());
            
            graph.get(edge.getVertexTwo().getID()).getAdj().put(graph.get(edge.getVertexOne().getID()), edge.getWeight());
        }
    }
    
    public Graph(final String fileName) throws FileNotFoundException {
        this(read(fileName));
    }
    
    public void dijkstra(final String start) {
        
        if (!graph.containsKey(start)) {
            System.err.print("No such vertex");
            return;
        }
        
        final Vertex startingVertex = graph.get(start);
        NavigableSet<Vertex> set = new TreeSet<>();
        
        for (Vertex vertex : graph.values()) {
            
            if (vertex.getID().equals(start)) {
                
                vertex.setPrev(startingVertex);
            } else {
                
                vertex.setPrev(null);
            }
            
            if (vertex.getID().equals(start)) {
                
                vertex.setDistance(0);
            } else {
                
                vertex.setDistance(Integer.MAX_VALUE);
            }
            
            set.add(vertex);

        }
        //for (Vertex v : set) {
        //    System.out.printf("%s %d ", v.getID(), v.getDistance());
        //}
        dijkstra(set);
    }
    
    public void dijkstra(NavigableSet<Vertex> set) {
        Vertex one;
        Vertex two;
        
        while(!set.isEmpty()) {
            
            one = set.pollFirst();                              //TODO pollfirst not returning lowest value
            
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
                //System.out.printf("%s %s%n", one.getID(), two.getID());
            }
        }
    }
    
    public String getPath(final String endPoint) {
        
        if (!graph.containsKey(endPoint)) {
            System.err.print("No such vertex");
            return "No such vertex";
        }
        
        return graph.get(endPoint).getPath();
    }
    
    public static LinkedList<Edge> read(final String fileName) throws FileNotFoundException {
        
        LinkedList<Edge> edges = new LinkedList<Edge>();
        
        File file = new File("TestList.txt");
    
        try {
            final Scanner SC = new Scanner(file);
            
            int vertexCount = 0;
            
            
            while (SC.hasNext()) {              
                
                Vertex vertexOne = new Vertex(SC.next(), vertexCount);

                vertexCount++;
                
                Vertex vertexTwo= new Vertex(SC.next(), vertexCount);

                vertexCount++;
                
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

        Graph test = new Graph("TestFile");
        
        test.dijkstra("c");
        
        System.out.println(test.getPath("h"));
    }
}
