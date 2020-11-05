package controller;

import view.GraphicalView;
import view.Window;

import java.util.HashMap;
import java.util.Map;

import model.Intersection;

public class AddRequestState implements State{
	
	@Override
	public void addRequest(Controller controller, Window window, int [] xCoord, int[] yCoord) {
		
		// add the four point in the good order to build the request
		// add the request to the delivery tour 
		// recompute just this special portion of the delivery tour 
		// display the tour updated 
		
		//not to forget
		ListOfCommands list =controller.getListOfCommands();
		list.add(new AddCommand());
		controller.setListOfCommands(list);// TODO 
		
//		HashMap<Long, Intersection> listIntersection;
//		GraphicalView graphicalView = window.getGraphicalView();
//		int panelHeight = graphicalView.getHeight();
//    	int panelWidth = graphicalView.getWidth();
//
//		listIntersection = controller.getCityMap().getListIntersection();
//		//Get coordinates of the zone shown on the map at the moment
//		float latMax = graphicalView.graphicalCityMap.graphicalSegment.getLatMaxMap();
//		float latMin = graphicalView.graphicalCityMap.graphicalSegment.getLatMinMap();
//		float longMin = graphicalView.graphicalCityMap.graphicalSegment.getLongMinMap();
//		float longMax = graphicalView.graphicalCityMap.graphicalSegment.getLongMaxMap();
//		
//		for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
//			int xInter = (int)Math.round((entry.getValue().getLongitude()-longMin)/(longMax-longMin)*panelWidth);
//			int yInter = panelHeight - (int)Math.round((entry.getValue().getLatitude()-latMin)/(latMax-latMin)*panelHeight);
//			if ((xCoord >= xInter - 3) && (xCoord<= xInter + 3) && (yCoord >= yInter - 3) && (yCoord<= yInter + 3)) {
//				System.out.println(entry.getValue());
//			}
//		}
//		
		
	}
}
