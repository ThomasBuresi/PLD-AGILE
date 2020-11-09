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
	 * Method called by the controller after a click on the button "Load map".
	 * It parses an XML file chosen by the user and creates the CityMap class,
	 * which is then shown in the application
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void loadMap(Controller c,  Window w) {};
	
	/**
	 * Method called by the controller after a click on the button "Load requests".
	 * It parses an XML file chosen by the user and creates the RequestList class,
	 * which is then shown in the application on the map and in the Textual View.
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void loadRequestsFile(Controller c, Window w) {};
	
	/**
	 * Method called by the controller after a click on the button "-" on the map.
	 * It zooms out to the initial state of the map
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void zoomOut(Controller c, Window w) {};
	
	/**
	 * Method called by the controller if the user clicks on the map and releases the
	 * clicked button in another place ( like drawing a rectangle on the map ). This
	 * rectangle represents the zone to zoom in.
	 * 
	 * @param c controller
	 * @param w the window
	 * @param longMin
	 * @param longMax
	 * @param latMin
	 * @param latMax
	 * 
	 */
	public default void zoomIn(Controller c, Window w, float longMin, float longMax, float latMin, float latMax) {};
	
	/**
	 * Method called by the controller after a click on the button "Compute Delivery Tour".
	 * It creates the DeliveryTour class and calculates the best tour for the user, which is 
	 * then shown on the map and the list in the Textual View is updated with the order 
	 * of passage.  
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void computeDeliveryTour(Controller c, Window w) {};
	
	/**
	 * Method called by the controller after a click on the button "Continue Calculation".
	 * It creates a new DeliveryTour class with the <code>DijkstraGraph</code> and the 
	 * <code>tsp</code> already calculated previously and continues calculating the best 
	 * tour for the user, which is then shown on the map and the list in the Textual View
	 * is updated with the order of passage.  
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void continueCalculation(Controller c, Window w) {};
	
	/**
	 * Method called by the controller after a click on the button "Skip".
	 * It changes the State to DeliveryTourState directly without calculating 
	 * a second time.
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void skipContinueCalculation(Controller c, Window w) {};
	
	/**
	 * Method called by the controller after a click on the button "Add".
	 * It changes the State to AddRequestState where the user can add a new
	 * request to the existing tour.
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void changeToAddRequestMode(Controller c, Window w) {};
	
	/**
	 * TODO
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void addRequest(Controller c, Window w) {};
	
	/**
	 * TODO
	 */
	public default void reInitialiseSelection(Controller c, Window w) {};
	
	
	/**
	 * Method called by the controller after a click on the button "Export Tour File".
	 * Exports the delivery tour as a description of the steps of the tour
     * to take to a file.
	 * @param c controller
	 * @param w the window
	 */
	public default void exportTourFile(Controller c,Window w) {};
	
	/**
	 * TODO
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void leftClick(Controller c, Window w,int xCoord,int yCoord) {};


	/**
	 * Method to process the removal of the request from the tour and add it to the list of commands for the 
	 * undo/redo. 
	 * 
	 * @param c controller
	 * @param w the window
	 */
	public default void removeRequest(Controller c,Window w) {};
	
	/**
	 * Method called by the controller after a click on the button "Undo"
	 * @param l the current list of commands
	 */
	public default void undo(Controller c, Window w,ListOfCommands l){};
	
	/**
	 * Method called by the controller after a click on the button "Redo"
	 * @param l the current list of commands
	 */
	public default void redo(Controller c, Window w,ListOfCommands l){};
	
	
}