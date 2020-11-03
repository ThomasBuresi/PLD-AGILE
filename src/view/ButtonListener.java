package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;

import com.sun.tools.javac.util.Pair;

import controller.Controller;
import model.CityMap;
import model.DeliveryTour;
import model.Intersection;
import model.RequestList;
import model.Segment;
import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP1;

/**
 * 
 */
public class ButtonListener implements ActionListener {
	/**
	 * To open a dialog window to load the files 
	 */
	private JFileChooser fc;
	
	/**
	 * Controller to perform the actions that require some processing 
	 */
	private Controller controller; 
	
	/**
	 * Repaint
	 */
	private Window window;
	
	private GraphicalView graphicalView;
	
	private TextualView textualView;
	
    /**
     * Default constructor
     */
    public ButtonListener(Controller controller, Window window, GraphicalView graphicalView, TextualView textualView) {
    	this.controller=controller;
    	this.window = window;
    	this.graphicalView = graphicalView;
    	this.textualView = textualView;
    	fc = new JFileChooser();
    	fc.setCurrentDirectory( new File ( System.getProperty("user.dir") + 
    			System.getProperty("file.separator")+ "src" + 
    			System.getProperty("file.separator")+ "resources"));
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Load Map":
			
			int val_ret = fc.showOpenDialog(null);

            if (val_ret == JFileChooser.APPROVE_OPTION) {
               File file = fc.getSelectedFile();
               
               //to print the absolute path of the file
               //System.out.println("Chemin absolu : "+file.getAbsolutePath()+"\n");
               
               controller.loadMapFile(file.getAbsolutePath());
               
               graphicalView.updateGraphicalCityMap(controller);
               //window.repaint();
               window.setVisibleRequestButton();
               window.removeLegend();
               textualView.update(controller);
            		   
            } else {
                 System.out.println("L'ouverture est annulée\n");
            }
			break;
		case "Load Requests":
			
			int val_ret_requests = fc.showOpenDialog(null);

            if (val_ret_requests == JFileChooser.APPROVE_OPTION) {
               File requests_file = fc.getSelectedFile();
               
               boolean res = controller.loadRequestsFile(requests_file.getAbsolutePath());
               
               if(res) {
            	   graphicalView.updateGraphicalCityMap(controller);
                   textualView.update(controller);
                   window.setVisibleCalculateButton();
                   window.addLegend();
                   //window.repaint();
                   //System.out.println("requests path was : " + requests_file.getAbsolutePath());
               }else {
            	   window.setErrorAtOpening();
               }
  	   
            } else {
                 System.out.println("L'ouverture est annulée\n");
            }
			break;
			
		case "-" :
			if (graphicalView.graphicalCityMap.graphicalSegment != null) {
				
				graphicalView.graphicalCityMap.graphicalSegment.resetCoord();
				if (graphicalView.graphicalCityMap.graphicalIntersection != null) {
					graphicalView.graphicalCityMap.graphicalIntersection.resetCoord();
				}
			}
			window.repaint();

			
			break;
		case"Calculate Delivery Tour" : 
			CityMap map = controller.getCityMap();
			RequestList reqlist = controller.getRequestList();
			DijkstraGraph g = new DijkstraGraph(map, reqlist);
			  
		  	for(int j = 0; j < 1+2*reqlist.getListRequests().size(); j++) {
			  for(int k = 0; k < 1+2*reqlist.getListRequests().size(); k++) {
				  System.out.print(g.getCost(j, k) + " ");
			  }
			  System.out.println();
		  	}
			TSP tsp = new TSP1();
			tsp.searchSolution(20000, g);
			System.out.println("Solution TSP de cout : " + tsp.getSolutionCost());
			for(int m = 0; m < 1+2*reqlist.getListRequests().size(); m++) {
				System.out.print(" " + tsp.getSolution(m));
			}
			System.out.println(" 0");
			DeliveryTour d = new DeliveryTour();
			d.addDeparture(reqlist.getDeparture());
			// on commence à un car on a déjà traité le cas du départ
			for(int l = 1; l < 1+2*reqlist.getListRequests().size(); l++) {
			//ajouter au delivery tour l'intersection qui correspond au numero de la requête ->
				int currentsolution=tsp.getSolution(l);
				if (currentsolution%2!=0) {
					d.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2).getDeliveryAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
				}
				else {
					
					d.addStep(reqlist.getListRequests().get(tsp.getSolution(l)/2 -1).getPickupAddress(), g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l-1)]); // inverser l'ordre??
				}
			}
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
			
			//controller computation
			
			//case it ends 
			
			//display it on the map 
			//order the request list 
			//setVisibleAddExport in Window
			//window.setVisibleAddExport();
			
			
			//case computation is too long 
			
			//remember that we can continue the computation only one time
			//setContinueVisible
			window.setContinueCalculation();
			
			break;
			
		case "Continue calculation (20sec more)":
			//controller computation
			
			//it ends 
			//setVisibleAddExport in Window
			window.setVisibleAddExport();
			//it doesn't end
			//set error mode ??  
			
			break;
		
		case "Add" :
			break;
		case "Remove" :
			break;
		case "Export Tour File" :
			
			//controller and delivery tour object 
			
			// no change in tg=he display ? or popup the export is complete ? 
			
			break;
		case "Undo" :
			break;
		case "Redo" :
			break;
			
		}
			
		
		
	}


}