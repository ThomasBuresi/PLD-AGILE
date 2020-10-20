package model;
import java.util.*;

/**
 * 
 */
public class Intersection {
	
	/**
     * Longitude of the intersection (in degrees)
     */
    protected float longitude;

    /**
     * Latitude of the intersection (in degrees)
     */
    protected float latitude;

    /**
     * Id of the intersection
     */
    protected long idIntersection;
    
    /**
     * List of all the segments that have as origin this
     * intersection
     */
    protected List<Segment> listSegments;
    
    /**
     * Default constructor
     */
    public Intersection() {
    	longitude = 0.0f;
    	latitude = 0.0f;
    	idIntersection=0;
    }
    
    /**
     * Constructor of Intersection
     * @param longitude
     * @param latitude
     * @param idIntersection
     */
    public Intersection(float longitude, float latitude, long idIntersection) {
    	this.longitude = longitude;
    	this.latitude = latitude;
    	this.idIntersection= idIntersection;
    	this.listSegments = new ArrayList<Segment>();
    			
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
	 * Add a <code>Segment</code> to the list of Segments of the Intersection
	 * (<code>listSegments</code>)
	 * @param s
	 */
	public void addSegment(Segment s) {
		listSegments.add(s);
	}

	public List<Segment> getListSegments() {
		return listSegments;
	}
   
}