import java.util.HashMap;
import java.util.Map;


public class Vertex implements Comparable<Vertex> {

    private final String ID;
    private final int vertexNumber;
    private Vertex prev;
    private int distance = Integer.MAX_VALUE;import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Vertex implements Comparable<Vertex> {

    private final String ID;
    private final int vertexNumber;
    private Vertex prev;
    private int distance = Integer.MAX_VALUE;
    private final Map<Vertex, Integer> adj = new HashMap<>();
    private Type type;
    
    enum Type {
        station,
        intermediatePoint
    }
    
    public Vertex(final String id, final int vertexNum) {
        this.ID = id;
        this.vertexNumber = vertexNum;
    }
    
    public LinkedList<int[]> getPath() {
        
        LinkedList<int[]> path = new LinkedList<int[]>();
        
        if (this == this.prev) {
            int[] station = {this.vertexNumber, this.distance};
            path.add(station);
        } else if (this.prev == null) {
            path = null;
        } else {
            path.addAll(this.prev.getPath());
            int[] station = {this.vertexNumber, this.distance};
            path.add(station);
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

    public int getVertexNumber() {
        return vertexNumber;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
    private final Map<Vertex, Integer> adj = new HashMap<>();
    private Type type;
    
    enum Type {
        station,
        intermediatePoint
    }
    
    public Vertex(final String id, final int vertexNum) {
        this.ID = id;
        this.vertexNumber = vertexNum;
    }
    
    public String getPath() {
        
        String path = "";
        
        if (this == this.prev) {
            path = this.ID + "(" + this.distance +") ";
        } else if (this.prev == null) {
            path = "unreached";
        } else {
            path = path + this.prev.getPath() + this.ID + "(" + this.distance + ") ";
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

    public int getVertexNumber() {
        return vertexNumber;
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
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
