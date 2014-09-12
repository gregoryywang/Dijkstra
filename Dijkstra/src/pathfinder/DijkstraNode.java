package pathfinder;

import utility.Vertex;

/**
 * DijkstraNode.java
 * A node which stores vertex and weight infomation that the heap can use.
 * Also stores heap location infomation for updating weights and a path pointer linking to
 * the previous node in the shortest path if this node is part of this path.
 * @author Patrick Colowick-Harbour
 * @author Yong Yu Wang
 */
public class DijkstraNode implements Comparable<DijkstraNode> {
	/**
	 * The current weight of the vertex
	 */
    private double weight;
    
    /**
     * The vertex object this node contains
     */
    private Vertex vertex;
    
    /**
     * The previous vertex linked to this vertex in the shortest path
     */
    private Vertex path;
    
    /**
     * The index where this node is stored in the heap array
     */
    private int heapLocation;
    
    /**
     * Constructor.
     * @param vertex	the vertex that belongs to this node
     * @param weight   the weight of the vertex
     * @param heapLocation     where this node is located in the heap
     */
    public DijkstraNode (Vertex vertex, double weight) {
        this.weight = weight;
        this.vertex = vertex;
    }
    
    /**
     * Gets the weight of the vertex in this node.
     * @return weight of this vertex
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Gets the index this node in the array implemented in the heap.
     * @return the array index of this node
     */
    public int getHeapLocation() {
        return heapLocation;
    }
    
    /**
     * Gets the vertex associated with this node
     * @return vertex associated with this node
     */
    public Vertex getVertex() {
    	return vertex;
    }
    
    /**
     * Gets the last vertex in the shortest path linked to this vertex, if this
     * vertex belongs in the shortest path.
     * @return path	the last linked vertex
     */
    public Vertex getPath() {
    	return path;
    }
    
    /**
     * Sets the weight of the vertex in this node to a new value.
     * @param newWeight	the new weight of this vertex
     */
    public void setWeight(double newWeight){
    	this.weight = newWeight;
    }
    
    /**
     * Sets the location of this node in the heap array to a new index.
     * @param newHeapLocation	the new index value of this node
     */
    public void setHeapLocation(int newHeapLocation){
    	this.heapLocation = newHeapLocation;
    }
    
    /**
     * Sets the path pointer of the last vertex linked to this vertex to a new vertex pointer.
     * @param last	the previous vertex joining this vertex in the minimum path
     */
    public void setPath(Vertex last) {
    	this.path = last;
    }
    
    /**
     * Comparator that the heap uses to store each node in the heap in order.
     * Uses the value of the weight of each node as a basis for comparison.  
     * @param other	node to compare this node to
     * @return whether the weight of this node is bigger, smaller, or equal to the other node
     * @override	
     */
	public int compareTo(DijkstraNode other) {
		if (this.weight < other.weight) {
            return -1;
        } else if (this.weight == other.weight) {
            return 0;
        } else {
            return 1;
        }
	}

}