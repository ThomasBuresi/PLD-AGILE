package controller;

import view.GraphicalView;
import view.TextualView;
import view.Window;

import java.util.HashMap;
import java.util.Map;

import model.Intersection;

public class AddRequestState implements State{
	
	@Override
	public void addRequest(Controller controller, Window w) {	
		
		GraphicalView graphicalView = w.getGraphicalView();

		if(graphicalView.getGraphicalCityMap().getGraphicalIntersection().getToBeAdded().size()==4) {
			
			ListOfCommands list =controller.getListOfCommands();
			list.add(new AddCommand(controller,controller.getDeliveryTour(),graphicalView.getGraphicalCityMap().getGraphicalIntersection().getToBeAdded()));
			controller.setListOfCommands(list);
			
			System.out.println(controller.getDeliveryTour().toString());
			System.out.println(controller.getRequestList().toString());
			
			
			
			graphicalView.getGraphicalCityMap().getGraphicalIntersection().reInitializedToBeAdded();
			graphicalView.updateSelection(false, null);
			
			w.getGraphicalView().updateGraphicalCityMap(controller);
			w.getTextualView().update(controller);
			
			w.setVisibleAddExport();
			controller.setCurrentState(controller.deliveryTourState);
		}else {
			System.out.println("wrong selection, missing points");
		}
		
		
		

	}
	
	@Override
	public void reInitialiseSelection(Controller c, Window w) {
		GraphicalView graphicalView = w.getGraphicalView();
		graphicalView.getGraphicalCityMap().getGraphicalIntersection().reInitializedToBeAdded();
		graphicalView.updateGraphicalCityMap(c);
	}
	
	@Override 
	public void leftClick(Controller c, Window w,int xCoord,int yCoord) {
		GraphicalView graphicalView = w.getGraphicalView();
		TextualView textualView = w.getTextualView();
		
		int panelHeight = graphicalView.getHeight();
		int panelWidth = graphicalView.getWidth();
		
		Intersection i  = graphicalView.getGraphicalCityMap().getGraphicalIntersection().getClickedIntersection(xCoord, yCoord, panelHeight, panelWidth);
		
		//System.out.println(i.toString());
		
		graphicalView.updateSelection(true, i);
		graphicalView.repaint();
		
		System.out.println(graphicalView.getGraphicalCityMap().getGraphicalIntersection().getToBeAdded().toString());
		
		
		
		
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
