
public class Station {
    
    private String name;
    private int distance;
    
    public Station(final String name , final int dist) {
        this.name = name;
        this.distance = dist;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
