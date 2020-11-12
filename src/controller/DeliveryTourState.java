package controller;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import view.GraphicalView;
import view.TextualView;
import view.Window;

public class DeliveryTourState extends MapLoadedState {
	
	/**
	 * To open a dialog window to load the files 
	 */
	private JFileChooser fc;
	
	
	@Override
	public void changeToAddRequestMode(Controller c, Window w) {
		w.setVisibleAddMode();
		c.setCurrentState(c.addRequestState);
	}
	
	@Override 
	public void exportTourFile(Controller c, Window w) {
		fc = new JFileChooser();
    	fc.setCurrentDirectory( new File ( System.getProperty("user.dir") + 
    			System.getProperty("file.separator")+ "src" + 
    			System.getProperty("file.separator")+ "resources"));
    	
		int val_ret_requests = fc.showOpenDialog(null);

        if (val_ret_requests == JFileChooser.APPROVE_OPTION) {
           String path = fc.getSelectedFile().getAbsolutePath();
           c.getDeliveryTour().writeDeliveryTourToFile(path);
        }
		
		
		//pop up to confirm the export 
		w.setPopUpExport();
	}
	
	
	@Override 
	public void leftClick(Controller c, Window w,int xCoord,int yCoord) {
		//mettre en mode remove et highlight dans la request list la requête à supprimer la 
		//plus proche 
		
		//case the textual view is clicked 
		//update style points sur la carte 
		
		
		// how to check that the point is in the map ? or is on a request ? 
		
		//List<Request> list = c.getRequestList().getListRequests();
		GraphicalView graphicalView = w.getGraphicalView();
		TextualView textualView = w.getTextualView();
		
		int panelHeight = graphicalView.getHeight();
		int panelWidth = graphicalView.getWidth();
		
		List<Integer> idList = graphicalView.getGraphicalCityMap().getGraphicalIntersection().getClickedRequestId(xCoord, yCoord, panelHeight, panelWidth);
		
		
		if(idList.size()!=0) {
			
			if(idList.size()>1) {
				
				w.setVisiblePopUpMultipleRequests();
				
			}else{
				int id = idList.get(0);
				
				System.out.println(id);
				graphicalView.updateHighlight(id);
				textualView.highlightTable(id);
				
				//if on a request : 
				
				w.setVisibleRemove();
				
				//update the table to highlight the corresponding request
				// highlight map
				//graphicalView.getGraphicalCityMap().getGraphicalIntersection().drawHighlight(g, height, width, id);
				
				
				c.setCurrentState(c.removeRequestState);
			}
			
		} 
		
		//TODO save id 
		
		//or which request is in the state clicked 
		
		//otherwise don't do anything
		
	}
	
	
	@Override
	public void undo(Controller c, Window w,ListOfCommands listOfCdes){
		listOfCdes.undo();
		GraphicalView graphicalView = w.getGraphicalView();
		TextualView textualView = w.getTextualView();
		
		graphicalView.updateGraphicalCityMap(c);
		textualView.update(c);
		//update views TODO
		
	}

	@Override
	public void redo(Controller c, Window w,ListOfCommands listOfCdes){
		listOfCdes.redo();
		GraphicalView graphicalView = w.getGraphicalView();
		TextualView textualView = w.getTextualView();
		
		graphicalView.updateGraphicalCityMap(c);
		textualView.update(c);
		//update views TODO
		
	}
}
