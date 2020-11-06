package controller;

import view.GraphicalView;
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
		
		int panelHeight = graphicalView.getHeight();
		int panelWidth = graphicalView.getWidth();
		
		int id = graphicalView.getGraphicalCityMap().getGraphicalIntersection().getClickedRequestId(xCoord, yCoord, panelHeight, panelWidth);
		
		System.out.println(id);
		
		graphicalView.updateHighlight(id);
		
		
		
	}
	
	
}
