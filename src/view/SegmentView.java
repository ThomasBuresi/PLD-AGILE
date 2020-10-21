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
public class SegmentView extends JPanel {

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
    public SegmentView(HashMap<Long, Intersection> listIntersection, float latMin, float latMax,
    		float longMin, float longMax) {

    	this.setLayout(null);
    	this.setBounds(0,0,900,500);
    	this.setBackground(Color.white);
    	this.listIntersection = listIntersection; 
    	this.latMax = latMax;
    	this.latMin = latMin;
    	this.longMax = longMax;
    	this.longMin = longMin;
    }
    
    //public Dimension getPreferredSize() {
    //    return new Dimension(900, 500);
    //}
    
    @Override
    public void paintComponent(Graphics g) 
    {     
    	
        super.paintComponent(g);
        //g.drawLine(0, 0, 900, 500);
        System.out.println("hii " + this.getWidth() + " " + this.getHeight());
        System.out.println(latMin + " "+ latMax + " " + longMin + " " + longMax);
        
        for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
  		  int i = entry.getValue().getListSegments().size();
  		  //System.out.println(i);
  		  List<Segment> seg = entry.getValue().getListSegments();
  		  for (Segment s : seg) {
  			  int yOrig = (int)Math.round((s.getOrigin().getLatitude()-latMin)/(latMax-latMin)*this.getHeight());
  			  int xOrig = (int)Math.round((s.getOrigin().getLongitude()-longMin)/(longMax-longMin)*this.getWidth());
  			  int yDest = (int)Math.round((s.getDestination().getLatitude()-latMin)/(latMax-latMin)*this.getHeight());
  			  int xDest = (int)Math.round((s.getDestination().getLongitude()-longMin)/(longMax-longMin)*this.getWidth());
  			  System.out.println(xOrig + " "+ yOrig + " " + xDest + " " + yDest);
  			  //System.out.println(s.getOrigin().getLatitude());
  			  g.drawLine(xOrig, yOrig, xDest, yDest);
  		  }
  		  //System.out.println(entry.getKey() + "/" + entry.getValue().toString());
  	  	}
    }

}