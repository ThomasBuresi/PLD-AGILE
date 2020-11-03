package view;

import java.awt.Color;
import java.awt.Graphics;
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
    	
    	System.out.println(requestList);
    	
    	int departure_py = height - (int)Math.round((requestList.getDeparture().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height)-5;
    	int departure_px = (int)Math.round((requestList.getDeparture().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width)-5;
    	System.out.println("departure : " + departure_px + " " + departure_py);
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