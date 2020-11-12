package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

import javax.swing.JPanel;

import model.Intersection;
import model.Request;
import model.RequestList;

/**
 * Graphical representation for the map of all the intersections. Highlighted or not. 
 * An intersection correspond to any point that is clickable in the add mode. 
 * All the intersections are linked by segments that represented in GraphicalSegment.
 * 
 * @authors H4112 
 */
public class GraphicalIntersection {
	
	/**
     * List of the intersections that need to be displayed. 
     */
	protected HashMap<Long, Intersection> listIntersection;
	
	/**
	 * Array of the 4 intersections clicked to be added 
	 */
	protected ArrayList<Intersection> toBeAdded;
	
	/**
     * List of the requests corresponding to the map that will be drawn, and highlighted if necessary. 
     */
	private RequestList requestList;
	
	/**
	 * The minimal latitude of the loaded map 
	 */
	protected float latMin;
	
	/**
	 * The maximal latitude of the loaded map 
	 */
	protected float latMax;
	
	/**
	 * The minimal longitude of the loaded map 
	 */
	protected float longMin;
	
	/**
	 * The maximal longitude of the loaded map 
	 */
	protected float longMax;
	
	/**
	 * The minimal latitude of the loaded map that is shown
	 * at the moment (if zoomed in)
	 */
	protected float latMinMap;
	
	/**
	 * The maximal latitude of the loaded map that is shown
	 * at the moment (if zoomed in)
	 */
	protected float latMaxMap;
	
	/**
	 * The minimal latitude of the loaded map that is shown
	 * at the moment (if zoomed in)
	 */
	protected float longMinMap;
	
	/**
	 * The minimal latitude of the loaded map that is shown
	 * at the moment (if zoomed in)
	 */
	protected float longMaxMap;
	
	/**
	 * Constructor of the graphical representation of the intersections.
	 * 
	 * @param listIntersection
	 * @param requestList
	 * @param latMin
	 * @param latMax
	 * @param longMin
	 * @param longMax
	 */
    public GraphicalIntersection(HashMap<Long, Intersection> listIntersection, RequestList requestList, 
    		float latMin, float latMax, float longMin, float longMax) {
    	this.listIntersection = listIntersection; 
    	toBeAdded = new ArrayList<Intersection>();
    	this.requestList = requestList;
    	this.latMax = latMax;
    	this.latMin = latMin;
    	this.longMax = longMax;
    	this.longMin = longMin;
    	this.latMinMap = latMin;
    	this.latMaxMap = latMax;
    	this.longMinMap = longMin;
    	this.longMaxMap = longMax;
    	System.out.println("new intersection view");
    }
    
    /**
     * Method to draw the intersections of the Request List. 
     * Each pair of intersections (pickup and delivery) of the request will be drawn in the same color. 
     * (for up to 17 requests the colors will be unique, over there will be some duplicates)
     * 
     * @param g
     * @param height
     * @param width
     */
    public void drawIntersection(Graphics g, int height, int width) 
    {     
    	List<Color> color = new ArrayList<Color>();
    	
    	color.add(Color.red);
    	color.add(Color.green);
    	color.add(Color.yellow);
    	color.add(Color.cyan);
    	color.add(Color.pink);
    	color.add(Color.orange);
    	color.add(Color.gray);
    	color.add(Color.magenta);
    	
    	color.add(new Color(62, 98, 89));
    	color.add(new Color(98, 146, 158));
    	color.add(new Color(130, 51, 41));
    	color.add(new Color(84, 106, 123));
    	color.add(new Color(138, 48, 51));
    	color.add(new Color(184, 140, 158));
    	color.add(new Color(164, 48, 63));
    	color.add(new Color(200, 214, 175));
    	color.add(new Color(162, 112, 53));
    	
    	Color colors[] = color.toArray(new Color[color.size()]);
    	List<Request> requests = requestList.getListRequests();
    	
    	int departure_py = height - (int)Math.round((requestList.getDeparture().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-5;
    	int departure_px = (int)Math.round((requestList.getDeparture().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-5;

        g.fillRect(departure_px, departure_py, 10, 10);
        
    	int col_counter = 0;
    	for (Request res : requests) {
    		if (col_counter == colors.length) {
    			col_counter = 0;
    		}
    		g.setColor(colors[col_counter]);
    		g.fillRect((int)Math.round((res.getPickupAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-5,
        			height - (int)Math.round((res.getPickupAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-5, 
        			10, 10);
    		g.fillOval((int)Math.round((res.getDeliveryAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-5,
        			height - (int)Math.round((res.getDeliveryAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-5, 
        			10, 10);
    		col_counter++;
    	}
    	g.setColor(Color.black);
    	
    	//System.out.println("draw intersections, already selected a number of "+toBeAdded.size());
    	if(!toBeAdded.isEmpty()) {
    		int k=0;
    		for(Intersection i : toBeAdded) {
        		if(k<2) {
            		
                	float x = i.getLatitude();
                	float y = i.getLongitude();
                	g.drawRect((int)Math.round((y-longMinMap)/(longMaxMap-longMinMap)*width)-10,
                			height - (int)Math.round((x-latMinMap)/(latMaxMap-latMinMap)*height)-10, 
                			20, 20);
            	}else {
            		
                	float x = i.getLatitude();
                	float y = i.getLongitude();
                	g.drawOval((int)Math.round((y-longMinMap)/(longMaxMap-longMinMap)*width)-10,
                			height - (int)Math.round((x-latMinMap)/(latMaxMap-latMinMap)*height)-10, 
                			20, 20);
            	}
        		k++;
        	}
    	}
    	
    }

    /**
     * Method to draw a highlight for a given request that was selected by the user.
     * An highlight si a black rectangle or circle drawn around the intersections of such a request. 
     * 
     * @param g
     * @param height
     * @param width
     * @param id the id of the selected request to be highlighted
     */
    public void drawHighlight(Graphics g, int height, int width,int id) {
    	Request r = null;
    	for (Request res : requestList.getListRequests()) {
    		if (res.getId() == id) {
    			r = res;
    		}
    	}
    	if (r!= null) {
	    	g.setColor(Color.black);
	    	g.drawRect((int)Math.round((r.getPickupAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-10,
	    			height - (int)Math.round((r.getPickupAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-10, 
	    			20, 20);
			g.drawOval((int)Math.round((r.getDeliveryAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-10,
	    			height - (int)Math.round((r.getDeliveryAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-10, 
	    			20, 20);
    	}
    }
    
    /**
     * Method designed for the add mode to add the intersections to be added under certain conditions. 
     * The conditions are that the second and 4rth points to be added need to already be in a request 
     * otherwise they are not accepted. 
     * 
     * @param i the intersection 
     */
    public void addSelectedIntersection(Intersection i) {
    	if (toBeAdded.size() == 1 || toBeAdded.size() == 3){
    		List <Request> res = requestList.getListRequests();
    		for (Request r : res) {
    			if (r.getDeliveryAddress().getIdIntersection() == i.getIdIntersection() || 
    					r.getPickupAddress().getIdIntersection() == i.getIdIntersection()) {
    				toBeAdded.add(i);
    				return;
    			}
    		}
    	} else if (toBeAdded.size() == 0 || toBeAdded.size() == 2) {
    		toBeAdded.add(i);
    	}

    }
    

    /**
     * From some coordinated on the map it will return the corresponding clicked intersection.
     * If the coordinates don't correspond to an intersection, it returns null. 
     * 
     * @param xCoord
     * @param yCoord
     * @param panelHeight
     * @param panelWidth
     * @return Intersection that was clicked or null 
     */
    public Intersection getClickedIntersection(int xCoord,int yCoord, int panelHeight, int panelWidth) {
    	for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
			int xInter = (int)Math.round((entry.getValue().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*panelWidth);
			int yInter = panelHeight - (int)Math.round((entry.getValue().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*panelHeight);
			
			if ((xCoord >= xInter - 5) && (xCoord<= xInter + 5) && (yCoord >= yInter - 5) && (yCoord<= yInter + 5)) {
				return entry.getValue();
			}
		}
    	return null;
    }
    
    /**
     * From some coordinated on the map it will return the corresponding clicked request Id, 
     * or many if there were many request at this location.
     * If the coordinates don't correspond to a request, it returns an empty list. 
     * 
     * @param xCoord
     * @param yCoord
     * @param panelHeight
     * @param panelWidth
     * @return List of Ids of resquests that are Intergers 
     */
    public List<Integer> getClickedRequestId(int xCoord,int yCoord, int panelHeight, int panelWidth) {
    	List<Request> list = requestList.getListRequests();
		
    	List<Integer> id = new ArrayList<Integer>();
		
		for (Request r : list) {
			int xInterP = (int)Math.round((r.getPickupAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*panelWidth);
			int yInterP = panelHeight - (int)Math.round((r.getPickupAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*panelHeight);
			if ((xCoord >= xInterP - 5) && (xCoord<= xInterP + 5) && (yCoord >= yInterP - 5) && (yCoord<= yInterP + 5)) {
				id.add(r.getId());
			}
			int xInterD = (int)Math.round((r.getDeliveryAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*panelWidth);
			int yInterD = panelHeight - (int)Math.round((r.getDeliveryAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*panelHeight);
			if ((xCoord >= xInterD - 5) && (xCoord<= xInterD + 5) && (yCoord >= yInterD - 5) && (yCoord<= yInterD + 5)) {
				id.add(r.getId());
			}
		}
    	return id;
    }
    
    
    /**
     * Re initialise the list of points to be added.
     */
    public void reInitializedToBeAdded() {
    	toBeAdded= new ArrayList<Intersection>();
    }
    
    /**
     * Reset the coordinates of the shown map to the default ones :
     * Zoom out
     */
    public void resetCoord() {
		this.latMinMap = this.latMin;
		this.latMaxMap = this.latMax;
		this.longMinMap = this.longMin;
		this.longMaxMap = this.longMax;
	}
    
    /**
     * Getter of the list of the intersections to be added 
     * 
     * @return ArrayList<Intersection> 
     */
    public ArrayList<Intersection> getToBeAdded() {
    	return toBeAdded;
    }
    

	public void setLatMinMap(float latMinMap) {
		this.latMinMap = latMinMap;
	}

	public void setLatMaxMap(float latMaxMap) {
		this.latMaxMap = latMaxMap;
	}

	public void setLongMinMap(float longMinMap) {
		this.longMinMap = longMinMap;
	}

	public void setLongMaxMap(float longMaxMap) {
		this.longMaxMap = longMaxMap;
	}

	

}