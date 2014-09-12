package pathfinder;
/**
 * Dijkstra.Java
 * TCSS 343 Fall 2014 Homework 6B
 * Searches for the shortest path between a origin and a destination vertex in a graph
 * @author Patrick Colowick-Harbour
 * @author Yong Yu Wang
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import utility.Edge;
import utility.GraphInput;
import utility.KeyboardReader;
import utility.SimpleGraph;
import utility.Vertex;

public class Dijkstra {
	
	static BinaryHeap dijkstraHeap = new BinaryHeap();
	static Set<Vertex> visitedVertexes = new HashSet<>();
	
	/**
	 * The main function, starts the program by calling start()
	 * @param args
	 */
	public static void main(String [] args) {
	    // create SimpleGraph ADT
        SimpleGraph graph = new SimpleGraph();
        // create GraphInput object
        GraphInput input = new GraphInput();
        
        /**
         * Loads graph data from text file, creates graph structure and stores data into SimpleGraph and Hashtable.
         * SimpleGraph contains linked lists of graph vertex and edges.
         * Hashtable associates vertex objects with their names.
         */
        Hashtable table = input.LoadSimpleGraph(graph);
		String response = "y";
		// loops the program as long as user wants to
		while(response.toLowerCase().equals("y")) {
		    
		    // Remove any data from previous runs
		    for (Object o : table.values()) {
		        Vertex v = (Vertex) o;
	            v.setData(null);
		    }
		    
			// gets the ball rolling
			start(graph, table);
			System.out.print("Continue? (y/n): ");
			response = KeyboardReader.readString();
			while(!response.toLowerCase().equals("y") && !response.toLowerCase().equals("n")) {
				System.out.print("Please enter y or n: ");
				response = KeyboardReader.readString();
			}
		}
	}
	
	/**
	 * Asks user to input two points in the graph for Dijkstra to compute the shortest path.
	 * @param graph	The SimpleGrapth ADT that contains the graph data
	 * @param table	The hashtable mapping names to vertices
	 */
	public static void start(SimpleGraph graph, Hashtable table) {
		
		// asks user to input starting vertex
		System.out.print("Enter the name of the origin: ");
		String originName = KeyboardReader.readString();
        Vertex originVertex = (Vertex) table.get(originName);
        // checks to see of vertex actually exists, repeats prompt if it does not
        while (originVertex == null) {
        	System.out.print("That name does not exist. Try again: ");
        	originName = KeyboardReader.readString();
            originVertex = (Vertex) table.get(originName);
        }
        
        // asks user to input ending vertex
		System.out.print("Enter the name of the destination: ");
		String destinationName = KeyboardReader.readString();
        Vertex destinationVertex = (Vertex) table.get(destinationName);
        // checks to see of vertex actually exists, repeats prompt if it does not
        while (destinationVertex == null) {
        	System.out.print("That name does not exist. Try again: ");
        	destinationName = KeyboardReader.readString();
            destinationVertex = (Vertex) table.get(destinationName);
        }
        
        // find the shortest path between the two vertices using the SimpleGraph ADT
        findDijkstra(originVertex, destinationVertex, graph);
	}
	
	/**
	 * The main subroutine to the Dijkstra Algorithm. 
	 * Given a starting vertex and an ending vertex, find the shortest path between these
	 * vertices and output a list of vertices in the shortest path, as well as total distance
	 * @param start	the starting vertex in the path
	 * @param end	the destination vertex in the path
	 * @param graph	the SimpleGraph ADT containing all the vertex and edge data
	 */
	public static void findDijkstra(Vertex start, Vertex end, SimpleGraph graph) {
	    dijkstraHeap = new BinaryHeap();
		// insert starting vertex into minheap with weight 0
		DijkstraNode initial = new DijkstraNode(start, 0);
		start.setData(initial);
		dijkstraHeap.insert(initial);
		boolean foundPath = false;

		while (!dijkstraHeap.isEmpty()) {
			try {
				// get the node that has the minimum weight from the heap
				DijkstraNode pickedNode = (DijkstraNode) dijkstraHeap.deleteMin();
				// get the vertex this node belongs to
				Vertex pickedVertex = pickedNode.getVertex();
				// if the picked vertex is the destination, then we have found a shortest path
				if (pickedVertex.equals(end)) {
				    foundPath = true;
				    break;
			    }
				// update all the weights of vertices adjacent to this vertex, looks to see if destination vertex is among them
				updateVertices(pickedVertex, end, graph);
				// DEBUG
				// System.out.println("Vertex path " + pickedVertex.getName());
			} catch (EmptyHeapException e) {
				System.out.println ("Datastructure error: tried to delete from an empty heap.");
				System.out.println("In other words, there is no path from the origin to the destination.");
				break;
			}
		}
		
		if (foundPath) {
			// We have found the path to the destination vertex. Update the path to the destination.
			printShortestPath(start, end);
		} else {
		    System.out.println("No path found from " + start.getName() + " to " + end.getName());
		}
	}
	
	/**
	 * Updates the weight of all vertices adjacent to the initial vertex. 
	 * If the adjacent vertex is not in the heap, assign it the calculated weight and add to heap.
	 * Otherwise, update the its weight in the heap if new weight is smaller.
	 * @param initial	the starting vertex to find adjacent vertices to update
	 * @param graph		The SimpleGraph ADT used to locate such adjancies
	 * @return flag		true if destination vertex is adjacent to this initial vertex so we can stop dijkstra,
	 * 					false otherwise.
	 */
	public static boolean updateVertices(Vertex initial, Vertex end, SimpleGraph graph) {
		
		// assumes that we have not seen destination vertex yet
		boolean improvedPath = false;
		
		// get the weight of the origin vertex
		double initialWeight = ((DijkstraNode) initial.getData()).getWeight();
		
		Iterator i;
		Edge e;
		// traverses all edges incident to initial vertex and finds all adjacent vertices
		for (i = graph.incidentEdges(initial); i.hasNext();) {
			// gets next incident edge
			e = (Edge) i.next();
			// calculates new weight by adding the weight of this edge to weight of origin vertex
			double newWeight = initialWeight + (Double) e.getData();
			// get a vertex adjacent to initial using edge
			Vertex adjacentVertex = graph.opposite(initial, e);
			
			// scenario 1: vertex is not in the heap yet 
			if (adjacentVertex.getData() == null) {
				// creates a new DijkstraNode with updated weight value
				DijkstraNode newNode = new DijkstraNode(adjacentVertex, newWeight);
				// store the DijkstraNode into the vertex
				adjacentVertex.setData(newNode);
				// add the DijkstraNode into the heap
				dijkstraHeap.insert(newNode);
				newNode.setPath(initial);
				// DEBUG
				//System.out.println(adjacentVertex.getName() + " path set to " + initial.getName());
			}
			// scenario 2: vertex has a DijkstraNode in the heap with a real weight to update
			else {
				// gets the DijkstraNode associated with this vertex in the heap
				DijkstraNode currentNode = (DijkstraNode) adjacentVertex.getData();
				// if new weight is smaller than current weight, make new weight the weight of this vertex
				if (currentNode.getWeight() > newWeight) {
					currentNode.setWeight(newWeight);
					// update the heap with updated weight
					dijkstraHeap.decreaseKey(currentNode.getHeapLocation());
					currentNode.setPath(initial);
					// DEBUG
					// System.out.println(adjacentVertex.getName() + " path set to " + initial.getName());
				}
			}
		}
		return improvedPath;
	}
	
	/**
	 * Traverses the path pointers from the destination vertex back to the origin vertex
	 * and print out the shortest path between these two points, as well as total distance.
	 * @param origin	the starting vertex in the path
	 * @param destination	the ending vertex in the path
	 */
	public static void printShortestPath(Vertex origin, Vertex destination) {
		Vertex temp = destination;
		Stack<Vertex> pathway = new Stack<>();
		while(!temp.equals(origin)) {
			pathway.push(temp);
			temp = ((DijkstraNode) temp.getData()).getPath();
		}
		pathway.push(origin);
		
		System.out.print("The shortest path goes through these cities: ");
		while (!pathway.isEmpty()) {
			System.out.print(pathway.pop().getName() +  " ");
		}
		System.out.println("\nThe total distance of this path is " + 
                ((DijkstraNode)destination.getData()).getWeight());
	}
}
