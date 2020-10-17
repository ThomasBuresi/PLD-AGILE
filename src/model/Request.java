package model;
import java.util.*;

/**
 * 
 */
public class Request {

    /**
     * 
     */
    protected int deliveryDuration;

    /**
     * 
     */
    protected Intersection deliveryAddress;
    
    /**
     * 
     */
    protected Intersection pickupAddress;
    
    /**
     * 
     */
    protected int pickupDuration;

    /**
     * Default constructor
     */
    public Request() {
    }

	public int getDeliveryDuration() {
		return deliveryDuration;
	}

	public Intersection getDeliveryAddress() {
		return deliveryAddress;
	}

	public Intersection getPickupAddress() {
		return pickupAddress;
	}

	public int getPickupDuration() {
		return pickupDuration;
	}



}