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
 * 
 */
public class ButtonListener implements ActionListener {
	
	/**
	 * To open a dialog window to load the files 
	 */
//	private JFileChooser fc;
	
	/**
	 * Controller to perform the actions that require some processing 
	 */
	private Controller controller; 
	
	/**
	 * Associated window
	 */
	private Window window; //won't be needed normally
	
//	private GraphicalView graphicalView;
//	
//	private TextualView textualView;
//	
    /**
     * Constructor of ButtonListener
     * 
     * @param controller
     */
    public ButtonListener(Controller controller, Window window, GraphicalView graphicalView, TextualView textualView) {
    	this.controller=controller;
    	this.window = window;
//    	this.graphicalView = graphicalView;
//    	this.textualView = textualView;
//    	fc = new JFileChooser();
//    	fc.setCurrentDirectory( new File ( System.getProperty("user.dir") + 
//    			System.getProperty("file.separator")+ "src" + 
//    			System.getProperty("file.separator")+ "resources"));
    	
    }

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
			controller.continueCalculation(); //TODO
			//controller computation
			
			//it ends 
			//setVisibleAddExport in Window
			window.setVisibleAddExport(); //TODO delete when controller done
			//it doesn't end
			//set error mode ??  
			
			break;
		
		case "Add" :
			// TODO controller.changeToAddRequestMode();
			break;
		case "Remove" :
			break;
		case "Export Tour File" :
			//TODO controller.exportTour();
			//controller and delivery tour object 
			
			// no change in tg=he display ? or popup the export is complete ? 
			
			break;
		case "Undo" :
			break;
		case "Redo" :
			break;
			
		}
			
		
		
	}


}