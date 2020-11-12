package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JPanel;

import controller.Controller;
import model.CityMap;
import model.DeliveryTour;
import model.Intersection;
import model.RequestList;

/**
 * This class groups all the graphical elements of the city map. 
 * It is used as the view of the CityMap of the model. 
 * 
 * @author H4112
 */
public class GraphicalCityMap {
	
	/**
	 * List of the intersections contained in the map.
	 */
	private HashMap<Long, Intersection> listIntersection;
	
	/**
	 * List of the requests corresponding to the map that will be highlighted on it if necessary. 
	 */
	private RequestList requestList;
	
	/**
	 * Delivery tour that we will have to print on the map. 
	 */
	private DeliveryTour deliveryTour;
	
	/**
	 * True if we are in add mode and should represent the selected intersections
	 */
	public boolean addMode;
	
	/** 
	 * Clicked intersection
	 */
	public Intersection i;
	
	/**
	 * Graphical representation for the map of all the segments. Highlighted or not. 
	 */
	private GraphicalSegment graphicalSegment;
	
	/**
	 * Graphical representation for the map of all the intersections. Highlighted or not. 
	 */
	private GraphicalIntersection graphicalIntersection;
	
	

    /**
     * Constructor of GraphicalCityMap
     * @param controller
     */
    public GraphicalCityMap(Controller controller) {
    	
    	addMode=false;
    	
    	if (controller.getCityMap() != null) {
    		listIntersection = controller.getCityMap().getListIntersection();
    	} else {
    		listIntersection = null;
    		System.err.println("listIntersection is null");
    	}
    	
    	if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    	} else {
    		requestList = null;
    		System.err.println("RequestList is null");
    	}
    	
    	if (controller.getDeliveryTour() != null) {
    		deliveryTour = controller.getDeliveryTour();
    	} else {
    		deliveryTour = null;
    		System.err.println("DeliveryTour is null");
    	}
    	
    	
    	if (listIntersection != null) {  
    		if (deliveryTour == null) {
        		System.out.println("Intersection List not null");
                graphicalSegment=new GraphicalSegment(listIntersection, controller.getCityMap().getLatMin(),
                		controller.getCityMap().getLatMax(), controller.getCityMap().getLongMin(),
                		controller.getCityMap().getLongMax());            
    		} else {
    			System.out.println("DeliveryTour not null");
                graphicalSegment=new GraphicalSegment(listIntersection, deliveryTour, controller.getCityMap().getLatMin(),
                		controller.getCityMap().getLatMax(), controller.getCityMap().getLongMin(),
                		controller.getCityMap().getLongMax());       
    		}
    	}
    	
    	if (requestList != null) {
    		System.out.println("request List not null");
    		graphicalIntersection = new GraphicalIntersection(listIntersection, requestList,  
    				controller.getCityMap().getLatMin(), controller.getCityMap().getLatMax(), 
    				controller.getCityMap().getLongMin(), controller.getCityMap().getLongMax());  
    	}
    
    }
    
    
    /**
     * Draw all the elements of the map in function of various parameters. For example,
     * if there is a request to highlight or not, a tour to print or not. 
     * 
     * @param g the graphics
     * @param height
     * @param width
     * @param id of the request to highlight 
     */
    public void drawGraphicalCityMap(Graphics g, int height, int width,int id) 
    {     
    	
    	if (graphicalIntersection != null) {
    		if(addMode) {
        		graphicalIntersection.addSelectedIntersection(i);
        		addMode=false;
        	}
    		graphicalIntersection.drawIntersection(g, height, width);
    		
    	}
    	if (graphicalSegment != null) {
    		graphicalSegment.drawSegment(g, height, width);
    	}
    	if(deliveryTour!=null) {
    		graphicalSegment.drawTour(g, height, width);
    	}
    	if(id!=-1) {
    		graphicalIntersection.drawHighlight(g, height, width, id);
    	}
    	
    }
   

    /**
     * Getter of the list of the intersections. 
     * 
     * @return ListIntersection
     */
	public HashMap<Long, Intersection> getListIntersection() {
		return listIntersection;
	}

	/**
	 * Getter of the object RequestList containing the list of requests and other attributes. 
	 * 
	 * @return RequestList
	 */
	public RequestList getRequestList() {
		return requestList;
	}

	/**
	 *Getter of the DeliveryTour 
	 * 
	 * @return DeliveryTour
	 */
	public DeliveryTour getDeliveryTour() {
		return deliveryTour;
	}
	
	/**
	 * Getter of the graphical representation of the segments of the map.
	 * 
	 * @return GraphicalSegment
	 */
	public GraphicalSegment getGraphicalSegment() {
		return graphicalSegment;
	}

	/**
	 * Getter of the graphical representation of the intersections of the map. 
	 * 
	 * @return GraphicalIntersection
	 */
	public GraphicalIntersection getGraphicalIntersection() {
		return graphicalIntersection;
	}
	
	/**
	 * Setter of the intersection that was clicked. 
	 * 
	 * @param i the intersection that is clicked for the purpose of the add mode. 
	 */
	public void setClickedIntersection(Intersection i) {
		this.i=i;
	}
    
}