package model;
import java.util.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

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
     * Exact address name of the intersection
     */
    protected String name;
    
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
     * 
     * @param latitude
     * @param longitude
     * @param idIntersection
     */
    public Intersection(float latitude, float longitude, long idIntersection) {
    	this.longitude = longitude;
    	this.latitude = latitude;
    	this.idIntersection= idIntersection;
    	this.listSegments = new ArrayList<Segment>();
    	this.name=null;
    			
    }
	
	/**
	 * Method to set and return the exact address of an intersection
	 * from an API using its coordinates
	 * 
	 * @param jOpenCageGeocoder class used by the API
	 */
	public String setAddress(JOpenCageGeocoder jOpenCageGeocoder) {
		
    	JOpenCageReverseRequest request = new JOpenCageReverseRequest((double)latitude, (double)longitude); //latitude, longitude
    	request.setLanguage("fr"); // prioritize results in a specific language using an IETF format language code
    	request.setNoDedupe(true); // don't return duplicate results
    	request.setLimit(5); // only return the first 5 results (default is 10)
    	request.setNoAnnotations(true); // exclude additional info such as calling code, timezone, and currency
    	request.setMinConfidence(3); // restrict to results with a confidence rating of at least 3 (out of 10)

    	JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

    	// get the formatted address of the first result:
    	this.name = response.getResults().get(0).getFormatted(); 
    	return this.name;
	}

	/**
	 * Add a <code>Segment</code> to the list of Segments of the Intersection
	 * (<code>listSegments</code>)
	 * @param s Segment to add
	 */
	public void addSegment(Segment s) {
		listSegments.add(s);
	}

	public List<Segment> getListSegments() {
		return listSegments;
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

	public String getName() {
		return name;
	}
   
}