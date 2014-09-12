package pathfinder;
/**
 * A binary minheap of comparable objects.
 * Modified by Yong Yu Wang to support updating the weight of DijkstraNodes
 * @author Donald Chinn
 * @author yongyuwang
 * @version September 19, 2003
 */
public class BinaryHeap {
    
    /* the heap is organized using the implicit array implementation.
     * Array index 0 is not used
     */
    private Comparable[] elements;
    private int size;       // index of last element in the heap
    
    // Constructor
    public BinaryHeap() {
        int initialCapacity = 10;
        
        this.elements = new Comparable[initialCapacity + 1];
        this.elements[0] = null;
        this.size = 0;
    }
    
    
    /**
     * Constructor
     * @param capacity  number of active elements the heap can contain
     */    
    public BinaryHeap(int capacity) {
        this.elements = new Comparable[capacity + 1];
        this.elements[0] = null;
        this.size = 0;
    }
    
    
    /**
     * Given an array of Comparables, return a binary heap of those
     * elements.
     * @param data  an array of data (no particular order)
     * @return  a binary heap of the given data
     */
    public static BinaryHeap buildHeap(Comparable[] data) {
        BinaryHeap newHeap = new BinaryHeap(data.length);
        for (int i = 0; i < data.length; i++) {
            newHeap.elements[i+1] = data[i];
        }
        newHeap.size = data.length;
        for (int i = newHeap.size / 2; i > 0; i--) {
            newHeap.percolateDown(i);
        }
        return newHeap;
    }


    /**
     * Determine whether the heap is empty.
     * @return  true if the heap is empty; false otherwise
     */
    public boolean isEmpty() {
        return (size < 1);
    }
    
    
    /**
     * Insert an object into the heap.
     * @param key   a key
     */
    public void insert(Comparable key) {

        if (size >= elements.length - 1) {
            // not enough room -- create a new array and copy
            // the elements of the old array to the new
            Comparable[] newElements = new Comparable[2*size];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        
        size++;
        elements[size] = key;
        // saves the location of the key in the heap into the node
        ((DijkstraNode) key).setHeapLocation(size);
        percolateUp(size);
    }
    
    
    /**
     * Remove the object with minimum key from the heap.
     * @return  the object with minimum key of the heap
     */
    public Comparable deleteMin() throws EmptyHeapException {
        if (!isEmpty()) {
            Comparable returnValue = elements[1];
            elements[1] = elements[size];
            size--;
            percolateDown(1);
            return returnValue;
            
        } else {
            throw new EmptyHeapException();
        }
    }
    
    
    /**
     * Given an index in the heap array, percolate that key up the heap.
     * @param index     an index into the heap array
     */
    private void percolateUp(int index) {
        Comparable temp = elements[index];  // keep track of the item to be moved
        while (index > 1) {
            if (temp.compareTo(elements[index/2]) < 0) {
                elements[index] = elements[index/2];
                // saves the location of the key in the heap into the node
                ((DijkstraNode) elements[index]).setHeapLocation(index);
                index = index / 2;
            } else {
                break;
            }
        }
        elements[index] = temp;
        // saves the location of the key in the heap into the node
        ((DijkstraNode) elements[index]).setHeapLocation(index);
    }
    
    
    /**
     * Given an index in the heap array, percolate that key down the heap.
     * @param index     an index into the heap array
     */
    private void percolateDown(int index) {
        int child;
        Comparable temp = elements[index];
        
        while (2*index <= size) {
            child = 2 * index;
            if ((child != size) &&
                (elements[child + 1].compareTo(elements[child]) < 0)) {
                child++;
            }
            // ASSERT: at this point, elements[child] is the smaller of
            // the two children
            if (elements[child].compareTo(temp) < 0) {
                elements[index] = elements[child];
                // saves the location of the key in the heap into the node
                ((DijkstraNode) elements[index]).setHeapLocation(index);

                index = child;
            } else {
                break;
            }
        }
        elements[index] = temp; 
        // saves the location of the key in the heap into the node
        ((DijkstraNode) elements[index]).setHeapLocation(index);
    }
    
    /**
     * Changes the weight of the DijkstraNode to a new value and reorders heap structure
     * @param index location of the DijkstraNode to make changes to
     * @param newWeight the new weight value of the node
     */
    public void decreaseKey(int index) {
    	percolateUp(index);
    }
}
