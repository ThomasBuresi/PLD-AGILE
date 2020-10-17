package model;
import java.util.*;

/**
 * 
 */
public class Intersection {
	
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

    /**
     * Default constructor
     */
    public Intersection() {
    }
    
    /**
     * Constructor
     */
    public Intersection(long _id, float _latitude, float _longitude) {
    	this.idIntersection = _id;
    	this.latitude = _latitude;
    	this.longitude = _longitude;
    }

    


    public long getIdIntersection() {
    	return idIntersection;
    }

    public float getLatitude() {
    	return latitude;
    }
    
    public float getLongitude() {
    	return longitude;
    }
}