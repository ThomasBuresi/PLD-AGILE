package model;
import java.util.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.sun.tools.javac.util.Pair;

import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 

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
	List <String> pickupOrDeliver ; // string qui dit si l'intersection est un point de pickup ou delivery
	CityMap map ;
	RequestList reqlist;
	DijkstraGraph g;

    /**
     * Default constructor of <code>DeliveryTour</code>
     */
    public DeliveryTour() {
		tour = new ArrayList <Pair<Intersection, List<Segment>>>();
		pickupOrDeliver =new ArrayList<String>();
    }
    
    /**
     * Constructor of <code>DeliveryTour</code> that takes as a parameter
     * the <code>tour</code>
     */
    public DeliveryTour(List <Pair<Intersection, List<Segment>>> tour) {
		this.tour = tour;
		pickupOrDeliver =new ArrayList<String>();
    }
    
    public DeliveryTour(String filenameMap, String filenameRequest) {
    	tour = new ArrayList <Pair<Intersection, List<Segment>>>();
		pickupOrDeliver =new ArrayList<String>();
		this.map = new CityMap(filenameMap);
		map.fillMap();
		this.reqlist = new RequestList(filenameRequest,map);
		reqlist.fillRequests();
		this.g = new DijkstraGraph(map,reqlist);
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

	public void fillDeliveryTour() {
		TSP tsp = new TSP1();
		tsp.searchSolution(20000, g);
		
		this.addDeparture(reqlist.getDeparture());
		this.addIntersectionDetail("");
		// on commence a un car on a deja traite le cas du depart
		for(int l = 1; l < 1+2*reqlist.getListRequests().size(); l++) {
		//ajouter au delivery tour l'intersection qui correspond au numero de la requete ->
		int currentsolution=tsp.getSolution(l);	
		if (currentsolution%2!=0) {
//			System.out.println("pair");
//			System.out.println(currentsolution + "   " + (tsp.getSolution(l)/2));
//			System.out.println("Pickup Address :" + reqlist.getListRequests().get(tsp.getSolution(l)/2).getPickupAddress().getIdIntersection() );
			this.addIntersectionDetail("Pickup Address");
			this.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 ).getPickupAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
		}
		else {
//			System.out.println("impair");
//			System.out.println(currentsolution + "   " + (tsp.getSolution(l)/2 -1));
//			System.out.println("Delivery Address :" + reqlist.getListRequests().get(tsp.getSolution(l)/2 - 1).getDeliveryAddress().getIdIntersection() );
			this.addIntersectionDetail("Delivery Address");
			this.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 -1).getDeliveryAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
		}
		//retour au point de départ :
		
		}
		this.addIntersectionDetail("Return to Departure");
		this.addStep(reqlist.getDeparture(), g.getSegmentPaths()[tsp.getSolution(2*reqlist.getListRequests().size())][tsp.getSolution(0)]); // inverser l'ordre??
		
	}
	
	
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
            	// a refaire
//    			List<Segment> seg = pair.snd;
//    			if (seg != null) {
//    				for (Segment s : seg) {
//    					br.write("          "+ s.toString()+ System.getProperty("line.separator"));
//    				}
//    			}
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


	public void addIntersectionDetail(String detail) {
		this.pickupOrDeliver.add(detail);
	}
}
			

		
	



