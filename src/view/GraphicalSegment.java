package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JPanel;

import com.sun.tools.javac.util.Pair;

import model.DeliveryTour;
import model.Intersection;
import model.Segment;

/**
 * Graphical representation for the map of all the segments. Highlighted or not. 
 * The segments can be highlighted to represent the tour.
 * 
 * @author H4122
 */
public class GraphicalSegment {

	/**
	 * List of the intersections contained in the map.
	 */
	private HashMap<Long, Intersection> listIntersection;
	
	/**
	 * Delivery tour that we will have to print on the map by changing the representation of some segments. 
	 */
	protected DeliveryTour deliveryTour;
	
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
     * Constructor of GraphicalSegment without the delivery tour.
     * 
     * @param listIntersection
     * @param latMin
     * @param latMax
     * @param longMin
     * @param longMax
     */
    public GraphicalSegment(HashMap<Long, Intersection> listIntersection, float latMin, float latMax,
    		float longMin, float longMax) {

    	this.listIntersection = listIntersection; 
    	this.deliveryTour = null;
    	this.latMax = latMax;
    	this.latMin = latMin;
    	this.longMax = longMax;
    	this.longMin = longMin;
    	this.latMinMap = latMin;
    	this.latMaxMap = latMax;
    	this.longMinMap = longMin;
    	this.longMaxMap = longMax;
    }
    
    /**
     * Constructor of GraphicalSegment with the delivery tour.
     * 
     * @param listIntersection
     * @param deliveryTour
     * @param latMin
     * @param latMax
     * @param longMin
     * @param longMax
     */
    public GraphicalSegment(HashMap<Long, Intersection> listIntersection, DeliveryTour deliveryTour, 
    		float latMin, float latMax, float longMin, float longMax) {

    	this.listIntersection = listIntersection;
    	this.deliveryTour = deliveryTour;
    	this.latMax = latMax;
    	this.latMin = latMin;
    	this.longMax = longMax;
    	this.longMin = longMin;
    	this.latMinMap = latMin;
    	this.latMaxMap = latMax;
    	this.longMinMap = longMin;
    	this.longMaxMap = longMax;
    }
    
    /**
     * Draw all the black segments according to the size of the map and the data. 
     * 
     * @param g
     * @param height
     * @param width
     */
    public void drawSegment(Graphics g, int height, int width) 
    {     
        for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
  		  List<Segment> seg = entry.getValue().getListSegments();
  		  for (Segment s : seg) {
  			  int yOrig = height - (int)Math.round((s.getOrigin().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height);
  			  int xOrig = (int)Math.round((s.getOrigin().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width);
  			  int yDest = height - (int)Math.round((s.getDestination().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height);
  			  int xDest = (int)Math.round((s.getDestination().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width);
  			  g.drawLine(xOrig, yOrig, xDest, yDest);
  		  }
  	  	}
    }
    
    /**
     * Draw the delivery tour over the other segments that were already drawn but in colors. 
     * 
     * @param g
     * @param height
     * @param width
     */
    public void drawTour(Graphics g, int height, int width) 
    {     
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setStroke(new BasicStroke(2f)); 
    	
    	List<Color> colors = new ArrayList<Color>();
    	for (int r=0; r<100; r++) colors.add(new Color(r*255/100,       255,         0));
    	for (int gr=100; gr>0; gr--) colors.add(new Color(      255, gr*255/100,         0));
    	for (int b=0; b<100; b++) colors.add(new Color(      255,         0, b*255/100));
    	for (int r=100; r>0; r--) colors.add(new Color(r*255/100,         0,       255));
    	for (int gr=0; gr<100; gr++) colors.add(new Color(        0, gr*255/100,       255));
    	for (int b=100; b>0; b--) colors.add(new Color(        0,       255, b*255/100));
    	colors.add(new Color(        0,       255,         0));
    	Color[] c = colors.toArray(new Color[colors.size()]);
    	
    	
    	                          
    	List <Pair<Intersection, List<Segment>>> tour = deliveryTour.getTour();
    	int i = 0;
        for (Pair<Intersection, List<Segment>> pair : tour) {
  		  //i += entry.getValue().getListSegments().size();
  		  List<Segment> seg =  pair.snd;
  		  if(seg!=null) {
  			//System.out.println(pair.fst.getName());
  			for (Segment s : seg) {
  				if (i == c.length) i=0;
    			int yOrig = height - (int)Math.round((s.getOrigin().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height);
    			int xOrig = (int)Math.round((s.getOrigin().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width);
    			int yDest = height - (int)Math.round((s.getDestination().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height);
    			int xDest = (int)Math.round((s.getDestination().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width);
    			
    			g2d.setColor(c[i]);
    			i++;
    			g2d.drawLine(xOrig, yOrig, xDest, yDest);
    		  }
  		  }
  	  	}
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

	public float getLatMinMap() {
		return latMinMap;
	}

	public float getLatMaxMap() {
		return latMaxMap;
	}

	public float getLongMinMap() {
		return longMinMap;
	}

	public float getLongMaxMap() {
		return longMaxMap;
	}

	

}