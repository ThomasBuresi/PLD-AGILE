package tsp;

import java.util.Collection;

/**
 * @author H4112
 * Second implementation of the Breach and Bound algorithm,
 * with an elaborate lower bound function (in quadratic time complexity)
 */
public class TSP2 extends TSP1 {
	
	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		int l = Integer.MAX_VALUE;
		for(int i = 0; i < g.getNbVertices(); i++) {
			if(i != currentVertex && !unvisited.contains(i)) {
				l = Integer.min(l, g.getCost(currentVertex, i));
			}
		}
		int s = 0; // Sum of l_i's
		for(int i : unvisited) {
			int li = g.getCost(i, 0);
			for(int j : unvisited) {
				if(i != j) {
					li = Integer.min(li, g.getCost(i, j));
				}
			}
			s += li;
		}
		return l + s; 
	}
}
