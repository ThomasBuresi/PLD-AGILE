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
    	listOfCommands= new ListOfCommands();
    	cityMap=null; 
    	requestList=null;
    	deliveryTour=null;
//    	tsp=null;
//    	dijkstraGraph=null;
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
    
    public void zoomIn(int pressedX, int pressedY, float longMin, float longMax, float latMin, float latMax) {
    	currentState.zoomIn(this, window, longMin, longMax, latMin, latMax);
    }
    
    public void computeDeliveryTour() {
		currentState.computeDeliveryTour(this, window);
	}
    
    public void continueCalculation() {
    	//TODO
    	currentState.continueCalculation(this, window);
    }
    
    public void skipContinueCalculation() {
    	currentState.skipContinueCalculation(this,window);
    }
    
    public void changeToAddRequestMode() {
    	currentState.changeToAddRequestMode(this,window);
    }
    
    public void addRequest(int [] xCoord, int [] yCoord) { //AddCommand , pass 4 Intersections as parameter
    	currentState.addRequest(this, window, xCoord, yCoord);
    }
    
    public void exportTour() {
    	currentState.exportTourFile(this, window);
    }
    
    public void leftClick (int xCoord,int yCoord) { //pass the id as parameter
    	currentState.leftClick(this,window,xCoord,yCoord);
    }
    
    public void removeRequest() {
    	currentState.removeRequest(this,window);//RemoveCommand
    }
    
    public void cancelRemove() {
    	window.getGraphicalView().updateHighlight(-1);
    	window.getTextualView().highlightTable(-1);
    	window.setVisibleAddExport();
    	this.setCurrentState(deliveryTourState);
    }
    
    
    
    
    /**
     * @return
     */
    public void undo() {
        currentState.undo(listOfCommands);
        
    }

    /**
     * @return
     */
    public void redo() {
    	currentState.redo(listOfCommands);
        
    }

    // Getters and setters 
    
    
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
	
	
	public State getCurrentState() {
		return currentState;
	}
	
	public ListOfCommands getListOfCommands() {
		return listOfCommands;
	}
	
	public void setListOfCommands(ListOfCommands list) {
		listOfCommands=list;
	}
	
	public Window getWindow() {
		return window;
	}
	
}