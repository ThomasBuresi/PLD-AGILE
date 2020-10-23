package controller;

import java.util.*;

import model.CityMap;
import model.RequestList;

/**
 * 
 */
public class Controller {

	/**
	 * CityMap that will be imported from a file path
	 */
	private CityMap cityMap;
	private RequestList requestList;
	
	
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
    	cityMap.fillMap();
    	System.out.println("map loaded");
    	
    }
    
    public void loadRequestsFile(String absolute_path) {
    	requestList = new RequestList(absolute_path, cityMap);
    	requestList.fillRequests();
    	System.out.println("requests loaded");
    }
    
    public CityMap getCityMap() {
    	return cityMap;
    }
    
    public RequestList getRequestList() {
    	return requestList;
    }
    
}