/**
 * 
 */
package tsp;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.RequestList;
import model.Segment;
import tsp.DijkstraState;

/**
 * @author sylvain
 *
 */
public class DijkstraGraph implements Graph {
	

	private final int NO_EDGE = -1;
	private CityMap cityMap;
	private RequestList requestList;
	private float[][] edges;
	private List<Segment>[][] segmentPaths;
	

	public DijkstraGraph(CityMap cityMap, RequestList requestList) {
		this.cityMap = cityMap;
		this.requestList = requestList;
		fillGraph();
	}
	
	public List<Segment>[][] getSegmentPaths() {
		return segmentPaths;
	}
	
	@Override
	public int getNbVertices() {
		// TODO Auto-generated method stub
		return edges.length;
	}

	@Override
	public int getCost(int i, int j) {
		// TODO improve int conversion
		return (int) edges[i][j];
	}

	@Override
	public boolean isArc(int i, int j) {
		// TODO Auto-generated method stub
		return edges[i][j] != -1;
	}
	public RequestList getRequestList() {
		return requestList;
	}

	public void setRequestList(RequestList requestList) {
		this.requestList = requestList;
	}
	private DijkstraState computeDistance(Intersection origin, Intersection destination) {
		PriorityQueue<DijkstraState> pq = new PriorityQueue<DijkstraState>(1, new DijkstraState());
		Set<Long> visitedIntersections = new HashSet<Long>();
		pq.add(new DijkstraState(0, origin, null, null));
		while (!pq.isEmpty()) {
			DijkstraState curState = pq.poll();
			if(curState.getPosition().getIdIntersection() == destination.getIdIntersection()) {
				return curState;
			}
			for(Segment s : curState.getPosition().getListSegments()) {
				if(!visitedIntersections.contains(s.getDestination().getIdIntersection())) {
					visitedIntersections.add(s.getDestination().getIdIntersection());
					pq.add(new DijkstraState(curState.getDistance() + s.getLength(), s.getDestination(), curState, s));
				}
			}
		}
		return null;
	}
	
	private void fillGraph() {
		edges = new float[1+2*requestList.getListRequests().size()][1+2*requestList.getListRequests().size()];
		segmentPaths = new List[1+2*requestList.getListRequests().size()][1+2*requestList.getListRequests().size()];
		// (2*n, 2*n+1) : pickup and delivery points for request n
		int requestNb = requestList.getListRequests().size();
		for(int i = 0; i < 1+2*requestNb; i++) {
			for(int j = 0; j < 1+2*requestNb; j++) {
				if(i == j) {
					edges[i][j] = 0;
				} else {
					Intersection origin, destination;
					i--;
					j--;
					if(i != -1) {
						Request originRequest = requestList.getListRequests().get(i/2);
						origin = (i % 2 == 0) ? originRequest.getPickupAddress() : originRequest.getDeliveryAddress();
					} else {
						origin = requestList.getDeparture();
					}
					
					if (j != -1) {
						Request destinationRequest = requestList.getListRequests().get(j/2);
						destination = (j % 2 == 0) ? destinationRequest.getPickupAddress() : destinationRequest.getDeliveryAddress();
					} else {
						destination = requestList.getDeparture();
					}
					DijkstraState finalState = computeDistance(origin, destination);
					i++;
					j++;
					if(finalState == null) {
						edges[i][j] = -1;
						segmentPaths[i][j] = null;
					}
					else {
						edges[i][j] = finalState.getDistance();
						segmentPaths[i][j] = new LinkedList<Segment>();
						while(finalState != null) {
							segmentPaths[i][j].add(0, finalState.getPreviousSegment());
							finalState = finalState.getPreviousState();
						}
					}
				}
			}
		}
	}

}
