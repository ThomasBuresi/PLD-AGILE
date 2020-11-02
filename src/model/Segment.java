package model;
import java.util.*;

/**
 * 
 */
public class Segment {
	
	/**
     * Name of the <code>Segment</code>
     */
    protected String name;

	/**
     * <code>Intersection</code> of origin of the <code>Segment</code>
     */
    protected Intersection origin;
    
    /**
     * <code>Intersection</code> of destination of the <code>Segment</code>
     */
    protected Intersection destination;


    /**
     * Length of the <code>Segment</code> in meters
     */
    protected float length;
	
	/**
     * Default constructor
     */
    public Segment() {
    	
    }

    /**
     * Constructor of <code>Segment</code>
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