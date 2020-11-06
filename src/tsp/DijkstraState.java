package tsp;

import java.util.Comparator;

import model.Intersection;
import model.Segment;

/**
 * This class represents each intermediate state of a Dijkstra search
 */
public class DijkstraState implements Comparator<DijkstraState> {

	private float distance;
	private Intersection position;
	private DijkstraState previousState;
	private Segment previousSegment;
	
	public DijkstraState(float distance, Intersection position, DijkstraState previousState, Segment previousSegment) {
		super();
		this.distance = distance;
		this.position = position;
		this.previousState = previousState;
		this.previousSegment = previousSegment;
	}
	
	public DijkstraState() {
	}
	
	/**
	 * @return the previous state, or <code>null</code> if this is the initial state
	 */
	public DijkstraState getPreviousState() {
		return previousState;
	}
	
	/**
	 * @return the segment taken between the previous state and this one, or <code>null</code> if this is the inital state
	 */
	public Segment getPreviousSegment() {
		return previousSegment;
	}

	
	/**
	 * @return the total travel distance in meters between the initial step and this one
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * @return the current position on the city map
	 */
	public Intersection getPosition() {
		return position;
	}

	@Override
	public int compare(DijkstraState o1, DijkstraState o2) {
		if (o1.getDistance() < o2.getDistance()) {
			return -1;
		}
		if (o1.getDistance() > o2.getDistance()) {
			return 1;
		}
		return 0;
	}

}
