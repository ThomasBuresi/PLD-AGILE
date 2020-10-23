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
	
    /**
     * Default constructor
     */
    public ButtonListener(Controller controller, Window window, GraphicalView graphicalView) {
    	this.controller=controller;
    	this.window = window;
    	this.graphicalView = graphicalView;
    	fc = new JFileChooser();
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
               
               controller.loadRequestsFile(requests_file.getAbsolutePath());
               
               graphicalView.updateGraphicalCityMap(controller);
               //window.repaint();
               System.out.println("requests path was : " + requests_file.getAbsolutePath());
            		   
            } else {
                 System.out.println("L'ouverture est annulée\n");
            }
			break;
			//
		}
			
		
		
	}


}