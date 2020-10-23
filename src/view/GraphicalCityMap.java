package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JPanel;

import controller.Controller;
import model.CityMap;
import model.Intersection;
import model.RequestList;

/**
 * 
 */
public class GraphicalCityMap extends JPanel {
	
	private HashMap<Long, Intersection> listIntersection;
	private RequestList requestList;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	public SegmentView segmentView;
	public IntersectionView intersectionView;

    /**
     * Default constructor
     */
    public GraphicalCityMap(Controller controller) {
    	//super();
    	if (controller.getCityMap() != null) {
    		listIntersection = controller.getCityMap().getListIntersection();
    	} else {
    		listIntersection = null;
    		requestList = null;
    		System.err.println("listIntersection is null");
    	}
    	if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    	} else {
    		requestList = null;
    		System.err.println("RequestList is null");
    	}
    	
    	this.setLayout(null);
    	this.setBounds(0,0,900,500);
    	this.setBackground(Color.white);
    	//this.setOpaque(false);
    	System.out.println("city view " + this.getWidth() + " " + this.getHeight());
        // to complete 
    	
    	if (listIntersection != null) {   
    		System.out.println("intersection List not null");
            segmentView=new SegmentView(listIntersection, controller.getCityMap().getLatMin(),
            		controller.getCityMap().getLatMax(), controller.getCityMap().getLongMin(),
            		controller.getCityMap().getLongMax());
            this.add(segmentView);
            repaint();
    	}
    	
    	if (requestList != null) {
    		System.out.println("request List not null");
    		intersectionView = new IntersectionView(listIntersection, requestList,  
    				controller.getCityMap().getLatMin(), controller.getCityMap().getLatMax(), 
    				controller.getCityMap().getLongMin(), controller.getCityMap().getLongMax());           
            this.add(intersectionView);
            repaint();
    	}
    
    }
    
    public void repaint(Graphics g) {
		super.repaint();
		paintComponent(g);
	}
    
    @Override
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	//g.drawLine(20, 20, 920, 520);
    	//g.drawOval(300, 300, 50, 50);
    	//System.out.println("oval in intersectionview.java");
    	
    	//intersectionView.paintComponent(g);
    	//segmentView.paintComponent(g);
    	

    }


}