package controller;

import java.util.*;

import model.CityMap;

/**
 * 
 */
public class Controller {

	/**
	 * CityMap that will be imported from a file path
	 */
	private CityMap cityMap;
	
	
    /**
     * Default constructor
     */
    public Controller() {
    }



    /**
     * @param CityMap 
     * @param int
     */
    public void Controller(CityMap cityMap ) {
        // TODO implement here
    }

    /**
     * @return
     */
    public void undo() {
        // TODO implement here
        
    }

    /**
     * @return
     */
    public void redo() {
        // TODO implement here
        
    }

    public void loadFile(String absolute_path) {
    	cityMap = new CityMap(absolute_path);
    	System.out.println("map loaded");
    }
    
    public CityMap getCityMap() {
    	return cityMap;
    }
    
}