package model;
import java.util.*;

/**
 * 
 */
public class Segment {

    /**
     * Default constructor
     */
	public Segment(String name, Intersection origin, Intersection destination, float length) {
		this.name = name;
		this.origin = origin;
		this.destination = destination;
		this.length = length;
	}

    /**
     * 
     */
    protected String name;

	/**
     * 
     */
    protected Intersection origin;
    
    /**
     * 
     */
    protected Intersection destination;


    /**
     * 
     */
    protected float length;




}