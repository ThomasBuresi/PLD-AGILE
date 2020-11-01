/**
 * 
 */
package tsp;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import model.CityMap;
import model.Intersection;
import model.RequestList;
import model.Segment;

/**
 * @author sylvain
 *
 */
public class DijkstraGraph implements Graph {
	private final int NO_EDGE = -1;
	private CityMap cityMap;
	private RequestList requestList;
	private float[][] edges;
	
	public DijkstraGraph(CityMap cityMap, RequestList requestList) {
		this.cityMap = cityMap;
		this.requestList = requestList;
		fillGraph();
	}
	
	@Override
	public int getNbVertices() {
		// TODO Auto-generated method stub
		return edges.length;
	}

	@Override
	public int getCost(int i, int j) {
		// TODO Auto-generated method stub
		return edges[i][j];
	}

	@Override
	public boolean isArc(int i, int j) {
		// TODO Auto-generated method stub
		return edges[i][j] != -1;
	}
	
	private float computeDistance(Intersection origin, Intersection destination) {
		PriorityQueue<DijkstraState> pq = new PriorityQueue<DijkstraState>();
		Set<Long> visitedIntersections = new HashSet<Long>();
		while (!pq.isEmpty()) {
			DijkstraState curState = pq.poll();
			if(curState.getPosition().getIdIntersection() == destination.getIdIntersection()) {
				return curState.getDistance();
			}
			for(Segment s : curState.getPosition().getListSegments()) {
				if(!visitedIntersections.contains(s.getDestination().getIdIntersection())) {
					visitedIntersections.add(s.getDestination().getIdIntersection());
					pq.add(new DijkstraState(curState.getDistance() + s.getLength(), s.getDestination()));
				}
			}
		}
		return -1;
	}
	
	private void fillGraph() {
		edges = new int[2*requestList.getRequests().size()][2*requestList.getRequests().size()];
		int requestNb = 10;
		for(int i = 0; i < requestNb; i++) {
			
		}
	}

}
