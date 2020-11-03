package controller;

import java.io.File;

import javax.swing.JFileChooser;

import model.CityMap;
import model.RequestList;
import view.GraphicalView;
import view.TextualView;
import view.Window;

public class MapLoadedState implements State{
	/**
	 * To open a dialog window to load the files 
	 */
	private JFileChooser fc;
	
	@Override
	public void loadRequestsFile(Controller controller, Window window) {
		fc = new JFileChooser();
    	fc.setCurrentDirectory( new File ( System.getProperty("user.dir") + 
    			System.getProperty("file.separator")+ "src" + 
    			System.getProperty("file.separator")+ "resources"));
    	
		int val_ret_requests = fc.showOpenDialog(null);

        if (val_ret_requests == JFileChooser.APPROVE_OPTION) {
           File requests_file = fc.getSelectedFile();
           
           RequestList requestList = new RequestList(requests_file.getAbsolutePath(), controller.getCityMap());
           boolean res = requestList.fillRequests();
           
           if(res) {
        	   System.out.println("requests loaded");
        	   
        	   controller.setRequestList(requestList);
        	   GraphicalView graphicalView = window.getGraphicalView();
               TextualView textualView = window.getTextualView();
        	   graphicalView.updateGraphicalCityMap(controller);
               textualView.update(controller);
               window.setVisibleCalculateButton();
               window.addLegend();

               controller.setCurrentState(controller.mapRequestsLoadedState);
           }else {
        	   System.out.println("requests didn't correspond");
        	   window.setErrorAtOpening();
           }
	   
        } else {
             System.out.println("L'ouverture est annul�e\n");
        }
		
		
	}
	
	@Override
	public void loadMap(Controller c,  Window w) {
		fc = new JFileChooser();
    	fc.setCurrentDirectory( new File ( System.getProperty("user.dir") + 
    			System.getProperty("file.separator")+ "src" + 
    			System.getProperty("file.separator")+ "resources"));
		
		int val_ret = fc.showOpenDialog(null);

        if (val_ret == JFileChooser.APPROVE_OPTION) {
           File file = fc.getSelectedFile();
           
           CityMap cityMap = new CityMap(file.getAbsolutePath());
       	   cityMap.fillMap();
       	   
       	   c.setCityMap(cityMap);
       	   System.out.println("map loaded");
           
           GraphicalView graphicalView = w.getGraphicalView();
           TextualView textualView = w.getTextualView();
           
           graphicalView.updateGraphicalCityMap(c);
           //window.repaint();
           w.setVisibleRequestButton();
           w.removeLegend();
           textualView.update(c);
           
           c.setCurrentState(c.mapLoadedState);
        		   
        } else {
             System.out.println("L'ouverture est annul�e\n");
        }
		
	}
	
	@Override
	public void zoomOut(Controller controller, Window window) {
		GraphicalView graphicalView = window.getGraphicalView();
		if (graphicalView.graphicalCityMap.graphicalSegment != null) {
			graphicalView.graphicalCityMap.graphicalSegment.resetCoord();
			if (graphicalView.graphicalCityMap.graphicalIntersection != null) {
				graphicalView.graphicalCityMap.graphicalIntersection.resetCoord();
			}
		}
		window.repaint();
	}

}
