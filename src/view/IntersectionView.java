package view;

import java.awt.Graphics;
import java.util.*;

import javax.swing.JPanel;

import model.Intersection;

/**
 * 
 */
public class IntersectionView extends JPanel {

	protected HashMap<Long, Intersection> listIntersection;
	
	/**
     * Default constructor
     */
    public IntersectionView(HashMap<Long, Intersection> listIntersection) {
    	this.listIntersection = listIntersection;    	
    }
    
    
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	g.drawOval(500, 500, 50, 50);
    	System.out.println("test oval");
    }

}