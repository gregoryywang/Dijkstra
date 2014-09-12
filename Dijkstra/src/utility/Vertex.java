package utility;

/**
 * Written by Ed Hong UWT Feb. 19, 2003.
 * Modified by Donald Chinn May 14, 2003.
 * Modified by Donald Chinn December 11, 2003.
 * Modified by Yong Yu Wang September 12, 2014
 */

import java.util.*;

/**
 * Class that represents a vertex in a graph.
 * A name (usually a string, but it can be an arbitrary object)
 * can be associated with the vertex.
 * 
 * Data (also represented by an object (e.g., a string)) can also be
 * associated with a vertex.  This could be useful, for example, if you
 * need to mark a vertex as being visited in some graph traversal.
 * 
 * @author edhong
 * @version 0.0
 */
public class Vertex {
    /** the edge list for this vertex */
    LinkedList<Edge> incidentEdgeList;

    private Object data;              // an object associated with this vertex
    private Object name;              // a name associated with this vertex
    
    /**
     * Constructor that allows data and a name to be associated
     * with the vertex.
     * @param data     an object to be associated with this vertex
     * @param name     a name to be associated with this vertex
     */
    public Vertex(Object data, Object name) {
        this.data = data;
        this.name = name;
        this.incidentEdgeList = new LinkedList<Edge>();
    }
    
    /**
     * Return the name associated with this vertex.
     * @return  the name of this vertex
     */
    public Object getName(){
        return this.name;
    }
    
    /**
     * Return the data associated with this vertex.
     * @return  the data of this vertex
     */
    public Object getData() {
        return this.data;
    }
    
    /**
     * Set the data associated with this vertex.
     * @param data  the data of this vertex
     */
    public void setData(Object data) {
        this.data = data;
    }
}
