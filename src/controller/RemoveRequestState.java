package controller;

import model.Request;
import model.RequestList;
import view.GraphicalView;
import view.TextualView;
import view.Window;

public class RemoveRequestState implements State {

	/*
	 * //not to forget
		ListOfCommands list =controller.getListOfCommands();
		list.add(new RemoveCommand());
		controller.setListOfCommands(list);// TODO 
	 */
	
	/*
	 * 
	 * left click 
	 * 
	 * compare if its the same request clicked or not 
	 * if so return to delivery tour state (views + state to update)
	 * else if its on another request highlight it and save it somewhere
	 * else do nothing 
	 * 
	 * 
	 * case the button was clicked : new method removeRequest
	 * perform the removal
	 * add the remove command to the list of commands to prepare the possible undo/redo 
	 * update the computation of the tour 
	 * update the views (graph+textual)
	 * update state of the controller 
	 * 
	 */
	
	@Override 
	public void leftClick(Controller c, Window w,int xCoord,int yCoord) {
		GraphicalView graphicalView = w.getGraphicalView();
		TextualView textualView = w.getTextualView();
		
		int panelHeight = graphicalView.getHeight();
		int panelWidth = graphicalView.getWidth();
		
		int id = graphicalView.getGraphicalCityMap().getGraphicalIntersection().getClickedRequestId(xCoord, yCoord, panelHeight, panelWidth);
		
		System.out.println(id);
		
		graphicalView.updateHighlight(id);
		textualView.highlightTable(id);
		
		
		
	}
	
	@Override 
	public void removeRequest(Controller c,Window w) {
//		perform the removal
//		add the remove command to the list of commands to prepare the possible undo/redo 
//		update the computation of the tour 
//		update the views (graph+textual)
//		update state of the controller 
		
		int idRequestToRemove = c.getWindow().getTextualView().getISelectedRequest();
		
		Request r = c.getRequestList().getListRequests().get(idRequestToRemove);
		
		
		
		if(r!=null) {
			ListOfCommands list = c.getListOfCommands();
			list.add(new RemoveCommand(c, c.getDeliveryTour(),r));
			
			c.setListOfCommands(list);
			
			w.getGraphicalView().setId(-1);
			
			w.getGraphicalView().updateGraphicalCityMap(c);
			w.getTextualView().update(c);
			
			w.setVisibleAddExport();
			c.setCurrentState(c.deliveryTourState);
			
		}else {
			System.out.println("error on remove the request");
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
