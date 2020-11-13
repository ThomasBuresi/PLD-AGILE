package controller;

import java.io.File;

import javax.swing.JFileChooser;

import model.RequestList;
import view.GraphicalView;
import view.TextualView;
import view.Window;

/**
 * State where the map is loaded and the requests can be loaded.
 * 
 * @author H4112
 *
 */
public class MapLoadedState extends InitialState{
	/**
	 * To open a dialog window to load the files 
	 */
	private JFileChooser fc;
	
	@Override
	public void loadRequestsFile(Controller controller, Window window) {
		fc = new JFileChooser();

		fc.setCurrentDirectory( new File ( System.getProperty("user.dir")));
    	
    	window.setLoadingRequests();
        window.repaint();
    	
		int val_ret_requests = fc.showOpenDialog(null);

        if (val_ret_requests == JFileChooser.APPROVE_OPTION) {
        	     	
        	File requests_file = fc.getSelectedFile();
        	RequestList requestList = new RequestList(requests_file.getAbsolutePath(), controller.getCityMap());
        	boolean res = requestList.fillRequests();
   
        	if(res) {
        		System.out.println("Requests loaded");
        		
        		controller.setRequestList(requestList);
        		controller.setDeliveryTour(null);
        		GraphicalView graphicalView = window.getGraphicalView();
        		TextualView textualView = window.getTextualView();
        		graphicalView.updateGraphicalCityMap(controller);
               	textualView.update(controller);
               	window.setVisibleCalculateButton();
               	window.addLegend();

               	controller.setCurrentState(controller.mapRequestsLoadedState);
        	}else {
        		System.err.println("Requests didn't correspond");
        		window.setErrorAtOpening();
        	}
        	
        } else {
        	System.out.println("The opening of the file was cancelled\n");
             
            if(controller.getCurrentState() instanceof DeliveryTourState) {
            	window.setVisibleAddExport();
	        } else if(controller.getCurrentState() instanceof ContinueComputationState) {
            	window.setContinueCalculation();
            
	        } else if(controller.getCurrentState() instanceof MapRequestsLoadedState) {
            	window.setVisibleCalculateButton();
            }
            else {
            	window.setVisibleRequestButton();
            } 
             
        }
	}

	@Override
	public void zoomOut(Controller controller, Window window) {
		GraphicalView graphicalView = window.getGraphicalView();
		if (graphicalView.graphicalCityMap.getGraphicalSegment() != null) {
			graphicalView.graphicalCityMap.getGraphicalSegment().resetCoord();
			if (graphicalView.graphicalCityMap.getGraphicalIntersection() != null) {
				graphicalView.graphicalCityMap.getGraphicalIntersection().resetCoord();
			}
		}
		window.repaint();
	}
	
	@Override
	public void zoomIn(Controller controller, Window window, float longMin, float longMax, float latMin, float latMax) {
		GraphicalView graphicalView = window.getGraphicalView();
		//Set the new coordinates for the segments of the map
		graphicalView.graphicalCityMap.getGraphicalSegment().setLatMaxMap(latMax);
		graphicalView.graphicalCityMap.getGraphicalSegment().setLatMinMap(latMin);
		graphicalView.graphicalCityMap.getGraphicalSegment().setLongMaxMap(longMax);
		graphicalView.graphicalCityMap.getGraphicalSegment().setLongMinMap(longMin);
		if (graphicalView.graphicalCityMap.getGraphicalIntersection() != null) {
			//Set the new coordinates for the points from the requests of the map
			graphicalView.graphicalCityMap.getGraphicalIntersection().setLatMaxMap(latMax);
			graphicalView.graphicalCityMap.getGraphicalIntersection().setLatMinMap(latMin);
			graphicalView.graphicalCityMap.getGraphicalIntersection().setLongMaxMap(longMax);
			graphicalView.graphicalCityMap.getGraphicalIntersection().setLongMinMap(longMin);
		}
		
		window.repaint();
	}

}
