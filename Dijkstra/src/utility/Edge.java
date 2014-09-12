package utility;

/*
 * Written by Ed Hong UWT Feb. 19, 2003.
 * Modified by Donald Chinn May 14, 2003.
 * Modified by Donald Chinn December 11, 2003.
 * Modified by Donald Chinn February 28, 2004. */

/**
 * Class that represents an edge in a graph.
 * An object (usually some sort of data) can be associated with the edge.
 * 
 * A label (also represented by an object (e.g., a string) can also be
 * associated with an edge.  This could be useful, for example, if you
 * need to mark an edge as being visited in some graph traversal.
 * 
 * @author edhong
 * @version 0.0
 */
public class Edge {
    /** the first endpoint of the edge */
    private Vertex v1;
    
    /** the second endpoint of the edge */
    private Vertex v2;
    
    private Object data;  // an object associated with this edge
    private Object name;  // a name associated with this edge
    
    /**
     * Constructor that allows data and a name to be associated
     * with the edge.
     * @param v     the first endpoint of this edge
     * @param w     the second endpoint of this edge
     * @param data  data to be associated with this edge
     * @param name  a name to be associated with this edge
     */
    public Edge (Vertex v, Vertex w, Object data, Object name) {
        this.data = data;
        this.name = name;
        this.v1 = v;
        this.v2 = w;
    }

    /**
     * Return the first endpoint of this edge.
     * @return  the first endpoint of this edge
     */
    public Vertex getFirstEndpoint() {
        return this.v1;
    }

    /**
     * Return the second endpoint of this edge.
     * @return  the second endpoint of this edge
     */
    public Vertex getSecondEndpoint() {
        return this.v2;
    }

    /**
     * Return the data associated with this edge.
     * @return  the data of this edge
     */
    public Object getData() {
        return this.data;
    }
        
    /**
     * Set the data associated with this edge.
     * @param data  the data of this edge
     */
    public void setData(Object data) {
        this.data = data;
    }
    
    /**
     * Return the name associated with this edge.
     * @return  the name of this edge
     */
    public Object getName() {
        return this.name;
    }
    
}
