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
import tsp.TSP3;




/**
 * Delivery tour  with the generation of the durations and export file. 
 * 
 * @author H4112
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
	
	List <Float> durations ;
	
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
		this.durations= new ArrayList<Float>();

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
		this.durations= new ArrayList<Float>();

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
     * Adds a new intersection after the intersection given as parameter, and updates relevant paths
     * @param previousInter the intersection after which the new step should be added
     * @param step the intersection to be added
     * @param PoD true for pickup, false for delivery
     * @param d duration in minutes
     */
	public void addIntermediateStep(Intersection previousInter, Intersection step, boolean PoD, float d) {
		int i = this.getIndexOfIntersection(previousInter);
		tour.add(++i,
				new Pair<>(step, DijkstraGraph.computeShortestPath(previousInter, step).getAllPreviousSegments()));
		if (PoD) {
			pickupOrDeliver.add(i, "Pickup Address");//ou i+1 ? je ne me souviens plus comment ca marche ++i
		}
		else {
			pickupOrDeliver.add(i, "Delivery Address");
		}
		d = d/60f;// from minutes to hour 
		durations.add(i,d); //?? good index 
		
		if (i + 1 < tour.size()) {
			Pair<Intersection, List<Segment>> nextStep = tour.get(i + 1);
			Pair<Intersection, List<Segment>> newStep = new Pair<>(nextStep.fst,
					DijkstraGraph.computeShortestPath(step, nextStep.fst).getAllPreviousSegments());
			tour.remove(i + 1);
			tour.add(i+1, newStep);
		}
	}
	
	/**
	 * Returns the index corresponding to an intersection of the tour.
	 *  
	 * @param i
	 * @return
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
	 * Remove a portion of the tour of a given intersection.
	 * 
	 * @param i
	 * @return
	 */
	public Intersection removeStep(Intersection i) {
		int index= this.getIndexOfIntersection(i);
		this.tour.remove(index);
		this.pickupOrDeliver.remove(index);
		this.durations.remove(index);
		//this.ordretsp.remove(index);
		Intersection temp = tour.get(index).fst;
		List <Segment> tempSeg = DijkstraGraph.computeShortestPath(temp, tour.get(index-1).fst).getAllPreviousSegments();
		tour.set(index, new Pair<Intersection, List<Segment>>(temp,tempSeg));
		return tour.get(index-1).fst;
	}


	/**
     * Adds the departure Intersection as the first one in the tour
     * @param i the departure Intersection
     */
    public void addDeparture(Intersection i) {
    	tour.add(new Pair<>(i, null));
    }


    /**
     * Writes in the console the tour
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
		this.durations.add(0.0f);
		// on commence a un car on a deja traite le cas du depart
		for(int l = 1; l < 1+2*reqlist.getListRequests().size(); l++) {
			//ajouter au delivery tour l'intersection qui correspond au numero de la requete ->
			int currentsolution=tsp.getSolution(l);	
			if (currentsolution%2!=0) {
				this.addIntersectionDetail("Pickup Address");
				this.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 ).getPickupAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
				this.durations.add( ((float)reqlist.getListRequests().get(tsp.getSolution(l)/2).pickupDuration)/3600);
			}
			else {
				this.addIntersectionDetail("Delivery Address");
				this.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 -1).getDeliveryAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
				this.durations.add( ((float)reqlist.getListRequests().get(tsp.getSolution(l)/2-1).deliveryDuration)/3600);
			}

		
		this.ordretsp.add(currentsolution);
		}
		//retour au point de depart :

		this.ordretsp.add(0);
		this.addIntersectionDetail("Return to Departure");
		this.durations.add(0.0f);
		this.addStep(reqlist.getDeparture(), g.getSegmentPaths()[tsp.getSolution(2*reqlist.getListRequests().size())][tsp.getSolution(0)]); // inverser l'ordre??
		
	}
	
	/**
     * Exports the delivery tour as a description of the steps of the tour
     * to take to a file (its path is given as a parameter)
     * 
     * @param filename path to the file where to export the tour
     */
	public void writeDeliveryTourToFile(String filename) {
		File file = new File(filename);
        FileWriter fr = null;
        BufferedWriter br = null;
        
        List <TimeDelivery> times = this.computeTime();
       
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            int p = 0;
            br.write("~~~~~~~~~~~ Delivery Roadmap ~~~~~~~~~~~"+ System.getProperty("line.separator"));
            for (Pair<Intersection, List<Segment>> pair: tour) {
            	if (p==0) {
            		br.write("Departure : " + pair.fst.getName() + " at " + times.get(0).toString() +  System.getProperty("line.separator"));
            		br.write (System.getProperty("line.separator"));
            	}
         
    			List<Segment> seg = pair.snd;
    			if (seg != null) {
    				Segment segmentprecedent = null;
    				Segment segmentsuivant;
    				int longueur = 0;
    				int dernierseg = seg.size();
    				int i =1;
    				for (Segment s : seg) {
    					segmentsuivant = s;
    					if (segmentsuivant != null && segmentprecedent != null) {
    						if (i != dernierseg) {
        						if (segmentsuivant.getName().equals(segmentprecedent.getName())!= true && !segmentsuivant.getName().equals("Name absent")) {
            						longueur += (int) segmentprecedent.length;
            						br.write("          For " + longueur + "m take " + segmentprecedent.getName()+ System.getProperty("line.separator"));
            						longueur = 0;
            					}
            					else {
            						longueur += (int) segmentprecedent.length;
            					}
        					}
        					else {
        						if (segmentprecedent != null && segmentsuivant != null && segmentsuivant.getName().equals(segmentprecedent.getName())!= true) {
        							longueur += (int) segmentprecedent.length;
            						br.write("          For " + longueur + "m take " + segmentprecedent.getName()+ System.getProperty("line.separator"));
            						longueur = (int) segmentsuivant.length;
            						br.write("          For " + longueur + "m take " + segmentsuivant.getName()+ System.getProperty("line.separator"));
        						}
        						else {
        							longueur += (int) segmentsuivant.length;
            						br.write("          For " + longueur + "m take " + segmentsuivant.getName()+ System.getProperty("line.separator"));
        						}
        					}
        					
        				}
    					segmentprecedent= segmentsuivant;
    					i++;
    					}
    					
    			}
    			br.write (System.getProperty("line.separator"));
    			if (p!=0) {
    				br.write("Step " + p +": "+ this.pickupOrDeliver.get(p)+ " at " + times.get(p).toString()+System.getProperty("line.separator"));
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
	 * Compute the durations corresponding to the steps of the tour.
	 * @return
	 */
	public List<TimeDelivery> computeTime(){
		List <TimeDelivery> result = new ArrayList <TimeDelivery>();
		List<Float> durationsMethod = new ArrayList <Float>(durations.size());
		for (float f : durations) {
			durationsMethod.add(f);
		}
		// departure time
		result.add(new TimeDelivery().addhours(this.reqlist.departureTime/3600));
		for (int i = 1 ; i< tour.size();i++) {
			float longueurtotale = 0.0f;
			for (int j = 0 ; j< tour.get(i).snd.size(); j ++) {
				longueurtotale +=  tour.get(i).snd.get(j).length/1000;
			}
			durationsMethod.set(i, durationsMethod.get(i)+longueurtotale/15); 
		}
		for (int i = 1; i < durationsMethod.size(); i++) {
			result.add(result.get(i-1).addhours(durationsMethod.get(i)));
		}
		return result;
	}
	/**
     * Adds the description of the intersection: if it'a a pickup/delivery point
     * or the return to the deposit 
     * @param detail the description of the intersection
     */
	public void addIntersectionDetail(String detail) {
		this.pickupOrDeliver.add(detail);
	}

	// Getters and Setters 
	
	public DijkstraGraph getG() {
		return g;
	}

	public TSP getTsp() {
		return tsp;
	}
	
	public List<Pair<Intersection, List<Segment>>> getTour() {
		return tour;
	}
	
	public void setReqlist(RequestList r) {
		reqlist=r;
	}
	

}
			

		
	



