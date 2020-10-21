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
    	
    	setBounds(20,20,900,500);
        setBackground(Color.white);
    	
        // add Observers here 
    	
    	graphicalCityMap = new GraphicalCityMap(controller);
    	
    	add(graphicalCityMap);
    	repaint();
    }

//    public void paintComponent(Graphics g) 
//    {     
//    	super.paintComponent(g);
//    }
//    
     
    public void update() {
        // TODO implement here
    }


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}