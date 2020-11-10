package controller;

import java.util.*;

import model.DeliveryTour;
import model.Intersection;
import model.Request;

/**
 * 
 */
public class AddCommand implements Command {

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
     *
     */
    public AddCommand(Controller c, DeliveryTour d, ArrayList<Intersection> toBeAdded) {
       controller = c;
       deliveryTour = d;
       pickup = toBeAdded.get(0);
       beforePickup = toBeAdded.get(1);
       delivery = toBeAdded.get(2);
       beforeDelivery = toBeAdded.get(3);
       //10 mins by default for the durations times
       r = new Request(600,delivery,pickup,600,c.getRequestList().getIndex());
    }

    /**
     * 
     */
    public void doCommand() {
        deliveryTour.addIntermediateStep(beforePickup, pickup, true);
        deliveryTour.addIntermediateStep(beforeDelivery, delivery, false);

        controller.getRequestList().setIndex(controller.getRequestList().getIndex()+1);
        controller.getRequestList().getListRequests().add(r);
        
        controller.setDeliveryTour(deliveryTour);
    }

    /**
     * 
     */
    public void undoCommand() {
    	deliveryTour.removeStep(pickup);
    	deliveryTour.removeStep(delivery);
    	int i=0;
    	int index = -1;
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
    	
    	controller.setDeliveryTour(deliveryTour);
    }

 

}