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
    	System.out.println("new intersection view");
    }
    
    public void drawIntersection(Graphics g, int height, int width) 
    {     
    	Color colors[] = {Color.red, Color.green, Color.yellow, Color.cyan, Color.pink, Color.orange, Color.gray, Color.magenta};
    	List<Request> requests = requestList.getListRequests();
    	
    	System.out.println(requestList);
    	
    	int departure_py = height - (int)Math.round((requestList.getDeparture().getLatitude()-latMin)/(latMax-latMin)*height)-5;
    	int departure_px = (int)Math.round((requestList.getDeparture().getLongitude()-longMin)/(longMax-longMin)*width)-5;
    	System.out.println("departure : " + departure_px + " " + departure_py);
        g.fillRect(departure_px, departure_py, 10, 10);
      
    	
        //System.out.println(" Min : "+longMin + " " + latMin+ " / Max :" + longMax + " " + latMax);
        
    	int col_counter = 0;
    	for (Request res : requests) {
    		g.setColor(colors[col_counter]);
    		g.fillRect((int)Math.round((res.getPickupAddress().getLongitude()-longMin)/(longMax-longMin)*width)-5,
        			height - (int)Math.round((res.getPickupAddress().getLatitude()-latMin)/(latMax-latMin)*height)-5, 
        			10, 10);
    		g.fillOval((int)Math.round((res.getDeliveryAddress().getLongitude()-longMin)/(longMax-longMin)*width)-5,
        			height - (int)Math.round((res.getDeliveryAddress().getLatitude()-latMin)/(latMax-latMin)*height)-5, 
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

}