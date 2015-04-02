
public class Train {

    private int source, destination, time;
    private String type;
    public Train (int s, int d, int t) {
        source = s;
        destination = d;
        time = t;
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
}
