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
public class IntersectionView extends JPanel {
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
    public IntersectionView(HashMap<Long, Intersection> listIntersection, RequestList requestList, 
    		float latMin, float latMax, float longMin, float longMax) {
    	this.setLayout(null);
    	this.setBounds(0,0,900,500);
    	this.setBackground(Color.white);
    	this.listIntersection = listIntersection; 
    	this.requestList = requestList;
    	this.latMax = latMax;
    	this.latMin = latMin;
    	this.longMax = longMax;
    	this.longMin = longMin;
    	System.out.println("new intersection view");
    }
    
    @Override
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	Color colors[] = {Color.red, Color.green, Color.yellow, Color.cyan, Color.pink, Color.orange, Color.gray, Color.magenta};
    	List<Request> requests = requestList.getListRequests();
    	
    	System.out.println(requestList);
    	
    	int departure_px = (int)Math.round((requestList.getDeparture().getLatitude()-latMin)/(latMax-latMin)*this.getWidth())-5;
    	int departure_py = this.getHeight() - (int)Math.round((requestList.getDeparture().getLongitude()-longMin)/(longMax-longMin)*this.getHeight())-5;
    	System.out.println("departure : " + departure_px + " " + departure_py);
        g.fillRect(departure_px, departure_py, 10, 10);
      
    	
        System.out.println(" Min : "+longMin + " " + latMin+ " / Max :" + longMax + " " + latMax);
        
    	int col_counter = 0;
    	for (Request res : requests) {
    		g.setColor(colors[col_counter]);
    		g.fillRect((int)Math.round((res.getPickupAddress().getLatitude()-latMin)/(latMax-latMin)*this.getWidth())-5,
        			this.getHeight() - (int)Math.round((res.getPickupAddress().getLatitude()-latMin)/(latMax-latMin)*this.getHeight())-5, 
        			10, 10);
    		g.fillOval((int)Math.round((res.getDeliveryAddress().getLatitude()-latMin)/(latMax-latMin)*this.getWidth())-5,
        			this.getHeight() - (int)Math.round((res.getDeliveryAddress().getLatitude()-latMin)/(latMax-latMin)*this.getHeight())-5, 
        			10, 10);
    		if (col_counter == colors.length) 
    		{
    			col_counter = 0;
    		} else 
    		{
    			col_counter++;
    		}
    	}
    	
    	
    }

}