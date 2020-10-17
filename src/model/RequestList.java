package model;
import java.util.*;

/**
 * 
 */
public class RequestList {
	
	/**
     * 
     */
    protected Intersection departure;

    /**
     * 
     */
    protected Date departureTime;


    /**
     * 
     */
    protected String filePath;
    
    /**
     * Default constructor
     */
    public RequestList() {
    }


    /**
     * 
     */
    public void fillRequests() {
        // TODO implement here
    }


	public Intersection getDeparture() {
		return departure;
	}


	public Date getDepartureTime() {
		return departureTime;
	}


	public String getFilePath() {
		return filePath;
	}

}