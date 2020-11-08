package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JPanel;

import controller.Controller;

/**
 * 
 */

public class GraphicalView extends JPanel{


	private static final long serialVersionUID = 1L;

	/**
	 * Containing the city map
	 */
	public GraphicalCityMap graphicalCityMap;
	
	/**
	 * Selected request id 
	 */
	private int id;
	
	/**
	 * It is true if the rectangle for the zone to zoom in should
	 * be drawn
	 */
	public boolean drawRect;
	
	/**
	 * Coordinates of the zone to draw for the zoom in
	 */
	public int[] coordRect;
		
    /**
     * Constructor of GraphicalView
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
    
    public void updateHighlight(int id) {
    	this.id=id;
    	repaint();
    }
    
    public void repaint(Graphics g) {
		super.repaint();
		paintComponent(g);
	}
    
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
    

	public GraphicalCityMap getGraphicalCityMap() {
		return graphicalCityMap;
	}

	public void setRectCoord(int pressedX, int pressedY, int width, int height) {
		coordRect[0] = pressedX;
		coordRect[1] = pressedY;
		coordRect[2] = width;
		coordRect[3] = height;
	}

	public void setDrawRect(boolean drawRect) {
		this.drawRect = drawRect;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	

}