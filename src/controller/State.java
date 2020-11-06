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
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void loadMap(Controller c,  Window w) {};
	
	public default void loadRequestsFile(Controller c, Window w) {};
	
	public default void zoomOut(Controller c, Window w) {};
	
	public default void zoomIn(Controller c, Window w, int pressedX, int pressedY, int relX, int relY) {};
	
	public default void computeDeliveryTour(Controller c, Window w) {};
	
	public default void continueCalculation(Controller c, Window w) {};
	
	public default void skipContinueCalculation(Controller c, Window w) {};
	
	public default void changeToAddRequestMode(Controller c, Window w) {};
	
	public default void addRequest(Controller c, Window w, int [] xCoord, int[] yCoord) {};
	
	public default void exportTourFile(Controller c,Window w) {};
	
	public default void leftClick(Controller c, Window w,int xCoord,int yCoord) {};


}