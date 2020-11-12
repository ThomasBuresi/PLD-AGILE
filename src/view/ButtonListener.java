package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;

import com.sun.tools.javac.util.Pair;

import controller.Controller;
import model.CityMap;
import model.DeliveryTour;
import model.Intersection;
import model.RequestList;
import model.Segment;
import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP1;

/**
 * For every button, it will call the corresponding method in the controller. 
 * It will launch a serie of actions and change the states and views. 
 * 
 * @author H4112
 */
public class ButtonListener implements ActionListener {
	
	/**
	 * Controller to perform the actions that require some processing 
	 */
	private Controller controller; 
	

    /**
     * Constructor of ButtonListener
     * 
     * @param controller
     */
    public ButtonListener(Controller controller) {
    	this.controller=controller;
    	
    }
    
    /**
     * Split the actions in function of the buttons. 
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Load Map":
			controller.loadMapFile();
			break;
			
		case "Load Requests":
			controller.loadRequestsFile();
			break;
			
		case "-" :
			controller.zoomOut();
			break;
			
		case"Calculate Delivery Tour" : 
			controller.computeDeliveryTour();			
			break;
			
		case "Continue calculation (20sec more)":
			controller.continueCalculation(); 
			break;
			
		case "Skip":
			controller.skipContinueCalculation();
			
			break;
		
		case "Add" :
			controller.changeToAddRequestMode();
			break;
			
		case "Confirm Add":
			controller.addRequest();
			break;
			
		case "Re initialise the selection":
			controller.reInitialiseSelection();
			break;
			
		case "Remove" :
			controller.removeRequest();
			
			break;
			
		case "Cancel":
			controller.cancel();
			break;
			
		case "Export Tour File" :
			controller.exportTour();
			break;
			
		case "Undo" :
			controller.undo();
			break;
			
		case "Redo" :
			controller.redo();
			break;
			
		}	
	}
}