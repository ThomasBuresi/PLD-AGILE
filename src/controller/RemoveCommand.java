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
	 * index to add the element for redo
	 */
	private int index;
	
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
     * 
     */
    public void doCommand() {
    	beforeDelivery=deliveryTour.removeStep(delivery);
    	beforePickup=deliveryTour.removeStep(pickup);
    	
    	int i=0;
    	index = -1;
    	List <Request> requests = controller.getRequestList().getListRequests();
    	for (Request res : requests) {
    		if (res.getId() == r.getId()) {
    			index = i;
    		}
    		i++;
    	}
    	if (index != -1) {
    		controller.getRequestList().getListRequests().remove(index);
    	}    
    	
    	deliveryTour.setReqlist(controller.getRequestList());
    	
    	//Delivery tour path updated directly in method remove or add 
    	controller.setDeliveryTour(deliveryTour);
    	
    	
    	// TODO remember where it was remove > modif return of method removeStep 
    	// that way the redo will be more direct 

    	

    }

    
    public void undoCommand() {
    	// true for the pickup, false for the delivery 
    	deliveryTour.addIntermediateStep(beforePickup, pickup, true,(float)r.getPickupDuration()/60f);
        deliveryTour.addIntermediateStep(beforeDelivery, delivery, false,(float)r.getDeliveryDuration()/60f);
         
        controller.getRequestList().getListRequests().add(index,r);
        
        deliveryTour.setReqlist(controller.getRequestList());
        
        controller.setDeliveryTour(deliveryTour);
    }

}