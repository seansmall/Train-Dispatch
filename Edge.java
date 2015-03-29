
public class Edge {

	private Vertex vertexOne;
	
	private Vertex vertexTwo;
	
	private int weight;
	
	public Edge(final Vertex one, final Vertex two, final int weight) {
		this.vertexOne = one;
		this.vertexTwo = two;
		this.weight = weight;
	}

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
