package model;
import java.util.*;
import com.google.common.collect;
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


	public void affiche() {
		
		for (int i =0; i< tour.size(); i++) {
			System.out.println(tour.get(i).fst.toString());
			if (tour.get(i).snd != null) {
				for (int j =0; j< tour.get(i).snd.size(); i++) {
					if (tour.get(i).snd.get(j)!=null) {
						
						System.out.println( tour.get(i).snd.get(j).toString());

					}
				}
			}

		}
	}



}