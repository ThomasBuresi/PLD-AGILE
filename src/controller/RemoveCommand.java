package controller;

import java.util.*;

import model.DeliveryTour;
import model.Intersection;
import model.Request;

/**
 * 
 */
public class RemoveCommand implements Command {

	/**
	 * Delivery tour where we remove a request.
	 */
	private DeliveryTour deliveryTour;
	
	/**
	 * Request that we remove 
	 */
	private Request r;
	
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
	 * Pickup intersection of the request we want to remove. 
	 */
	private Intersection beforePickup;
	
	/**
	 * Delivery intersection of the request we want to remove. 
	 */
	private Intersection beforeDelivery;
	
    /**
     * Default constructor
     */
    public RemoveCommand(Controller controller,DeliveryTour tour,Request r) { //A request ? id ?  
    	this.r=r;
    	this.controller=controller;
    	this.pickup=r.getPickupAddress();
    	this.delivery=r.getDeliveryAddress();
    	deliveryTour=tour;
    }

    /**
     * @return
     */
    public void doCommand() {
        
    	beforePickup=deliveryTour.removeStep(pickup);
    	beforeDelivery=deliveryTour.removeStep(delivery);
    	
    	controller.getRequestList().getListRequests().remove(r.getId());
    	//remember return intersection for the add
    	
    	//Delivery tour path updated directly in method remove or add 
    	controller.setDeliveryTour(deliveryTour);
    	
    	//controller.getWindow().getGraphicalView().updateGraphicalCityMap(controller);
    	
    	// TODO remember where it was remove > modif return of method removeStep 
    	// that way the redo will be more direct 
    	
        //return null;
    }

    /**
     * @return
     */
    
    public void undoCommand() {
    	
    	// true for the pickup, false for the delivery 
        deliveryTour.addIntermediateStep(beforePickup, pickup,true);
        deliveryTour.addIntermediateStep(beforeDelivery, delivery,false);
        controller.getRequestList().getListRequests().add(r.getId(),r);
        controller.setDeliveryTour(deliveryTour);
    	
    	
        //return null;
    }

}