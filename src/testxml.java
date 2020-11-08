import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;


import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File; 
import model.CityMap;
import model.DeliveryTour;
import model.Intersection;
import model.Segment;
import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP1;
import model.RequestList;

import java.util.*;


public class testxml {
  public static void main(String[] args) {
	  try{  

		  File file = new File("src/resources/smallMap.xml");  
		
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		  DocumentBuilder db = dbf.newDocumentBuilder();  
		  Document doc = db.parse(file);  
		  doc.getDocumentElement().normalize();  
		  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
		
		  NodeList nodeList = doc.getElementsByTagName("intersection");
		  float latmin = 99.9f;
		  float latmax = 0.0f;
		  float longmin = 99.9f;
		  float longmax = 0.0f;
		  for (int i = 0; i < nodeList.getLength(); i++) {  
			Node node = nodeList.item(i);  
			//System.out.println("\nNode Name :" + node.getNodeName());  
			if (node.getNodeType() == Node.ELEMENT_NODE){  
		  		Element eElement = (Element) node;
		  		Intersection inter = new Intersection(Float.parseFloat(eElement.getAttribute("latitude")),Float.parseFloat(eElement.getAttribute("longitude")),Long.parseLong(eElement.getAttribute("id")));
		  		if (inter.getLatitude()> latmax){
		  			latmax = inter.getLatitude();
		  		}
		  		else if(inter.getLatitude()< latmin) {
		  			latmin = inter.getLatitude();
		  		}
		  		if (inter.getLongitude()> longmax){
		  			longmax = inter.getLongitude();
		  		}
		  		else if(inter.getLongitude()< longmin) {
		  			longmin = inter.getLongitude();
		  		}
		  		//System.out.println(inter.toString());  

		  	}  
		  }
		System.out.println("Latitude min = " + latmin);
		System.out.println("Latitude max = " + latmax);
		System.out.println("Longitude min = " + longmin);
		System.out.println("Longitude max = " + longmax);
	  	}   		
		  catch (Exception e){  
		  e.printStackTrace(); 
		}  
	  
	  System.out.println("\n************TEST OF CITYMAP****************");
	  
	  CityMap map = new CityMap("src/resources/largeMap.xml");
	  map.fillMap();
	  
	  System.out.println("\n"+map.getIntersection(25175791).toString());
	  
	  System.out.println("\nintersections "+map.getListIntersection().size());
	  
	  HashMap <Long, Intersection> list = map.getListIntersection();
	  
	  int i=0;
	  
	  //Iterate through the HashMap (to get all the segments for example)
	  for (Map.Entry <Long, Intersection> entry : list.entrySet()) {
		  i+= entry.getValue().getListSegments().size();
		  //System.out.println(entry.getKey() + "/" + entry.getValue().toString());
	  }
	  
	  System.out.println("segments " + i);
	  
	  System.out.println("\n************TEST OF REQUESTLIST****************");
	  
	  RequestList reqlist = new RequestList("src/resources/requestsSmall2.xml", map);
	  
	  reqlist.fillRequests();
	  
	  System.out.println(reqlist);
	  
	  DijkstraGraph g = new DijkstraGraph(map, reqlist);
	  
	  	for(int j = 0; j < 1+2*reqlist.getListRequests().size(); j++) {
		  for(int k = 0; k < 1+2*reqlist.getListRequests().size(); k++) {
			  System.out.print(g.getCost(j, k) + " ");
		  }
		  System.out.println();
	  	}
		TSP tsp = new TSP1();
		tsp.searchSolution(20000, g);
		System.out.println("Solution TSP de cout : " + tsp.getSolutionCost());
		for(int m = 0; m < 1+2*reqlist.getListRequests().size(); m++) {
			System.out.print(" " + tsp.getSolution(m));
		}
		System.out.println(" 0");
		//DeliveryTour d = new DeliveryTour("src/resources/largeMap.xml","src/resources/requestsSmall2.xml");
		//d.fillDeliveryTour();
//		d.addDeparture(reqlist.getDeparture());
//		d.addIntersectionDetail("");
//		// on commence � un car on a d�j� trait� le cas du d�part
//		for(int l = 1; l < 1+2*reqlist.getListRequests().size(); l++) {
//		//ajouter au delivery tour l'intersection qui correspond au numero de la requ�te ->
//		int currentsolution=tsp.getSolution(l);
//		
//		
//		List <Segment> segs = g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)];
////		for (int i1 =1; i1<segs.size();i1++) {
////			System.out.println(segs.get(i1).toString());
////		}
////		
//		if (currentsolution%2!=0) {
//			System.out.println("pair");
//			System.out.println(currentsolution + "   " + (tsp.getSolution(l)/2));
//			System.out.println("Pickup Address :" + reqlist.getListRequests().get(tsp.getSolution(l)/2).getPickupAddress().getIdIntersection() );
//			d.addIntersectionDetail("Pickup Address");
//			d.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 ).getPickupAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
//		}
//		else {
//			System.out.println("impair");
//			System.out.println(currentsolution + "   " + (tsp.getSolution(l)/2 -1));
//			System.out.println("Delivery Address :" + reqlist.getListRequests().get(tsp.getSolution(l)/2 - 1).getDeliveryAddress().getIdIntersection() );
//			d.addIntersectionDetail("Delivery Address");
//			d.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 -1).getDeliveryAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
//		}
//		//retour au point de d�part :
//		
//		}
//		d.addIntersectionDetail("Return to Departure");
//		d.addStep(reqlist.getDeparture(), g.getSegmentPaths()[tsp.getSolution(2*reqlist.getListRequests().size())][tsp.getSolution(0)]); // inverser l'ordre??
//	d.affiche();
//	d.writeDeliveryTourToFile("test.txt");
	
    //Bienvenue sur le projet AGILE 
  }
}
  

