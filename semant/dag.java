import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * An example class for directed graphs.  The vertex type can be specified.
 * There are no edge costs/weights.
 * 
 * Written for CS211, Nov 2006.
 * 
 * @author Paul Chew
 */
public class dag<V> {
    
    /**
     * The implementation here is basically an adjacency list, but instead
     * of an array of lists, a Map is used to map each vertex to its list of 
     * adjacent vertices.
     */   
	//Associa un vertice a una lista che contiene vertici adiacenti
    private Map<V,List<V>> neighbors = new HashMap<V,List<V>>();
    //Associa un vertice al vertice padre
    private Map<V, V> parents = new HashMap<V,V>(); 
    /**
     * String representation of graph.
     */
    public String toString () {
        StringBuffer s = new StringBuffer();
        for (V v: neighbors.keySet()) s.append("\n    " + v + " -> " + neighbors.get(v));
        return s.toString();                
    }
    
    /**
     * Add a vertex to the graph.  Nothing happens if vertex is already in graph.
     */
    public Object add (V vertex) {
        if (neighbors.containsKey(vertex)) return null;
        return neighbors.put(vertex, new ArrayList<V>());
    }
    
    /**
     * True iff graph contains vertex.
     */
    public boolean contains (V vertex) {
        return neighbors.containsKey(vertex);
    }
    
    public boolean containsParent( V vertex ){
    	return parents.containsKey(vertex);
    }
    
    public V getParent( V vertex ){
    	return parents.get(vertex);
    }
    
    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public boolean add (V from, V to) {
    	if ( !neighbors.containsKey(to) ){
    		this.add(from); this.add(to);
            neighbors.get(from).add(to);
            parents.put(to, from);
            return true;
    	}
    	return false;
    }
    
    /**
     * Remove an edge from the graph.  Nothing happens if no such edge.
     * @throws IllegalArgumentException if either vertex doesn't exist.
     */
    public void remove (V from, V to) {
        if (!(this.contains(from) && this.contains(to)))
            throw new IllegalArgumentException("Nonexistent vertex");
        neighbors.get(from).remove(to);
    }
    
    public List<V> getAdjList( V v){
    	return neighbors.get(v);
    }
    
    /**
     * Report (as a Map) the out-degree of each vertex.
     */
    public Map<V,Integer> outDegree () {
        Map<V,Integer> result = new HashMap<V,Integer>();
        for (V v: neighbors.keySet()) result.put(v, neighbors.get(v).size());
        return result;
    }
    
    /**
     * Report (as a Map) the in-degree of each vertex.
     */
    public Map<V,Integer> inDegree () {
        Map<V,Integer> result = new HashMap<V,Integer>();
        for (V v: neighbors.keySet()) result.put(v, 0);       // All in-degrees are 0
        for (V from: neighbors.keySet()) {
            for (V to: neighbors.get(from)) {
                result.put(to, result.get(to) + 1);           // Increment in-degree
            }
        }
        return result;
    }
    
    /**
     * Report (as a List) the topological sort of the vertices; null for no such sort.
     */
    public List<V> topSort () {
        Map<V, Integer> degree = inDegree();
        // Determine all vertices with zero in-degree
        Stack<V> zeroVerts = new Stack<V>();        // Stack as good as any here
        for (V v: degree.keySet()) {
            if (degree.get(v) == 0) zeroVerts.push(v);
        }
        // Determine the topological order
        List<V> result = new ArrayList<V>();
        while (!zeroVerts.isEmpty()) {
            V v = zeroVerts.pop();                  // Choose a vertex with zero in-degree
            result.add(v);                          // Vertex v is next in topol order
            // "Remove" vertex v by updating its neighbors
            for (V neighbor: neighbors.get(v)) {
                degree.put(neighbor, degree.get(neighbor) - 1);
                // Remember any vertices that now have zero in-degree
                if (degree.get(neighbor) == 0) zeroVerts.push(neighbor);
            }
        }
        // Check that we have used the entire graph (if not, there was a cycle)
        if (result.size() != neighbors.size()) return null;
        return result;
    }
    
    /**
     * True iff graph is a dag (directed acyclic graph).
     */
    public boolean isDag () {
        return topSort() != null;
    }
    
    /**
     * Report (as a Map) the bfs distance to each vertex from the start vertex.
     * The distance is an Integer; the value null is used to represent infinity
     * (implying that the corresponding node cannot be reached).
     */
    public Map bfsDistance (V start) {
        Map<V,Integer> distance = new HashMap<V,Integer>();
        // Initially, all distance are infinity, except start node
        for (V v: neighbors.keySet()) distance.put(v, null);
        distance.put(start, 0);
        // Process nodes in queue order
        Queue<V> queue = new LinkedList<V>();
        queue.offer(start);                                    // Place start node in queue
        while (!queue.isEmpty()) {
            V v = queue.remove();
            int vDist = distance.get(v);
            // Update neighbors
            for (V neighbor: neighbors.get(v)) {
                if (distance.get(neighbor) != null) continue;  // Ignore if already done
                distance.put(neighbor, vDist + 1);
                queue.offer(neighbor);
            }
        }
        return distance;
    }
 
}
