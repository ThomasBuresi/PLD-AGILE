package tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author H4112
 * Final implementation of the Breach and Bound algorithm
 * which uses an advanced iterator in order to favor
 * the nearest nodes to the current node.
 */
public class TSP3 extends TSP2 {
	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter2(unvisited, currentVertex, g);
	}
}
