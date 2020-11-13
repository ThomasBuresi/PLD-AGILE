package controller;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import view.GraphicalView;
import view.TextualView;
import view.Window;

/**
 * State where the delivery tour is displayed. 
 * 
 * @author H4112
 *
 */
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

		fc.setCurrentDirectory( new File ( System.getProperty("user.dir")));
    	
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
				
				w.setVisibleRemove();
				
				c.setCurrentState(c.removeRequestState);
			}
			
		} 
		
		
	}
	
	
	@Override
	public void undo(Controller c, Window w,ListOfCommands listOfCdes){
		listOfCdes.undo();
		GraphicalView graphicalView = w.getGraphicalView();
		TextualView textualView = w.getTextualView();
		
		graphicalView.updateGraphicalCityMap(c);
		textualView.update(c);
		
		
	}

	@Override
	public void redo(Controller c, Window w,ListOfCommands listOfCdes){
		listOfCdes.redo();
		GraphicalView graphicalView = w.getGraphicalView();
		TextualView textualView = w.getTextualView();
		
		graphicalView.updateGraphicalCityMap(c);
		textualView.update(c);
		
		
	}
}
