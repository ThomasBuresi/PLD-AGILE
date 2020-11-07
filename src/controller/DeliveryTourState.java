package controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import com.sun.tools.javac.util.Pair;

import model.CityMap;
import model.DeliveryTour;
import model.Intersection;
import model.Request;
import model.RequestList;
import model.Segment;
import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP1;
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
		//w.setPopUpExport()
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
		
		int panelHeight = graphicalView.getHeight();
		int panelWidth = graphicalView.getWidth();
		
		int id = graphicalView.getGraphicalCityMap().getGraphicalIntersection().getClickedRequestId(xCoord, yCoord, panelHeight, panelWidth);
		
//		//Get coordinates of the zone shown on the map at the moment
//		float latMax = graphicalView.graphicalCityMap.getGraphicalSegment().getLatMaxMap();
//		float latMin = graphicalView.graphicalCityMap.getGraphicalSegment().getLatMinMap();
//		float longMin = graphicalView.graphicalCityMap.getGraphicalSegment().getLongMinMap();
//		float longMax = graphicalView.graphicalCityMap.getGraphicalSegment().getLongMaxMap();
//		
//		int id = -1;
//		
//		for (Request r : list) {
//			int xInterP = (int)Math.round((r.getPickupAddress().getLongitude()-longMin)/(longMax-longMin)*panelWidth);
//			int yInterP = panelHeight - (int)Math.round((r.getPickupAddress().getLatitude()-latMin)/(latMax-latMin)*panelHeight);
//			if ((xCoord >= xInterP - 3) && (xCoord<= xInterP + 3) && (yCoord >= yInterP - 3) && (yCoord<= yInterP + 3)) {
//				id=r.getId();
//			}
//			int xInterD = (int)Math.round((r.getDeliveryAddress().getLongitude()-longMin)/(longMax-longMin)*panelWidth);
//			int yInterD = panelHeight - (int)Math.round((r.getDeliveryAddress().getLatitude()-latMin)/(latMax-latMin)*panelHeight);
//			if ((xCoord >= xInterD - 3) && (xCoord<= xInterD + 3) && (yCoord >= yInterD - 3) && (yCoord<= yInterD + 3)) {
//				id=r.getId();
//			}
//		}
		
		System.out.println(id);
		
		graphicalView.updateHighlight(id);
		
		
		if(id!=-1) {
			//if on a request : 
			
			w.setVisibleRemove();
			
			//update the table to highlight the corresponding request
			// highlight map
			//graphicalView.getGraphicalCityMap().getGraphicalIntersection().drawHighlight(g, height, width, id);
			
			
			c.setCurrentState(c.removeRequestState);
		} 
		
		//TODO save id 
		
		//or which request is in the state clicked 
		
		//otherwise don't do anything
		
	}
	/*
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
        	   controller.setDeliveryTour(null);
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
       	   c.setRequestList(null);
       	   c.setDeliveryTour(null);
       	   System.out.println("map loaded");
           
           GraphicalView graphicalView = w.getGraphicalView();
           TextualView textualView = w.getTextualView();
           
           graphicalView.updateGraphicalCityMap(c);
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
	}*/
	
	
	
	
	
	
}
