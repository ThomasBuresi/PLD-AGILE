package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JPanel;

import model.Intersection;
import model.Segment;

/**
 * 
 */
public class GraphicalSegment {

	protected HashMap<Long, Intersection> listIntersection;
	
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
    public GraphicalSegment(HashMap<Long, Intersection> listIntersection, float latMin, float latMax,
    		float longMin, float longMax) {

    	this.listIntersection = listIntersection; 
    	this.latMax = latMax;
    	this.latMin = latMin;
    	this.longMax = longMax;
    	this.longMin = longMin;
    	this.latMinMap = latMin;
    	this.latMaxMap = latMax;
    	this.longMinMap = longMin;
    	this.longMaxMap = longMax;
    }
    
    //public Dimension getPreferredSize() {
    //    return new Dimension(900, 500);
    //}
    
    public void drawSegment(Graphics g, int height, int width) 
    {     
        //g.drawLine(0, 0, 900, 500);
        // System.out.println("hii " + width + " " + height);
        // System.out.println(latMin + " "+ latMax + " " + longMin + " " + longMax);
        int i = 0;
        for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
  		  i += entry.getValue().getListSegments().size();
  		  //System.out.println(i);
  		  List<Segment> seg = entry.getValue().getListSegments();
  		  for (Segment s : seg) {
  			  int yOrig = height - (int)Math.round((s.getOrigin().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height);
  			  int xOrig = (int)Math.round((s.getOrigin().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width);
  			  int yDest = height - (int)Math.round((s.getDestination().getLatitude()-latMinMap)/(latMaxMap-latMinMap)*height);
  			  int xDest = (int)Math.round((s.getDestination().getLongitude()-longMinMap)/(longMaxMap-longMinMap)*width);
  			  //System.out.println(xOrig + " "+ yOrig + " " + xDest + " " + yDest);
  			  //System.out.println(s.getOrigin().getLatitude());
  			  g.drawLine(xOrig, yOrig, xDest, yDest);
  		  }
  		  //System.out.println(entry.getKey() + "/" + entry.getValue().toString());
  	  	}
        //System.out.println(i);
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

	public void resetCoord() {
		this.latMinMap = this.latMin;
		this.latMaxMap = this.latMax;
		this.longMinMap = this.longMin;
		this.longMaxMap = this.longMax;
	}

}