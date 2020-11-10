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
       controller=c;
       deliveryTour=d;
       beforePickup=toBeAdded.get(0);
       pickup=toBeAdded.get(1);
       beforeDelivery=toBeAdded.get(2);
       delivery=toBeAdded.get(3);
       //10 mins by default for the durations times
       r= new Request(600,delivery,pickup,600,c.getRequestList().getListRequests().size());
    }

    /**
     * @return
     */
    public void doCommand() {
        deliveryTour.addIntermediateStep(beforePickup, pickup,true);
        deliveryTour.addIntermediateStep(beforeDelivery, delivery,false);
        //test id add  remove add
        controller.getRequestList().getListRequests().add(r);
        controller.setDeliveryTour(deliveryTour);
    }

    /**
     * @return
     */
    public void undoCommand() {
    	deliveryTour.removeStep(pickup);
    	deliveryTour.removeStep(delivery);
    	
    	controller.getRequestList().getListRequests().remove(r.getId());
    	
    	controller.setDeliveryTour(deliveryTour);
    }

    /**
     * 
     */
    public void Operation1() {
        // TODO implement here
    }

 

}