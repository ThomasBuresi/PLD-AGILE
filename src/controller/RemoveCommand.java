package controller;

import java.util.*;

import model.DeliveryTour;
import model.Intersection;

/**
 * 
 */
public class RemoveCommand implements Command {

	/**
	 * Delivery tour where we remove a request.
	 */
	private DeliveryTour deliveryTour;
	
	/**
	 * Controller to save the modifications onto the tour
	 */
	private Controller controller;
	
	/**
	 * Pickup intersection of the request we want to remove. 
	 */
	private Intersection pickup;
	
	/**
	 * Delivery intersection of the request we want to remove. 
	 */
	private Intersection delivery;
	
    /**
     * Default constructor
     */
    public RemoveCommand(Controller controller,DeliveryTour tour,Intersection pickup,Intersection delivery) { //A request ? id ?  
    	this.controller=controller;
    	this.pickup=pickup;
    	this.delivery=delivery;
    	deliveryTour=tour;
    }

    /**
     * @return
     */
    public void doCommand() {
        
    	deliveryTour.removeStep(pickup);
    	deliveryTour.removeStep(delivery);
    	deliveryTour.fillDeliveryTour(1000);
    	controller.setDeliveryTour(deliveryTour);
    	
    	// TODO remember where it was remove > modif return of method removeStep 
    	// that way the redo will be more direct 
    	
        //return null;
    }

    /**
     * @return
     */
    public void undoCommand() {
        // TODO readd the request 
        //return null;
    }

}