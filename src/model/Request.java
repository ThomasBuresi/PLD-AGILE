package model;
import java.util.*;

/**
 * 
 */
public class Request {

    /**
     * Duration of the delivery in seconds
     */
    protected int deliveryDuration;

    /**
     * <code>Intersection</code> of delivery
     */
    protected Intersection deliveryAddress;
    
    /**
     * <code>Intersection</code> of pickup
     */
    protected Intersection pickupAddress;
    
    /**
     * Duration of the pickup in seconds
     */
    protected int pickupDuration;
    
    /**
     * Default constructor
     */
    public Request() {
    }
    
	/**
     * Constructor of <code>Request</code>
     * @param deliveryDuration
     * @param deliveryAddress
     * @param pickupAddress
     * @param pickupDuration
     */
    public Request(int deliveryDuration, Intersection deliveryAddress, Intersection pickupAddress, int pickupDuration) {
		this.deliveryDuration = deliveryDuration;
		this.deliveryAddress = deliveryAddress;
		this.pickupAddress = pickupAddress;
		this.pickupDuration = pickupDuration;
	}


	@Override
	public String toString() {
		return "Request [deliveryDuration=" + deliveryDuration + ", deliveryAddress=" + deliveryAddress
				+ ", pickupAddress=" + pickupAddress + ", pickupDuration=" + pickupDuration + "]";
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