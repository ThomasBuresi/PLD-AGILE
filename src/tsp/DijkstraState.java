package tsp;

import java.util.Comparator;

import model.Intersection;

public class DijkstraState implements Comparator<DijkstraState> {
	
	private float distance;
	private Intersection position;
	
	public DijkstraState(float distance, Intersection position) {
		super();
		this.distance = distance;
		this.position = position;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public Intersection getPosition() {
		return position;
	}
	
	@Override
	public int compare(DijkstraState o1, DijkstraState o2) {
		if(o1.getDistance() < o2.getDistance()) {
			return -1;
		}
		if (o1.getDistance() > o2.getDistance()) {
			return 1;
		}
		return 0;
	}
	
	
	
	

}
