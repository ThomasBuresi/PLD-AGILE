package tsp;

import java.util.Collection;

public class TSP2 extends TSP1 {
	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		return 0;
	}
}
