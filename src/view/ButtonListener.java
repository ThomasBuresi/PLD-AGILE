package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;

import controller.Controller;
import model.CityMap;

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
                   //window.repaint();
                   //System.out.println("requests path was : " + requests_file.getAbsolutePath());
               }else {
            	   window.setErrorAtOpening();
               }
  	   
            } else {
                 System.out.println("L'ouverture est annulée\n");
            }
			break;
		case"Calculate Delivery Tour" : 
			
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