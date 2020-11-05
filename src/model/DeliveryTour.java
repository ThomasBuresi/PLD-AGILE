package model;
import java.util.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.sun.tools.javac.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 

/**
 * 
 */
public class DeliveryTour {
	// Hashmap qui prend en entrée 
	List <Pair<Intersection, List<Segment>>> tour;
	List <String> pickupOrDeliver ; // string qui dit si l'intersection est un point de pickup ou delivery

    /**
     * Default constructor
     */
    public DeliveryTour() {
		tour = new ArrayList <Pair<Intersection, List<Segment>>>();
		pickupOrDeliver =new ArrayList<String>();
    }
    
    public DeliveryTour(List <Pair<Intersection, List<Segment>>> tour) {
		this.tour = tour;
		pickupOrDeliver =new ArrayList<String>();
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
		
	public void writeDeliveryTourToFile(String filename) {
		File file = new File(filename);
        FileWriter fr = null;
        BufferedWriter br = null;
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");

        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            int p = 0;
            br.write("~~~~~~~~~~~ Delivery Roadmap ~~~~~~~~~~~"+ System.getProperty("line.separator"));
            for (Pair<Intersection, List<Segment>> pair: tour) {
            	if (p==0) {
            		br.write("Departure : " + pair.fst.toAddress(jOpenCageGeocoder) +  System.getProperty("line.separator"));
            		br.write (System.getProperty("line.separator"));
            	}
//    			List<Segment> seg = pair.snd;
//    			if (seg != null) {
//    				for (Segment s : seg) {
//    					br.write("          "+ s.toString()+ System.getProperty("line.separator"));
//    				}
//    			}
    			if (p!=0) {
    				br.write("Step " + p +": "+ this.pickupOrDeliver.get(p)+ System.getProperty("line.separator"));
    				br.write("Address : " + pair.fst.toAddress(jOpenCageGeocoder) + System.getProperty("line.separator"));
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
			

		
	



