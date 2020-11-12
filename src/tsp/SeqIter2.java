package tsp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SeqIter2 extends SeqIter {
	public class VertexComparator implements Comparator<Integer> {
		Graph graph;
		int origin;
		
		public VertexComparator (Graph graph, int origin) {
			this.graph = graph;
			this.origin = origin;
		}
		
		@Override
		public int compare(Integer o1, Integer o2) {
			return graph.getCost(origin, o1) - graph.getCost(origin, o2);
		}
		
	}
	public SeqIter2(Collection<Integer> unvisited, int currentVertex, Graph g) {
		super(unvisited, currentVertex, g);
		candidates = Arrays.copyOf(candidates, nbCandidates);
		Arrays.sort(candidates, new VertexComparator(g, currentVertex));
	}

}
