Dijkstra
========

A basic Java shortest path finder using Dijkstra's algorithm.

__Authors:__ Yong Yu Wang and Patrick Colowick-Harbour<br />
__License:__ [MIT](http://opensource.org/licenses/MIT)<br/>
__Status:__ Stable<br />
__Version:__ 1.0

###Developement Notes

The basic structure of this graph is represented by the Vertex, Edge, and SimpleGraph class files. We designed the Dijkstra class that we create to work with these classes. The first major design element that we implemented is the structures that will create a functioning binary heap. We use a pre-existing BinaryHeap class to do this and make a few minor modifications to the code so that it supports some of the properties that are unique to Dijkstra. We already have vertices that we need to store into the heap by weight, but since we cannot modify the Vertex class we need to create a new data structure, named DijkstraNode, to store a vertex, it’s weight, its location in the heap, as well as a path pointer to a vertex which will come useful when we want to find the shortest path. Next, we were tasked in designing a workable model that will closely follow the pencil and paper implementation of the dijkstra algorithm commenly depicted in textbooks. We know that if we have a vertex, we can all vertices adjacent to this vertex by getting a list of all edges that are incident to this vertex and finding the vertex at the opposite end of this edge. But once we get these adjacent vertices, where do we go next? 

This is where we encountered the first major problem in our design. The DijkstraNode have the ability to store vertex information as part of its data, but there was no way to go the other direction and obtain a vertex’s corresponding DijkstraNode in order to change its properties in the heap. One of the early ideas that we implemented is the creation of another data structure, a TreeMap named VertexLocation. This data structure contained a mapping between a Vertex object and the index location of the DijkstraNode that contains this vertex in the heap. At this phase of the design, we were going to initialize all vertices on the graph with DijkstraNodes with a default weight of double POSITIVE_INFINITY and insert them into the heap at the beginning of the program, along with the origin vertex with weight 0 whose DijkstraNode would naturally be at the very top of the minheap. When the looping portion of the dijkstra algorithm begins, we would call deleteMin and get the node with the smallest weight and store it in an ArrayList of known vertices which represented the shortest path. We would then call the method that would update the weight of all vertices adjacent to the vertex in this node, which will perform the “relaxation” process in the dijkstra algorithm. We would continue doing this until either the destination vertex is encountered and assigned a weight, which signified the end of the algorithm, or when a DijkstraNode returned by deleteMin had a weight of POSITIVE_INFINITY, which signified that there does not exist a path between the origin and the destination. 

The main design flaw in this original implementation is that it was extraordinary cumbersome and created a major headache when it comes writing code associating Vertex objects with their corresponding DijkstraNodes, since it was perceived that there was no direct way to find the DijkstraNode that belongs to a Vertex. Utilizing the TreeMap solves this problem, added more problems that had to be resolved in an extremely unintuitive way. For example, since the Vertex class did not have a comparator class that TreeMap could use to store the vertices, we had to write our own comparator class that sorted Vertex objects by name. 
On Monday night, once we were presented in the proper way of how to associate Vertex with DijkstraNodes, everything become clearer. It turns out that we can utilize the data object field of each Vertex object to store its corresponding DijkstraNode, which gives us the ability to go between Vertex objects and DijkstraNodes without using complicated data structures such as the TreeMap. Also, because this made performing everything else in our program easier, we determined that it was both inefficient and incorrect in our decision to assign every vertex with a DijkstraNode with an infinite weight and insert the all into the heap in the beginning. Instead, the heap should only store DijkstraNodes of vertices that have a real weight value as calculated when we encounter these vertices in the adjacency list.

Due to the design errors we initially made, we had to rewrite approximately eighty percent of the original code but the subsequent result is both simpler, more efficient, and easy to understand. It is interesting to note though that the thought process that inspired our original implementation was due to a misunderstanding in the design of the Vertex class, as it was initially thought that the data field of the Vertex object was reserved to store data information belonging to the Vertex itself and should not be utilized as a component in the dijkstra’s shortest path algorithm. If this assumption was true and we cannot utilize this field as part of our code, then the design of this assignment would be much harder to implement and a design like what we thought of at the beginning may be warranted.

The main lesson that we learned is that code that is based on a well-constructed design is both easier to implement, more efficient, more intuitive, and basically all around better than code based on designs that simply does not “get it”. We found this out the hard way when we tried to implement a design that simply did not utilize the most efficient way of coding dijkstra’s algorithm and paid the price in the form of ten hours’ worth of wasted work on something was unnecessary. Perhaps this lesson is as important as any when it comes to the field of programming, as it demonstrates the true importance of why learning algorithms and data structures is critical to one’s success in software engineering.  
