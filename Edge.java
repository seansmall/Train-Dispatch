
public class Edge {

	// the two nodes that this edge connects
	private Vertex vertexOne;
	private Vertex vertexTwo;
	// the weight of the edge between vertex  one and vertex two
	private int weight;
	
	public Edge(final Vertex one, final Vertex two, final int weight) {
		this.vertexOne = one;
		this.vertexTwo = two;
		this.weight = weight;
	}

	@Override
	public String toString() {
		// return a string with the id's of both vertices and the weight between
		return vertexOne.getID() + "," + vertexTwo.getID() + "," + weight;
	}
	
	public boolean equals(final Edge e){
		// if both edges share the same vertices in any order and the same wieght then they are equal
		return (((e.getVertexOne().equals(vertexOne))||(e.getVertexOne().equals(vertexTwo)))
				&& ((e.getVertexTwo().equals(vertexTwo)) || (e.getVertexTwo().equals(vertexOne)))
				&& (e.getWeight() == weight));
	}
	
	//////////////   Get/Set methods   ///////////////////////
	public Vertex getVertexOne() {
		return vertexOne;
	}

	public Vertex getVertexTwo() {
		return vertexTwo;
	}

	public int getWeight() {
		return weight;
	}

	public void setVertexOne(Vertex vertexOne) {
		this.vertexOne = vertexOne;
	}

	public void setVertexTwo(Vertex vertexTwo) {
		this.vertexTwo = vertexTwo;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
