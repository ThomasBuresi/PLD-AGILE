package controller;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import com.sun.tools.javac.util.Pair;

import model.CityMap;
import model.DeliveryTour;
import model.Intersection;
import model.RequestList;
import model.Segment;
import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP1;
import view.GraphicalView;
import view.TextualView;
import view.Window;

public class MapRequestsLoadedState implements State{
	
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
	}
	
	@Override
	public void computeDeliveryTour(Controller controller, Window window) {
		DeliveryTour d = new DeliveryTour(controller);
		d.fillDeliveryTour(5000);
		controller.setDeliveryTour(d);
		
		for (Pair<Intersection, List<Segment>> pair: d.getTour()) {
			//Intersection i = pair.getFirst();
			//System.out.println("hello");
			System.out.println(pair.fst.toString());
			List<Segment> seg = pair.snd;
			if (seg != null) {
				for (Segment s : seg) {
					System.out.println(s.toString());
				}
			}
		}
		// print the delivery tour 
		
		GraphicalView graphicalView = window.getGraphicalView();
        TextualView textualView = window.getTextualView();
 	    
        graphicalView.updateGraphicalCityMap(controller);
        textualView.update(controller);
		
		window.setContinueCalculation();
		controller.setCurrentState(controller.continueComputationState);
		//controller computation
		
		//case it ends 
		
		//display it on the map 
		//order the request list 
		//setVisibleAddExport in Window
		//window.setVisibleAddExport();
		
		
		//case computation is too long 
		
		//remember that we can continue the computation only one time
		//setContinueVisible
		
		/*CityMap map = controller.getCityMap();
		RequestList reqlist = controller.getRequestList();
		DijkstraGraph g = new DijkstraGraph(map, reqlist);
		  
	  	for(int j = 0; j < 1+2*reqlist.getListRequests().size(); j++) {
		  for(int k = 0; k < 1+2*reqlist.getListRequests().size(); k++) {
			  System.out.print(g.getCost(j, k) + " ");
		  }
		  System.out.println();
	  	}
		TSP tsp = new TSP1();
		//20000 for the 20sec but with our data set we test with shorter time limits
		tsp.searchSolution(5000, g);
		System.out.println("Solution TSP de cout : " + tsp.getSolutionCost());
		for(int m = 0; m < 1+2*reqlist.getListRequests().size(); m++) {
			System.out.print(" " + tsp.getSolution(m));
		}
		
		//add the result of tsp to the controller in case we continue the computation
		// and also thhe corresponding graph 
		controller.setDijkstraGraph(g);
		controller.setTsp(tsp);
		
		System.out.println(" 0");
		DeliveryTour d = new DeliveryTour();
		d.addDeparture(reqlist.getDeparture());
		// on commence � un car on a d�j� trait� le cas du d�part
		for(int l = 1; l < 1+2*reqlist.getListRequests().size(); l++) {
		//ajouter au delivery tour l'intersection qui correspond au numero de la requ�te ->
			int currentsolution=tsp.getSolution(l);
			if (currentsolution%2!=0) {
				d.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2).getDeliveryAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
			}
			else {
				
				d.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 -1).getPickupAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
			}
			
		}
		//retour au point de d�part :
		d.addStep(reqlist.getDeparture(), g.getSegmentPaths()[tsp.getSolution(2*reqlist.getListRequests().size())][tsp.getSolution(0)]); // inverser l'ordre??
		
		controller.setDeliveryTour(d);
		
		for (Pair<Intersection, List<Segment>> pair: d.getTour()) {
			//Intersection i = pair.getFirst();
			//System.out.println("hello");
			System.out.println(pair.fst.toString());
			List<Segment> seg = pair.snd;
			if (seg != null) {
				for (Segment s : seg) {
					System.out.println(s.toString());
				}
			}
		}
		// print the delivery tour 
		
		GraphicalView graphicalView = window.getGraphicalView();
        TextualView textualView = window.getTextualView();
 	    
        graphicalView.updateGraphicalCityMap(controller);
        textualView.update(controller);
		
		window.setContinueCalculation();
		controller.setCurrentState(controller.continueComputationState);*/
	}

}
