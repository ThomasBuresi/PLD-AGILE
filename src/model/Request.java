package model;
import java.util.*;

/**
 * 
 */
public class Request {

	/**
     * Default constructor
     */
    public Request(int deliveryDuration, Intersection deliveryAddress, Intersection pickupAddress, int pickupDuration) {
		this.deliveryDuration = deliveryDuration;
		this.deliveryAddress = deliveryAddress;
		this.pickupAddress = pickupAddress;
		this.pickupDuration = pickupDuration;
	}

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





}