
public class Coordinates {

    private double x,y;
    public Coordinates (double a, double b) {
        x = a;
        y = b;
    }
   public double getX() {
       return x;
   }
   
   public double getY() {
       return y;
   }
   
   public void setX(double a) {
       x = a;
   }
   
   public void setY(double a) {
       y = a;
   }
   
   public Coordinates subtract (Coordinates a) {
       return new Coordinates(x - a.getX(), y - a.getY());
   }
   
   public void divide (double t) {
       x = x/t;
       y = y/t;
   }
   
   public void add (Coordinates a) {
       x += a.getX();
       y += a.getY();
   }
   
   public boolean isEqualTo (Coordinates a) {
       if (x <= a.getX() + 10 && x >= a.getX() - 10 && y <= a.getY() + 10 && y >= a.getY() - 10) {
           return true;
       }
       return false;
   }
}
