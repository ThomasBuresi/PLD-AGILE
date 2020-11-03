package controller;

import java.util.*;


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
	public default void loadMap(Controller c, Window w){};


}