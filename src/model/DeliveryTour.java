package model;
import java.util.*;
//import com.google.common.collect;
import com.sun.tools.javac.util.Pair;

/**
 * 
 */
public class DeliveryTour {
	// Hashmap qui prend en entrée 
	List <Pair<Intersection, List<Segment>>> tour;

    /**
     * Default constructor
     */
    public DeliveryTour() {
		tour = new ArrayList <Pair<Intersection, List<Segment>>>();
    }
    
    public DeliveryTour(List <Pair<Intersection, List<Segment>>> tour) {
		this.tour = tour;
    }
    
    public void addStep(Intersection i , List<Segment> s) {
    	tour.add(new Pair<>(i,s));
    }
    
    public void addDeparture(Intersection i) {
    	tour.add(new Pair<>(i, null));
    }

	public List<Pair<Intersection, List<Segment>>> getTour() {
		return tour;
	}


	public void affiche() {
		
		for (Pair<Intersection, List<Segment>> pair: tour) {
			//Intersection i = pair.getFirst();
			//System.out.println("hello");
			System.out.println(pair.fst.toString());
			List<Segment> seg = pair.snd;
			if (seg != null) {
				for (Segment s : seg) {
					System.out.println(s.toString());
				}
			}
		}
	}
				
			

		
	



}