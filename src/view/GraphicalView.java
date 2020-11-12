package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JPanel;

import controller.Controller;
import model.Intersection;

/**
 * Global GraphicalView containing the main GraphicalCityMap and the zoom functions.
 * 
 * @author H4112
 */

public class GraphicalView extends JPanel{


	private static final long serialVersionUID = 1L;

	/**
	 * Containing the city map.
	 */
	public GraphicalCityMap graphicalCityMap;
	
	/**
	 * Selected request id.
	 */
	private int id;
	
	/**
	 * It is true if the rectangle for the zone to zoom in should
	 * be drawn.
	 */
	public boolean drawRect;
	
	
	/**
	 * Coordinates of the zone to draw for the zoom in.
	 */
	public int[] coordRect;
		
    /**
     * Constructor of GraphicalView.
     * 
     * @param controller
     */
    public GraphicalView(Controller controller) {
    	this.setLayout(null);
    	this.setBounds(20,20,900,500);
        setBackground(Color.white);
    	
    	graphicalCityMap = new GraphicalCityMap(controller);
    	id=-1;
    	drawRect = false;
    	
    	coordRect = new int[4];
    	repaint();
    }
    
    /**
     * Update graphical city map to take into account the change of CityMap
     * @param controller
     */
    public void updateGraphicalCityMap(Controller controller) {
    	graphicalCityMap = new GraphicalCityMap(controller);
    	repaint();
    }
    
    /**
     * Update the highlight of a request
     * @param id of the request to highlight 
     */
    public void updateHighlight(int id) {
    	this.id=id;
    	repaint();
    }
    
    /**
     * Update the selected intersections in add mode
     * @param on true if switch on mode
     */
    public void updateSelection(boolean on,Intersection i) {
    	graphicalCityMap.addMode=on;
    	if(i!=null)graphicalCityMap.setClickedIntersection(i);
    }
    
    /**
     * Repaint the graphical view
     * @param g
     */
    public void repaint(Graphics g) {
		super.repaint();
		paintComponent(g);
	}
    
    /**
     * Draw the map and the zoom if it's the case. 
     */
    @Override
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	graphicalCityMap.drawGraphicalCityMap(g, this.getHeight(), this.getWidth(),id);
    	if (drawRect) {
    		g.setColor(Color.red);
    		g.drawRect(coordRect[0], coordRect[1], coordRect[2], coordRect[3]);
    		g.setColor(new Color(255, 0, 0, 50));
    		g.fillRect(coordRect[0], coordRect[1], coordRect[2], coordRect[3]);
    	}
    	
    }
    
    /**
     * Getter of the graphical representation of the CityMap
     * @return GraphicalCityMap
     */
	public GraphicalCityMap getGraphicalCityMap() {
		return graphicalCityMap;
	}

	/**
	 * Set the coordinates of the zoom in rectangle.
	 * 
	 * @param pressedX
	 * @param pressedY
	 * @param width
	 * @param height
	 */
	public void setRectCoord(int pressedX, int pressedY, int width, int height) {
		coordRect[0] = pressedX;
		coordRect[1] = pressedY;
		coordRect[2] = width;
		coordRect[3] = height;
	}
	
	/**
	 * Setter of the rectangle to draw.
	 * 
	 * @param drawRect
	 */
	public void setDrawRect(boolean drawRect) {
		this.drawRect = drawRect;
	}
	
	/**
	 * Setter of the id of the selected request to highlight.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id=id;
	}
	
	

}