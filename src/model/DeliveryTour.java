package model;
import java.util.*;
import com.google.common.collect;
import com.sun.tools.javac.util.Pair;

import tsp.DijkstraGraph;

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
    
    /**
     * Adds a new intersection after the i-th index (starting from 0), and updates relevant paths
     * @param i the index after which the new step should be added
     * @param step the intersection to be added
     */
	public void addIntermediateStep(int i, Intersection step) {
		Pair<Intersection, List<Segment>> previousStep = tour.get(i);
		tour.add(++i,
				new Pair<>(step, DijkstraGraph.computeShortestPath(previousStep.fst, step).getAllPreviousSegments()));
		if (i + 1 < tour.size()) {
			Pair<Intersection, List<Segment>> nextStep = tour.get(i + 1);
			Pair<Intersection, List<Segment>> newStep = new Pair<>(nextStep.fst,
					DijkstraGraph.computeShortestPath(step, nextStep.fst).getAllPreviousSegments());
			tour.remove(i + 1);
			tour.add(newStep);
		}
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