
public class Delay implements Comparable<Delay> {

	private String station;
	private int time;
	
	public Delay(final String station, final int time) {
		this.station = station;
		this.time = time;
	}
	
	@Override
	public int compareTo(Delay delay) {
		
		return Integer.compare(time, delay.getTime());
	}
	
	public boolean equals(final Delay delay) {
		return ((this.station.equals(delay.getStation())) && (this.time == delay.getTime()));
	}
	
	@Override
	public String toString() {
		return this.station + " " + this.time;
	}
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
