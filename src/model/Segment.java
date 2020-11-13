package model;

/**
 * Segments contained in the map.
 * 
 * @author H4112
 *
 */
public class Segment {
	
	/**
     * Name of the Segment
     */
    protected String name;

	/**
     * Intersection of origin of the Segment
     */
    protected Intersection origin;
    
    /**
     * Intersection of destination of the Segment
     */
    protected Intersection destination;


    /**
     * Length of the Segment in meters
     */
    protected float length;
	
	/**
     * Default constructor
     */
    public Segment() {
    	
    }

    /**
     * Constructor of Segment
     * @param name 
     * @param origin
     * @param destination
     * @param length
     */
	public Segment(String name, Intersection origin, Intersection destination, float length) {
		this.name = name;
		this.origin = origin;
		this.destination = destination;
		this.length = length;
	}


	@Override
	public String toString() {
		return "Segment [name=" + name + ", origin=" + origin + ", destination=" + destination + ", length=" + length
				+ "]";
	}

	//Getters and setters 
	
	public String getName() {
		return name;
	}

	public Intersection getOrigin() {
		return origin;
	}

	public Intersection getDestination() {
		return destination;
	}

	public float getLength() {
		return length;
	}
    

}