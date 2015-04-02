
public class Train {

    private int source, destination, time, ID;
    private String type;
    private int[][] route;
    
    public Train (int s, int d, int t, int id) {
        source = s;
        destination = d;
        time = d;
        ID= id;
    }
    
    public int getSource () {
        return source;
    }
    
    public int getDestination () {
        return destination;
    }
    
    public int getProposedTime () {
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
    
    public void setRoute(final int[] stations, final int[] distance){
        
        route = new int[stations.length][2];
        
        for (int i = 0; i < route.length; i++) {
            route[i][0] = stations[i];
            route[i][1] = distance[i];
        }
    }
    
    public int[][] getRoute() {
        return route;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }
}
