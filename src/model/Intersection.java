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

    public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public long getIdIntersection() {
		return idIntersection;
	}

	public void setIdIntersection(long idIntersection) {
		this.idIntersection = idIntersection;
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