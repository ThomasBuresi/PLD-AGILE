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
 * 
 */
public class GraphicalIntersection {
	/**
     * 
     */
	protected HashMap<Long, Intersection> listIntersection;
	
	/**
	 * Array of the 4 intersections clicked to be added 
	 */
	protected ArrayList<Intersection> toBeAdded;
	
	/**
     * 
     */
	private RequestList requestList;
	
	/**
	 * 
	 */
	protected float latMin;
	
	/**
	 * 
	 */
	protected float latMax;
	
	/**
	 * 
	 */
	protected float longMin;
	
	/**
	 * 
	 */
	protected float longMax;
	
	/**
	 * 
	 */
	protected float latMinMap;
	
	/**
	 * 
	 */
	protected float latMaxMap;
	
	/**
	 * 
	 */
	protected float longMinMap;
	
	/**
	 * 
	 */
	protected float longMaxMap;
	
	/**
     * Default constructor
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
    
    public void drawIntersection(Graphics g, int height, int width) 
    {     
    	Color colors[] = {Color.red, Color.green, Color.yellow, Color.cyan, Color.pink, Color.orange, Color.gray, Color.magenta};
    	List<Request> requests = requestList.getListRequests();
    	
    	//System.out.println(requestList);
    	
    	int departure_py = height - (int)Math.round((requestList.getDeparture().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-5;
    	int departure_px = (int)Math.round((requestList.getDeparture().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-5;

        g.fillRect(departure_px, departure_py, 10, 10);
      
    	
        //System.out.println(" Min : "+longMin + " " + latMin+ " / Max :" + longMax + " " + latMax);
        
    	int col_counter = 0;
    	for (Request res : requests) {
    		g.setColor(colors[col_counter]);
    		g.fillRect((int)Math.round((res.getPickupAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-5,
        			height - (int)Math.round((res.getPickupAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-5, 
        			10, 10);
    		g.fillOval((int)Math.round((res.getDeliveryAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-5,
        			height - (int)Math.round((res.getDeliveryAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-5, 
        			10, 10);
    		if (col_counter == colors.length) 
    		{
    			col_counter = 0;
    		} else 
    		{
    			col_counter++;
    		}
    	}
    	g.setColor(Color.black);
    	
    	System.out.println("draw intersections, already selected a number of "+toBeAdded.size());
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

    public void drawHighlight(Graphics g, int height, int width,int id) {
    	Request r = requestList.getListRequests().get(id);
    	g.setColor(Color.black);
    	g.drawRect((int)Math.round((r.getPickupAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-10,
    			height - (int)Math.round((r.getPickupAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-10, 
    			20, 20);
		g.drawOval((int)Math.round((r.getDeliveryAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-10,
    			height - (int)Math.round((r.getDeliveryAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-10, 
    			20, 20);
    }
    
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
    
    public int getClickedRequestId(int xCoord,int yCoord, int panelHeight, int panelWidth) {
    	List<Request> list = requestList.getListRequests();
		
		int id = -1;
		
		for (Request r : list) {
			int xInterP = (int)Math.round((r.getPickupAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*panelWidth);
			int yInterP = panelHeight - (int)Math.round((r.getPickupAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*panelHeight);
			if ((xCoord >= xInterP - 5) && (xCoord<= xInterP + 5) && (yCoord >= yInterP - 5) && (yCoord<= yInterP + 5)) {
				id=r.getId();
			}
			int xInterD = (int)Math.round((r.getDeliveryAddress().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*panelWidth);
			int yInterD = panelHeight - (int)Math.round((r.getDeliveryAddress().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*panelHeight);
			if ((xCoord >= xInterD - 5) && (xCoord<= xInterD + 5) && (yCoord >= yInterD - 5) && (yCoord<= yInterD + 5)) {
				id=r.getId();
			}
		}
    	return id;
    }
    
    public void reInitializedToBeAdded() {
    	toBeAdded= new ArrayList<Intersection>();
    }
    
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

	public void resetCoord() {
		this.latMinMap = this.latMin;
		this.latMaxMap = this.latMax;
		this.longMinMap = this.longMin;
		this.longMaxMap = this.longMax;
	}

}