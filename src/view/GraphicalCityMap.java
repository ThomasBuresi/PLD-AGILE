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
 * 
 */
public class GraphicalCityMap {
	
	private HashMap<Long, Intersection> listIntersection;
	private RequestList requestList;
	private DeliveryTour deliveryTour;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	/**
	 * True if we are in add mode and should represent the selected intersections
	 */
	public boolean addMode;
	
	/** 
	 * Clicked intersection
	 */
	public Intersection i;
	
	
	private GraphicalSegment graphicalSegment;
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
    
    public void drawGraphicalCityMap(Graphics g, int height, int width,int id) 
    {     
    	if (graphicalIntersection != null) {
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
    	
    	
    	if(addMode) {
    		graphicalIntersection.addSelectedIntersection(i);
    		addMode=false;
    	}
    	
    	
    		
    	//int id 
    }
   

	public HashMap<Long, Intersection> getListIntersection() {
		return listIntersection;
	}

	public RequestList getRequestList() {
		return requestList;
	}


	public DeliveryTour getDeliveryTour() {
		return deliveryTour;
	}

	public GraphicalSegment getGraphicalSegment() {
		return graphicalSegment;
	}

	public GraphicalIntersection getGraphicalIntersection() {
		return graphicalIntersection;
	}
	
	public void setClickedIntersection(Intersection i) {
		this.i=i;
	}
    
}