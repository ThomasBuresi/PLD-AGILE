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