package model;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.sun.tools.javac.util.Pair;

import controller.Controller;
import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP2;
import tsp.TSP3;




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
     * Represents the description of the intersection in the tour:
     * if it'a a pickup/delivery point or the return to the deposit 
     */ 
	List <String> pickupOrDeliver ; 
	
	/**
     * List of ids of the intersections in the tour in the right order 
     * of passage
     */ 
	List <Integer> ordretsp;//

	CityMap map ;
	RequestList reqlist;
	
	/**
     * Represents the graph with the shortest distance between all the 
     * intersections present in the map 
     */ 
	DijkstraGraph g;
	
	/**
     * Represents the solution of the best tour calculated
     */ 
	TSP tsp;

    /**
     * Constructor of <code>DeliveryTour</code>  that calculates
     * the <code>DijkstraGraph</code> from the loaded map and requests 
     */
    public DeliveryTour(Controller controller) {
		this.tour = new ArrayList <Pair<Intersection, List<Segment>>>();
		this.pickupOrDeliver =new ArrayList<String>();
		this.tsp = new TSP3();
		this.map = controller.getCityMap();
		this.reqlist = controller.getRequestList();
		this.g = new DijkstraGraph(this.map, this.reqlist);
		this.ordretsp = new ArrayList<Integer>();
    }
    /**
     * Constructor of <code>DeliveryTour</code>  that takes as parameters the
     * <code>DijkstraGraph</code> and the <code>TSP</code> already calculated
     * to be able to continue the calculation of the tour
     */
    public DeliveryTour(Controller controller, TSP tsp, DijkstraGraph g) {
		this.tour = new ArrayList <Pair<Intersection, List<Segment>>>();
		this.pickupOrDeliver =new ArrayList<String>();
		this.tsp = tsp;
		this.map = controller.getCityMap();
		this.reqlist = controller.getRequestList();
		this.g = g;
		this.ordretsp = new ArrayList<Integer>();
    }
    
 
    /**
     * Adds an intersection and the list of segments that lead to this 
     * intersection from the previous one in the tour.
     * @param i the Intersection to add to the tour
     * @param s the list of segments
     */
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
	
	/**
     * TODO
     */ 
	public int getIndexOfIntersection(Intersection i) {
		int index = 0;
		for (Pair<Intersection, List<Segment>> pair: tour) {
			if ( i.getIdIntersection() == pair.fst.getIdIntersection()) {
				return index;
			}
			index ++;
		}
		return -1;
	}
	
	/**
     * TODO
     */ 
	public void removeStep(Intersection i) {
		int index= this.getIndexOfIntersection(i);
		this.tour.remove(index);
		this.pickupOrDeliver.remove(index);
		this.ordretsp.remove(index);
		Intersection temp = tour.get(index).fst;
		List <Segment> tempSeg = g.getSegmentPaths()[index][index-1];
		tour.set(index, new Pair<Intersection, List<Segment>>(temp,tempSeg));
	}


	/**
     * Adds the departure Intersection as the first one in the tour
     * @param i the departure Intersection
     */
    public void addDeparture(Intersection i) {
    	tour.add(new Pair<>(i, null));
    }


    /**
     * TODO
     */ 
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



	
	/**
     * Calculates the delivery tour until the <code>timeLimit</code> is reached
     * @param timeLimit the limit of time in ms available to calculate the tour 
     */
	public void fillDeliveryTour(int timeLimit) {
		
		tsp.searchSolution(timeLimit, g);
		
		this.addDeparture(reqlist.getDeparture());
		this.addIntersectionDetail("");
		this.ordretsp.add(0);
		// on commence a un car on a deja traite le cas du depart
		for(int l = 1; l < 1+2*reqlist.getListRequests().size(); l++) {
			//ajouter au delivery tour l'intersection qui correspond au numero de la requete ->
			int currentsolution=tsp.getSolution(l);	
			if (currentsolution%2!=0) {
	//			System.out.println(currentsolution + "   " + (tsp.getSolution(l)/2));
	//			System.out.println("Pickup Address :" + reqlist.getListRequests().get(tsp.getSolution(l)/2).getPickupAddress().getIdIntersection() );
				this.addIntersectionDetail("Pickup Address");
				this.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 ).getPickupAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
			}
			else {
	//			System.out.println(currentsolution + "   " + (tsp.getSolution(l)/2 -1));
	//			System.out.println("Delivery Address :" + reqlist.getListRequests().get(tsp.getSolution(l)/2 - 1).getDeliveryAddress().getIdIntersection() );
				this.addIntersectionDetail("Delivery Address");
				this.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 -1).getDeliveryAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
			}

		
		this.ordretsp.add(currentsolution);
		}
		//retour au point de départ :

		this.ordretsp.add(0);
		this.addIntersectionDetail("Return to Departure");
		this.addStep(reqlist.getDeparture(), g.getSegmentPaths()[tsp.getSolution(2*reqlist.getListRequests().size())][tsp.getSolution(0)]); // inverser l'ordre??
		
	}
	
	/**
     * Exports the delivery tour as a description of the steps of the tour
     * to take to a file (its path is given as a parameter)
     */
	public void writeDeliveryTourToFile(String filename) {
		File file = new File(filename);
        FileWriter fr = null;
        BufferedWriter br = null;
       
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            int p = 0;
            br.write("~~~~~~~~~~~ Delivery Roadmap ~~~~~~~~~~~"+ System.getProperty("line.separator"));
            for (Pair<Intersection, List<Segment>> pair: tour) {
            	if (p==0) {
            		br.write("Departure : " + pair.fst.getName() +  System.getProperty("line.separator"));
            		br.write (System.getProperty("line.separator"));
            	}
         
    			List<Segment> seg = pair.snd;
    			if (seg != null) {
    				Segment segmentprecedent = null;
    				Segment segmentsuivant;
    				float longueur = 0.0f;
    				for (Segment s : seg) {
    					segmentsuivant = s;
    					if (segmentprecedent != null && segmentsuivant != null && segmentsuivant.getName().equals(segmentprecedent.getName())!= true) {
    						longueur += segmentprecedent.length;
    						br.write("          Pendant" + longueur + "m prendre " + segmentprecedent.getName()+ System.getProperty("line.separator"));
    						longueur = 0.0f;
    					}
    					else {
    						
    					}
    				}
    			}
    			if (p!=0) {
    				br.write("Step " + p +": "+ this.pickupOrDeliver.get(p)+ System.getProperty("line.separator"));
    				br.write("Address : " + pair.fst.getName() + System.getProperty("line.separator"));
    				br.write (System.getProperty("line.separator"));
    			}
    			p++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	/**
     * Adds the description of the intersection: if it'a a pickup/delivery point
     * or the return to the deposit 
     * @param detail the description of the intersection
     */
	public void addIntersectionDetail(String detail) {
		this.pickupOrDeliver.add(detail);
	}

	public DijkstraGraph getG() {
		return g;
	}

	public TSP getTsp() {
		return tsp;
	}
	
	public List<Pair<Intersection, List<Segment>>> getTour() {
		return tour;
	}
}
			

		
	



