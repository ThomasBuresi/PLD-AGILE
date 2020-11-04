package model;
import java.util.*;
//import com.google.common.collect;
import com.sun.tools.javac.util.Pair;

/**
 * 
 */
public class DeliveryTour {
	
	/**
     * Represents the delivery tour calculated. It is modeled as a 
     * list of pairs composed of an intersection and the list of 
     * segments that lead to this intersection from the previous one
     */ 
	List <Pair<Intersection, List<Segment>>> tour;

    /**
     * Default constructor of <code>DeliveryTour</code>
     */
    public DeliveryTour() {
		tour = new ArrayList <Pair<Intersection, List<Segment>>>();
    }
    
    /**
     * Constructor of <code>DeliveryTour</code> that takes as a parameter
     * the <code>tour</code>
     */
    public DeliveryTour(List <Pair<Intersection, List<Segment>>> tour) {
		this.tour = tour;
    }
    
    /**
     * 
     */
    public void addStep(Intersection i , List<Segment> s) {
    	tour.add(new Pair<>(i,s));
    }
    
    /**
     * 
     */
    public void addDeparture(Intersection i) {
    	tour.add(new Pair<>(i, null));
    }

	public List<Pair<Intersection, List<Segment>>> getTour() {
		return tour;
	}


	public void affiche() {
		
		for (Pair<Intersection, List<Segment>> pair: tour) {
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