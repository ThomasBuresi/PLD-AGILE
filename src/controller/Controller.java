package controller;

import java.util.*;


import model.CityMap;
import model.DeliveryTour;
import model.RequestList;
import view.Window;

/**
 * 
 */
public class Controller {

	/**
	 * CityMap that will be imported from a file path
	 */
	private CityMap cityMap;
	private RequestList requestList;
	private DeliveryTour deliveryTour;
	private Window window;
	private ListOfCommands listOfCommands;
	private State currentState; 
	
	// Instances associated with each possible state of the controller
	protected final InitialState initialState = new InitialState();
	protected final MapLoadedState mapLoadedState = new MapLoadedState();
	protected final MapRequestsLoadedState mapRequestsLoadedState = new MapRequestsLoadedState();
	protected final DeliveryTourState deliveryTourState = new DeliveryTourState();
	protected final RequestHighlightedState requestHighlighted = new RequestHighlightedState();
	protected final ContinueComputationState continueComputationState = new ContinueComputationState();
	protected final AddRequestState addRequestState = new AddRequestState();
	protected final RemoveRequestState removeRequestState = new RemoveRequestState();
	
	
	
    /**
     * Default constructor
     */
    public Controller() {
    	currentState = initialState;
    	window = new Window(this);
    	cityMap=null; 
    	requestList=null;
    }
    
    
    /**
	 * Change the current state of the controller
	 * @param state the new current state
	 * from PlaCo 
	 */
	protected void setCurrentState(State state){
		currentState = state;
	}


    /**
	 * Method called by window after a click on the button "Load Map"
	 */
    public void loadMapFile() {
   		currentState.loadMap(this, window);
    	
    }
    
    public void loadRequestsFile() {
    	currentState.loadRequestsFile(this, window);
    }
    
    public void zoomOut() {
		currentState.zoomOut(this, window);		
	}
    
    public void computeDeliveryTour() {
		currentState.computeDeliveryTour(this, window);
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

    
    
    public CityMap getCityMap() {
    	return cityMap;
    }
    public void setCityMap(CityMap m) {
    	cityMap=m;
    }
    
    public RequestList getRequestList() {
    	return requestList;
    }
    
    public void setRequestList(RequestList requestList) {
		this.requestList = requestList;
	}
    
    public DeliveryTour getDeliveryTour() {
		return deliveryTour;
	}

	public void setDeliveryTour(DeliveryTour d) {
		this.deliveryTour = d;
	}
    
}