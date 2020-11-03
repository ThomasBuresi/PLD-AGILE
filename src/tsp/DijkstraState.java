package tsp;

import java.util.Comparator;

import model.Intersection;
import model.Segment;

public class DijkstraState implements Comparator<DijkstraState> {

	private float distance;
	private Intersection position;
	private DijkstraState previousState;

	public DijkstraState getPreviousState() {
		return previousState;
	}

	public Segment getPreviousSegment() {
		return previousSegment;
	}

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

	public float getDistance() {
		return distance;
	}

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
