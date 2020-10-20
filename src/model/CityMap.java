package model;


import java.io.File;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import src.tsp.*;

/**
 * 
 */
public class CityMap {
	
	 /**
     * 
     */
    protected String filePath;
    
    /**
     * 
     */
    protected HashMap<Long,Intersection> listIntersection;
    
    
    /**
     * 
     */
    protected HashMap<Long,Segment> listSegment;

    /**
     * Default constructor
     */
    public CityMap() {
    }
    
    /**
     * Constructor
     */
    public CityMap(String _filePath) {
    	this.filePath = _filePath;
    }



    /**
     * 
     */
    public void fillMap() {
        // TODO implement here
    }
    
    /**
<<<<<<< HEAD
     * Returns the intersection with the specified ID
     * @param id
     */
    public Intersection getIntersection(long id) {
    	return listIntersection.get(id);
=======
     * 
     */
    public void fillIntersectionsList() {
    	try{  
  		  File file = new File(filePath);  
  		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
  		  DocumentBuilder db = dbf.newDocumentBuilder();  
  		  Document doc = db.parse(file);  
  		  doc.getDocumentElement().normalize();  
  		  NodeList intersectionsList = doc.getElementsByTagName("intersection");
  		  System.out.println("\nNumber of intersections: " + intersectionsList.getLength());
  		  for (int i = 0; i < intersectionsList.getLength(); i++) {  
  			  Node node = intersectionsList.item(i);  
  			  if (node.getNodeType() == Node.ELEMENT_NODE){ 
  				  Element eElement = (Element) node;  
  	  			  long id = Long.parseLong(eElement.getAttribute("id"));  
  	  			  float lat = Float.parseFloat(eElement.getAttribute("latitude"));  
  	  			  float longit = Float.parseFloat(eElement.getAttribute("longitude"));
  				  Intersection inters = new Intersection( lat, longit, id);
  				  listIntersection.put(id, inters);
  	  		  }  
  	  	  }
  	  }   
  	  catch (Exception e){  
  		  e.printStackTrace(); 
  	  }  
    }

    /**
     * 
     */
    public void fillSegmentsList() {
    	try{  
    		  File file = new File(filePath);  
    		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    		  DocumentBuilder db = dbf.newDocumentBuilder();  
    		  Document doc = db.parse(file);  
    		  doc.getDocumentElement().normalize();  
    		  NodeList segmentsList = doc.getElementsByTagName("segment");
    		  System.out.println("\nNumber of segments: " + segmentsList.getLength());
    		  for (int i = 0; i < segmentsList.getLength(); i++) {  
    			  Node node = segmentsList.item(i);  
    			  if (node.getNodeType() == Node.ELEMENT_NODE){ 
    				  Element eElement = (Element) node;  
    	  			  long idOrigin = Long.parseLong(eElement.getAttribute("origin"));  
    	  			  long idDest = Long.parseLong(eElement.getAttribute("destination"));  
    	  			  float length = Float.parseFloat(eElement.getAttribute("length"));  
    	  			  String name = eElement.getAttribute("name");
    	  			  //Use ids of intersection instead of objects
    				  Segment seg = new Segment();
    				  listSegment.put(idOrigin, seg);
    	  		  }  
    	  	  }
    	  }   
    	  catch (Exception e){  
    		  e.printStackTrace(); 
    	  }  
    }
    
    public HashMap<Long,Segment> getListSegment(){
    	return listSegment;
    }
    
    public HashMap<Long,Intersection> getListIntersection(){
    	return listIntersection;
>>>>>>> c892e7504a7f09b1a1b8e1a47c972a746a66f088
    }

}