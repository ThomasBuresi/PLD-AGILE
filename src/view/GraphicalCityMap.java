package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JPanel;

import model.CityMap;

/**
 * 
 */
public class GraphicalCityMap extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SegmentView segmentView;
	public IntersectionView intersectionView;

    /**
     * Default constructor
     */
    public GraphicalCityMap() {
    	//super();
    	
    	
    	setBounds(20,20,900,500);
        // to complete 
        intersectionView = new IntersectionView();
        
        segmentView=new SegmentView();

        
        repaint();
        //add(intersectionView);
    
    }
    
    public void repaint(Graphics g) {
		super.repaint();
		paintComponent(g);
	}
    
    public void paintComponent(Graphics g) 
    {     
    	//super.paintComponent(g);
    	
    	segmentView.paintComponent(g);
    	//intersectionView.paintComponent(g);
    	

    }


}