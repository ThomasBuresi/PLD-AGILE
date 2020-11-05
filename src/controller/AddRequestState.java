package controller;

import view.GraphicalView;
import view.Window;

import java.util.HashMap;
import java.util.Map;

import model.Intersection;

public class AddRequestState implements State{
	
	@Override
	public void addRequest(Controller controller, Window window, int xCoord, int yCoord) {
		HashMap<Long, Intersection> listIntersection;
		GraphicalView graphicalView = window.getGraphicalView();
		int panelHeight = graphicalView.getHeight();
    	int panelWidth = graphicalView.getWidth();

		listIntersection = controller.getCityMap().getListIntersection();
		//Get coordinates of the zone shown on the map at the moment
		float latMax = graphicalView.graphicalCityMap.getGraphicalSegment().getLatMaxMap();
		float latMin = graphicalView.graphicalCityMap.getGraphicalSegment().getLatMinMap();
		float longMin = graphicalView.graphicalCityMap.getGraphicalSegment().getLongMinMap();
		float longMax = graphicalView.graphicalCityMap.getGraphicalSegment().getLongMaxMap();
		
		for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
			int xInter = (int)Math.round((entry.getValue().getLongitude()-longMin)/(longMax-longMin)*panelWidth);
			int yInter = panelHeight - (int)Math.round((entry.getValue().getLatitude()-latMin)/(latMax-latMin)*panelHeight);
			if ((xCoord >= xInter - 3) && (xCoord<= xInter + 3) && (yCoord >= yInter - 3) && (yCoord<= yInter + 3)) {
				System.out.println(entry.getValue());
			}
		}
		
		
	}
}
