package controller;


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
	protected final ContinueComputationState continueComputationState = new ContinueComputationState();
	protected final AddRequestState addRequestState = new AddRequestState();
	public final RemoveRequestState removeRequestState = new RemoveRequestState();
	
	
	
    /**
     * Default constructor of Controller that initializes the attributes
     */
    public Controller() {
    	currentState = initialState;
    	window = new Window(this);
    	listOfCommands= new ListOfCommands();
    	cityMap=null; 
    	requestList=null;
    	deliveryTour=null;
    }
    
    
    /**
	 * Change the current state of the controller
	 * @param state the new current state
	 * from PlaCo 
	 */
	public void setCurrentState(State state){
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
    	currentState.continueCalculation(this, window);
    }
    
    public void skipContinueCalculation() {
    	currentState.skipContinueCalculation(this,window);
    }
    
    public void changeToAddRequestMode() {
    	currentState.changeToAddRequestMode(this,window);
    }
    
    public void addRequest() { 
    	currentState.addRequest(this, window);
    }
    
    public void reInitialiseSelection() {
    	currentState.reInitialiseSelection(this,window);
    }
    
    public void exportTour() {
    	currentState.exportTourFile(this, window);
    }
    
    public void leftClick (int xCoord,int yCoord) { 
    	currentState.leftClick(this,window,xCoord,yCoord);
    }
    
    public void removeRequest() {
    	currentState.removeRequest(this,window);
    }
    
    /**
     * TODO Move the content of this method to the corresponding state : remove/add where needed 
     */
    
    public void cancel() {
    	window.getGraphicalView().getGraphicalCityMap().getGraphicalIntersection().reInitializedToBeAdded();
    	window.getGraphicalView().updateHighlight(-1);
    	window.getTextualView().highlightTable(-1);
    	window.setVisibleAddExport();
    	this.setCurrentState(deliveryTourState);
    }
    
    
    
    
    /**
     * 
     */
    public void undo() {
        currentState.undo(this,window,listOfCommands);
    }

    /**
     * 
     */
    public void redo() {
    	currentState.redo(this,window,listOfCommands);
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