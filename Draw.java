import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Draw {

    private static ArrayList<String> City = new ArrayList<String>();
    private static String map;
    private static ArrayList<Coordinates> stations = new ArrayList<Coordinates>();
    private static ArrayList<Coordinates> lines = new ArrayList<Coordinates>();
    private static int ticks = 0;
    private static ArrayList<Train> trains = new ArrayList<Train>();
    
    public static void main (String[] args) throws IOException {
        
        StdDraw.setCanvasSize(1300, 680);
        StdDraw.setXscale(-650, 650);
        StdDraw.setYscale(-340,340);
        
         map = "Amtrak System Map.png";
         if (map.contains("Amtrak")) {
             for (int i = 0; i < SequenceGenerator.AMTRAK_STATIONS.length; i++) {
             City.add(SequenceGenerator.AMTRAK_STATIONS[i]);
             }    
             readCoordinates("Amtrak Coordinates.txt");
            readLines("Amtrak System Data.txt");
         }else {
             for (int i = 0; i < SequenceGenerator.EUROPE_STATIONS.length; i++) {
             City.add(SequenceGenerator.EUROPE_STATIONS[i]);
             }    
             readCoordinates("Europe Coordinates.txt");
             readLines("Europe Map Data.txt");
         }
         
         if (args.length != 0) {
             if (args[0].equals("edit")) {
            findStations();            
            editStations();
            editLines();
            return;
             }
         }
        
        animation();

    }    
  //***************************************
  //Draws each of the stations
  //***************************************
    public static void drawStation () {
        for (int i = 0; i < stations.size(); i++) {
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.setPenRadius(.05);
            StdDraw.point(stations.get(i).getX(), stations.get(i).getY());
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(stations.get(i).getX(), stations.get(i).getY(), Integer.toString(i+1));
            StdDraw.setPenColor(StdDraw.BLACK);
        }
    }    
    //***************************************
    //Reads the coordinates of the stations
    //***************************************
    private static void readCoordinates (String newPoints) throws FileNotFoundException {
        Scanner std = new Scanner(new File (newPoints));
        std.useDelimiter(",");
        while (std.hasNextLine()) {
            String a = std.next();
            int index = City.indexOf(a);
            if (index != -1) {
                double b = std.nextDouble();
                double c = std.nextDouble();
                if(index> stations.size()) {
                    stations.add(new Coordinates(b,c));
                }
                stations.add(index, new Coordinates(b,c));
            }    
            std.nextLine();
        }
        std.close();
    }
    //***************************************
    //Part of the editing stage
    //***************************************
    private static void findStations () {
        while(true) { 
            StdDraw.picture(0, 0, map);
            StdDraw.text(600, -200, StdDraw.mouseX()+","+StdDraw.mouseY());  
            StdDraw.show(100);
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
    }
    //***************************************
    //Part of the editing stage
    //***************************************
    private static void editStations () throws FileNotFoundException {
        while (true) {
            drawBackground();
            for (int i = 0; i < stations.size(); i++) {
                if (StdDraw.mouseX() <= stations.get(i).getX() + 10 && StdDraw.mouseX() >= stations.get(i).getX() - 10
                        &&StdDraw.mouseY() <= stations.get(i).getY() + 10 && StdDraw.mouseY() >= stations.get(i).getY() - 10) {
                    while (StdDraw.mousePressed()) {
                        stations.set(i, new Coordinates (StdDraw.mouseX(),StdDraw.mouseY()));
                    }
                }
            }
            StdDraw.show(100);
            if (StdDraw.isKeyPressed(KeyEvent.VK_1)) {
                break;
            }
        }
        PrintWriter stn = new PrintWriter("Amtrak Coordinates.txt");
        for (int i = 0; i < City.size(); i++) {
            stn.print(City.get(i)+",");
            stn.print(stations.get(i).getX()+",");
            stn.print(stations.get(i).getY()+",");
            stn.println();
        }
        stn.close();
    }
  //***************************************
    //Draws the Background
    //***************************************
    public static void drawBackground () throws FileNotFoundException {
        StdDraw.clear();
        StdDraw.picture(0, 0, map);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.setPenRadius(.01);  
        drawLines();
        drawStation();
        
    }
    //***************************************
    //Reads the connections between points
    //***************************************
    private static void readLines (String name) throws FileNotFoundException {
        lines.clear();
        Scanner std = new Scanner(new File (name));
        std.useDelimiter(",\\s*");
        while (std.hasNextLine()) {
            StdDraw.setPenColor(StdDraw.ORANGE);
            String a = std.next(), b=std.next();
            int first = City.indexOf(a);
            int second = City.indexOf(b);
            lines.add(stations.get(first));
            lines.add(stations.get(second));
            std.nextLine();
        }
        std.close();
    }
    //***************************************
    //Part of the editing stage
    //***************************************
    private static void editLines () throws FileNotFoundException { 
        int i = 0, count = 0; 
        ArrayList<Coordinates> points = new ArrayList<Coordinates>();
        while (true) {            
            drawBackground();
            while (StdDraw.mousePressed()) {
                if (i==0) {
                    points.add(new Coordinates(StdDraw.mouseX(),StdDraw.mouseY()));
                    i++;
                }
            }
            if (i != 0) {
                points.add(new Coordinates(StdDraw.mouseX(),StdDraw.mouseY()));
                lines.add(points.get(count));
                count = points.size()-1;
                lines.add(points.get(count));            
                i = 0;
                if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
                    break;
                }
            }
            StdDraw.show(1);
        }
        
        PrintWriter stn = new PrintWriter("Line.txt");
        for (int j = 0; j < lines.size(); j+=2) {
            stn.print(lines.get(j).getX()+",");
            stn.print(lines.get(j).getY()+",");
            stn.print(lines.get(j+1).getX()+",");
            stn.print(lines.get(j+1).getY()+",");
            stn.println();
        }
        stn.close();
    }
    //***************************************
    //Draws the train lines
    //***************************************
    private static void drawLines () {
        for (int i = 0; i < lines.size(); i+=2 ) {
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(lines.get(i).getX(), lines.get(i).getY(),
                        lines.get(i+1).getX(), lines.get(i+1).getY());
            StdDraw.setPenRadius(.003);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.line(lines.get(i).getX(), lines.get(i).getY(),
                        lines.get(i+1).getX(), lines.get(i+1).getY());
        }
    }
    //***************************************
    //Draws the train
    //***************************************
    public static void drawTrain (Train a) {
        StdDraw.setPenRadius(.01);        
        StdDraw.setPenColor(a.getColor());
        StdDraw.filledRectangle(a.getCoordinates().getX(), a.getCoordinates().getY(), 20, 10);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(a.getCoordinates().getX(), a.getCoordinates().getY(), Integer.toString(a.getID()), 45);

    }
    //***************************************
    //Reads all the information for the animation
    //***************************************
    public static void readSchedule (String filename) throws IOException {
        int i = 0;
        Scanner stdinput = new Scanner(Paths.get(filename));
        LinkedList<Edge> edges = new LinkedList<>();
        stdinput.useDelimiter(",\\s*"); 
        while (stdinput.hasNextLine()) {   
            edges.clear();
            Scanner stdinput1 = new Scanner(stdinput.nextLine());
            stdinput1.useDelimiter(",\\s*");
            int id = stdinput1.nextInt();
            int speed = stdinput1.nextInt();
            int depart = stdinput1.nextInt();
            int arrival = stdinput1.nextInt();
            int type = stdinput1.nextInt();
            ArrayList<Delay> delay = new ArrayList<Delay>();
            String a = stdinput1.next();
            if (a.contains("[")) {
                String e = stdinput1.next();
                String f = stdinput1.next();
                if(f.contains("]")) {
                    f = f.substring(0, f.indexOf("]"));
                }
                edges.add(new Edge(new Vertex(a.substring(1)), new Vertex(e), Integer.parseInt(f)));
                while (stdinput1.hasNext()) {                    
                    String b = stdinput1.next();
                    if (b.equals("[]")) {
                        break;
                    }
 
                    String c = stdinput1.next();
                    String d = stdinput1.next();                    
                    
                     if (d.contains("]")) {
                         d = d.substring(0, d.indexOf("]"));
                         edges.add(new Edge(new Vertex(b), new Vertex(c), Integer.parseInt(d)));
                         break;
                     }else {
                         edges.add(new Edge(new Vertex(b), new Vertex(c), Integer.parseInt(d)));
                     }
                     
                }
                
            }
            //Adds delays
            while (stdinput1.hasNext()) {
                String b = stdinput1.next();   
                if(b.length() > 2) {
                    if (b.contains("]") && b.contains("[")) {
                        delay.add(new Delay(b.substring(1, b.lastIndexOf(" ")), Integer.parseInt(b.substring(b.lastIndexOf(" ")+1, b.indexOf("]")))));
                    }else if (b.contains("[")){
                        delay.add(new Delay(b.substring(1, b.lastIndexOf(" ")), Integer.parseInt(b.substring(b.lastIndexOf(" ")+1))));                                     
                    }else if (b.contains("]")){
                        delay.add(new Delay(b.substring(0, b.lastIndexOf(" ")), Integer.parseInt(b.substring(b.lastIndexOf(" ")+1, b.indexOf("]")))));   
                    }
                }
                
            }    
            for (int j = edges.size() - 1; j > 0; j--) {
                edges.get(j).setWeight(edges.get(j).getWeight() - edges.get(j - 1).getWeight());
            }
            //Initializes trains
            trains.add(new Train(edges.getFirst().getVertexOne().getID(),edges.getLast().getVertexTwo().getID(),
                   depart, id, type));      
            trains.get(i).setSpeed(speed);
            trains.get(i).setRoute(edges);
            trains.get(i).setCurrentEdge(edges.get(0));
            trains.get(i).setRandomColor();
            trains.get(i).setCoordinates(stations.get(City.indexOf(edges.get(0).getVertexOne().getID())));
            trains.get(i).setDelays(delay);
            trains.get(i).setWaitLimit(0);
            trains.get(i).setActualArrivalTime(arrival);
            stdinput1.close();
            i++;
        }
        
        stdinput.close();
    }
    //***************************************
    //Moves the train 
    //***************************************
    public static void move (Train running) {
        
       Coordinates a = new Coordinates(running.getCoordinates().getX(), running.getCoordinates().getY());
       Coordinates first = stations.get(City.indexOf(running.getCurrentEdge().getVertexOne().getID()));
       Coordinates second = stations.get(City.indexOf(running.getCurrentEdge().getVertexTwo().getID())); 
       Coordinates ratio = new Coordinates(second.subtract(first).getX(), second.subtract(first).getY());
       
       double t = running.getCurrentEdge().getWeight() / running.getSpeed();
       ratio.divide(t);
       a.add(ratio);
       
       if(t <= ticks + 1 && t >= ticks-1) {
           Coordinates b = stations.get(City.indexOf(running.getRoute().getFirst().getVertexTwo().getID()));
           running.setCoordinates(new Coordinates(b.getX(), b.getY()));
       }else if (running.getActualArrivalTime() == ticks) {
           Coordinates b = stations.get(City.indexOf(running.getRoute().getLast().getVertexTwo().getID()));
           running.setCoordinates(new Coordinates(b.getX(), b.getY()));
       }else {
           running.setCoordinates(new Coordinates(a.getX(), a.getY()));
       }      
       
       for (int j = 0; j < running.getRoute().size(); j++) {
           String start = running.getRoute().get(j).getVertexOne().getID();
           String end = running.getRoute().get(j).getVertexTwo().getID();
           StdDraw.setPenColor(running.getColor());
           drawCurrent(stations.get(City.indexOf(start)),stations.get(City.indexOf(end)));
       }
       drawTrain(running);
    }
    //***************************************
    //Highlights stations that trains have arrived in
    //***************************************
    public static void atStation (Coordinates stop) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setPenRadius(.05);
        StdDraw.point(stop.getX(), stop.getY());        
    }
    
    public static void drawCurrent (Coordinates start, Coordinates end) {
        StdDraw.setPenRadius(.01);
        StdDraw.line(start.getX(), start.getY(),
                    end.getX(), end.getY());
        StdDraw.setPenRadius(.003);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.line(start.getX(), start.getY(),
                    end.getX(), end.getY());
    }

    public static void printDelay(ArrayList<Train> delayed) {
        StdDraw.setPenColor(StdDraw.BLACK);
        double x = -690; double y = 0;
        for (int i = 0; i < delayed.size(); i++) {
            String station = delayed.get(i).getDelays().get(0).getStation();
            int time = delayed.get(i).getDelays().get(0).getTime();;
            y = -370 + (20*i);
            StdDraw.textLeft(x, y, "Train "+delayed.get(i).getID()+" was delayed at "+station+
                   " for "+time+" ticks" );
        }
    }
    
    public static void animation () throws IOException {
        
        readSchedule("amtrak test case animation data.txt");
        ArrayList<Train> active = new ArrayList<Train>();
        ArrayList<Train> delayed = new ArrayList<Train>();
        StdDraw.show(1);
        while (true) {
            drawBackground();
            
            //Adds trains to the trains being drawn if it's meant to leave
            for (int i = 0; i < trains.size(); i++) {
                if (trains.get(i).getDepatureTime() == ticks) {
                    active.add(trains.get(i));
                    trains.remove(i);
                    i--;
                }
            }
            
            //Draws trains on the field
            for (int i = 0; i < active.size(); i++) {
                StdDraw.textRight(650, 340, "Time: "+Integer.toString(ticks));         
                //Checks Delays
                if (active.get(i).getDelays().isEmpty()) {
                    move(active.get(i));
                }else {
                    String delay = active.get(i).getDelays().get(0).getStation();
                    if (active.get(i).getCoordinates().isEqualTo(stations.get(City.indexOf(delay)))) {
                        if (active.get(i).getWaitingTime() == 0) {
                            active.get(i).setWaitCount(ticks);                            
                        }
                        active.get(i).setWaitingTime(active.get(i).getWaitingTime()+1);
                        
                        StdDraw.setPenRadius(.05);        
                        StdDraw.setPenColor(active.get(i).getColor());
                        StdDraw.point(active.get(i).getCoordinates().getX(), active.get(i).getCoordinates().getY());
                        if (delayed.indexOf(active.get(i)) == -1) {
                            delayed.add(active.get(i));
                        }
                        
                        printDelay(delayed);

                        if (active.get(i).getWaitingTime() == active.get(i).getWaitCount() + active.get(i).getDelays().get(0).getTime()) {
                            move(active.get(i));
                            active.get(i).getDelays().remove(0);
                            active.get(i).setWaitingTime(0);
                            delayed.remove(active.get(i));
                        }
                    }else {
                        move(active.get(i));
                    }    
                }                        
                //Checks if the train is at a stop
                String stop = active.get(i).getCurrentEdge().getVertexTwo().getID();                
                if (active.get(i).getCoordinates().isEqualTo(stations.get(City.indexOf(stop)))) {
                     atStation(stations.get(City.indexOf(stop)));
                     active.get(i).setCoordinates(stations.get(City.indexOf(stop)));
                     active.get(i).getRoute().removeFirst();                         
                     if (active.get(i).getRoute().size() >= 1) {
                         active.get(i).setCurrentEdge(active.get(i).getRoute().getFirst());     
                     }else {
                         active.remove(i);
                         i--;
                     }
                }
                //Checks if the simulation is over
                if (trains.isEmpty() && active.isEmpty()) {
                    StdDraw.clear();
                    StdDraw.text(0, 0, "Simulation Over");
                    return;
                }
            }
            ticks++;
            StdDraw.show(1);
        }
    }

}
                
            
