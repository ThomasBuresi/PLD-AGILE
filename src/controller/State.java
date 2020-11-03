package controller;

import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;

import model.CityMap;
import view.GraphicalView;
import view.TextualView;
import view.Window;

/**
 * 
 */
public interface State {

	
	/**
	 * Method called by the controller after a click on the button "Load map"
	 * @param c controller
	 * @param w the window
	 */
	public default void loadMap(Controller c,  Window w) {};
	
	public default void loadRequestsFile(Controller c, Window w) {};
	
	public default void zoomOut(Controller c, Window w) {};
	
	public default void computeDeliveryTour(Controller c, Window w) {};


}