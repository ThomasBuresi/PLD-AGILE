package controller;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import com.sun.tools.javac.util.Pair;

import model.CityMap;
import model.DeliveryTour;
import model.Intersection;
import model.RequestList;
import model.Segment;
import tsp.DijkstraGraph;
import tsp.TSP;
import view.GraphicalView;
import view.TextualView;
import view.Window;

/**
 * During this state we 
 * 
 * 
 * @author H4112
 *
 */

public class ContinueComputationState extends MapRequestsLoadedState{

	@Override
	public void continueCalculation(Controller controller, Window window) {
		//continue the calculation 
		TSP tsp = controller.getDeliveryTour().getTsp();
		DijkstraGraph g = controller.getDeliveryTour().getG();
		DeliveryTour d = new DeliveryTour(controller, tsp, g);
		d.fillDeliveryTour(20000);
		controller.setDeliveryTour(d);
		
		GraphicalView graphicalView = window.getGraphicalView();
        TextualView textualView = window.getTextualView();
 	    
        graphicalView.updateGraphicalCityMap(controller);
        textualView.update(controller);
		
        window.getTextualView().highlightTable(-1);
		window.setVisibleAddExport();
		controller.setCurrentState(controller.deliveryTourState);
		
	}
	@Override
	public void skipContinueCalculation(Controller controller, Window window) {
		//directly goes to the next state 
		window.setVisibleAddExport();
		window.getTextualView().highlightTable(-1);
		controller.setCurrentState(controller.deliveryTourState);
	}
	
	
}
