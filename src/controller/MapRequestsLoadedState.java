package controller;

import model.DeliveryTour;
import view.GraphicalView;
import view.TextualView;
import view.Window;

public class MapRequestsLoadedState extends MapLoadedState{
	
	@Override
	public void computeDeliveryTour(Controller controller, Window window) {
		DeliveryTour d = new DeliveryTour(controller);
		d.fillDeliveryTour(5000);
		controller.setDeliveryTour(d);
		
		GraphicalView graphicalView = window.getGraphicalView();
        TextualView textualView = window.getTextualView();
 	    
        graphicalView.updateGraphicalCityMap(controller);
        textualView.update(controller);
		
		window.setContinueCalculation();
		controller.setCurrentState(controller.continueComputationState);

	}

}
