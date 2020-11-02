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
     * Default constructor
     */
    public GraphicalSegment(HashMap<Long, Intersection> listIntersection, float latMin, float latMax,
    		float longMin, float longMax) {

    	this.listIntersection = listIntersection; 
    	this.latMax = latMax;
    	this.latMin = latMin;
    	this.longMax = longMax;
    	this.longMin = longMin;
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
  			  int yOrig = height - (int)Math.round((s.getOrigin().getLatitude()-latMin)/(latMax-latMin)*height);
  			  int xOrig = (int)Math.round((s.getOrigin().getLongitude()-longMin)/(longMax-longMin)*width);
  			  int yDest = height - (int)Math.round((s.getDestination().getLatitude()-latMin)/(latMax-latMin)*height);
  			  int xDest = (int)Math.round((s.getDestination().getLongitude()-longMin)/(longMax-longMin)*width);
  			  //System.out.println(xOrig + " "+ yOrig + " " + xDest + " " + yDest);
  			  //System.out.println(s.getOrigin().getLatitude());
  			  g.drawLine(xOrig, yOrig, xDest, yDest);
  		  }
  		  //System.out.println(entry.getKey() + "/" + entry.getValue().toString());
  	  	}
        //System.out.println(i);
    }

}