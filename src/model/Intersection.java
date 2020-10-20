package model;
import java.util.*;

/**
 * 
 */
public class Intersection {

    /**
     * Default constructor
     */
    public Intersection() {
    	longitude = 0.0f;
    	latitude = 0.0f;
    	idIntersection=0;
    	
    			
    }
    
    /**
     * constructor
     */
    public Intersection(float longitude, float latitude, long idIntersection) {
    	this.longitude = longitude;
    	this.latitude = latitude;
    	this.idIntersection= idIntersection;
    	
    			
    }

    @Override
	public String toString() {
		return "Intersection [longitude=" + longitude + ", latitude=" + latitude + ", idIntersection=" + idIntersection
				+ "]";
	}

	/**
     * 
     */
    protected float longitude;

    /**
     * 
     */
    protected float latitude;



    /**
     * 
     */
    protected long idIntersection;





}