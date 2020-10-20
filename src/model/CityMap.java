package model;


import java.util.*;

/**
 * 
 */
public class CityMap {

    /**
     * Default constructor
     */
    public CityMap() {
    }

    /**
     * 
     */
    protected String filePath;
    
    /**
     * 
     */
    protected HashMap<Long,Intersection> listIntersection;
    
    
    /**
     * 
     */
    protected HashMap<Long,Segment> listSegment;


    /**
     * 
     */
    public void fillMap() {
        // TODO implement here
    }
    
    /**
     * Returns the intersection with the specified ID
     * @param id
     */
    public Intersection getIntersection(long id) {
    	return listIntersection.get(id);
    }

}