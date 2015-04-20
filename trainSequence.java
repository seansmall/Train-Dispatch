class trainSequence implements Comparable<Object>{
	private String source;
	private String destination;
	private int depatureTime;
	private int type;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
	    this.source = source;
	}

	public String getDestination() {
	    return destination;
	}

	public void setDestination(String destination) {
	    this.destination = destination;
	}

	public int getDepatureTime() {
	    return depatureTime;
	}

	public void setDepatureTime(int depatureTime) {
		this.depatureTime = depatureTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int compareTo(Object anotherTrain) throws ClassCastException {
		if (!(anotherTrain instanceof trainSequence))
			throw new ClassCastException("A trainSequence object expected.");
		int anotherTrainDepTime = ((trainSequence) anotherTrain).getDepatureTime();  
		return this.depatureTime - anotherTrainDepTime;    
	}
}
