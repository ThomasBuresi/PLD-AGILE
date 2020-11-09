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
			
		}else {
			System.out.println("wrong selection, missing points");
		}
		
		
		//graphicalView.updateGraphicalCityMap(controller);
		
		
		//not to forget
		ListOfCommands list =controller.getListOfCommands();
		list.add(new AddCommand());
		controller.setListOfCommands(list);// TODO 
		
		//getclicked intersections
		//update the view after each click ? 
		
		
		//reset everythhing
		graphicalView.getGraphicalCityMap().getGraphicalIntersection().reInitializedToBeAdded();
		graphicalView.updateSelection(false, null);
		

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
		
		System.out.println(i.toString());
		
		graphicalView.updateSelection(true, i);
		graphicalView.repaint();
		graphicalView.repaint();
		
		System.out.println(graphicalView.getGraphicalCityMap().getGraphicalIntersection().getToBeAdded().toString());
		
		//System.out.println(id);
		
		//graphicalView.updateHighlight(id);
		//textualView.highlightTable(id);
		
		
		
	}
	
	
}
