package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JPanel;

import controller.Controller;
import model.CityMap;
import model.Intersection;

/**
 * 
 */
public class GraphicalCityMap extends JPanel {
	
	private HashMap<Long, Intersection> listIntersection;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
    	}
    	
    	setBounds(20,20,900,500);
        // to complete 
    	
    	if (listIntersection != null) {
    		intersectionView = new IntersectionView(listIntersection);            
            //segmentView=new SegmentView(listIntersection);
    		add(intersectionView);
            repaint();
            //add(intersectionView);
    	}
        
    	
    	
    
    }
    
    public void repaint(Graphics g) {
		super.repaint();
		paintComponent(g);
	}
    
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	g.drawOval(300, 300, 50, 50);
    	System.out.println("oval in graphicalcitymap.java");
    	
    	
    	//segmentView.paintComponent(g);
    	//intersectionView.paintComponent(g);
    	

    }


}