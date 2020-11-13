package model;

/**
 * 
 */
public class Request {

    /**
     * Duration of the delivery in seconds
     */
    protected int deliveryDuration;

    /**
     * Intersection of delivery
     */
    protected Intersection deliveryAddress;
    
    /**
     * Intersection of pickup
     */
    protected Intersection pickupAddress;
    
    /**
     * Duration of the pickup in seconds
     */
    protected int pickupDuration;
    
    /**
     * Id of the request 
     */
    protected int id; 
    
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
     * @param id 
     */
    public Request(int deliveryDuration, Intersection deliveryAddress, Intersection pickupAddress, int pickupDuration, int id) {
		this.deliveryDuration = deliveryDuration;
		this.deliveryAddress = deliveryAddress;
		this.pickupAddress = pickupAddress;
		this.pickupDuration = pickupDuration;
		this.id=id;
	}


	@Override
	public String toString() {
		return "Request [deliveryDuration=" + deliveryDuration + ",\n deliveryAddress=" + deliveryAddress
				+ ",\n pickupAddress=" + pickupAddress + ",\n pickupDuration=" + pickupDuration + "]";
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
	
	public int getId() {
		return id;
	}



}