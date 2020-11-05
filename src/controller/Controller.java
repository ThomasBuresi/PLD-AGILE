package controller;

import java.util.*;


import model.CityMap;
import model.DeliveryTour;
import model.RequestList;
import tsp.DijkstraGraph;
import tsp.TSP;
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
	private DijkstraGraph dijkstraGraph;
	private TSP tsp;
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
    	deliveryTour=null;
    	tsp=null;
    	dijkstraGraph=null;
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
    
    public void zoomIn(int pressedX, int pressedY, int relX, int relY) {
    	currentState.zoomIn(this, window, pressedX, pressedY, relX, relY);
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
    
    public void addRequest(int [] xCoord, int [] yCoord) { //AddCommand 
    	currentState.addRequest(this, window, xCoord, yCoord);
    }
    
    public void exportTour() {
    	currentState.exportTourFile(this, window);
    }
    
    public void leftClick (int xCoord,int yCoord) {
    	currentState.leftClick(this,window,xCoord,yCoord);
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

    /**
     * Getters and setters 
     * @return
     */
    
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
	
	public TSP getTsp() {
		return tsp;
	}
	
	public void setTsp(TSP tsp) {
		this.tsp=tsp;
	}
	
	public DijkstraGraph getDijkstraGraph () {
		return dijkstraGraph;
	}
    
	public void setDijkstraGraph (DijkstraGraph g) {
		this.dijkstraGraph=g;
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
	
}