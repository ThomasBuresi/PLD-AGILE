package tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP3 extends TSP2 {
	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter2(unvisited, currentVertex, g);
	}
}
