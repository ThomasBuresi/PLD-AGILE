package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JPanel;

import controller.Controller;

/**
 * 
 */
@SuppressWarnings("deprecation")
public class GraphicalView extends JPanel implements Observer {

	
	/**
	 * Containing the city map
	 */
	public GraphicalCityMap graphicalCityMap;
		
    /**
     * Default constructor
     */
    public GraphicalView(Controller controller) {
    	this.setLayout(null);
    	this.setBounds(20,20,900,500);
        setBackground(Color.white);
        //this.setOpaque(false);
    	System.out.println("view " + this.getWidth() + " " + this.getHeight());
        // add Observers here 
    	
    	graphicalCityMap = new GraphicalCityMap(controller);
    	
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
    
    public void repaint(Graphics g) {
		super.repaint();
		paintComponent(g);
	}
    
    @Override
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	graphicalCityMap.drawGraphicalCityMap(g, this.getHeight(), this.getWidth());
    }
    
     
    public void update() {
        // TODO implement here
    }


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}