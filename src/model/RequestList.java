package model;

import java.io.File;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.TimeUtils;

/**
 *List of all the requests imported from a compatible XML file. 
 * 
 * @author H4112
 *
 */
public class RequestList {
	
	/**
     * Intersection of departure of the delivery person
     * (Starting point)
     */
    protected Intersection departure;

    /**
     * The moment at which the delivery person starts the
     * delivery tour ( time in seconds )
     */
    protected int departureTime;
    
    /**
     * index 
     */
    protected int index;

    /**
	 * The path to the XML file containing the requests
	 */
    protected String filePath;
    
    
	/**
	 * The associated <code>CityMap</code> of the requests
	 */
	protected CityMap cityMap;
	
	/**
	 * The list of all the Requests contained in the Requests file
	 */
	protected List<Request> listRequests;
	
	/**
     * Default constructor
     */
    public RequestList() {
    }
    
    /**
	 * Constructor of <code>RequestList</code>
	 * @param filePath
	 * @param cityMap
	 */
	public RequestList(String filePath, CityMap cityMap) {
		this.filePath = filePath;
		this.cityMap = cityMap;
		this.listRequests = new ArrayList<Request>();
		this.index = 0;
	}
	
	/**
	 * Method that parses the XML Requests file and affects the <code>departure</code>
	 * intersection and the <code>departureTime</code>, and also adds all the requests
	 * in <code>listRequests</code>
	 */
	public boolean fillRequests() {
		
		try {
			File file = new File(filePath);
			System.err.println("Loading requests file " + filePath);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			//System.err.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList depotList = doc.getElementsByTagName("depot");
			if(depotList.getLength() != 1) {
				System.err.println("Error : found " + depotList.getLength() + " depots instead of one");
				return false;
			}
			Element depot = (Element) depotList.item(0);
			departure = cityMap.listIntersection.get(Long.parseLong(depot.getAttribute("address")));
			// would return null if no corresponding intersection
			if(departure==null) {
				System.err.println("Error : the intersection of departure was not found in the map");
				return false;
			}
			departureTime = TimeUtils.timeToSeconds(depot.getAttribute("departureTime"));
			NodeList nodeList = doc.getElementsByTagName("request");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					Intersection pickupAddress = cityMap.getIntersection(Long.valueOf(eElement.getAttribute("pickupAddress")));
					if(pickupAddress==null) {
						System.err.println("Error : the intersection of pickup " + Long.valueOf(eElement.getAttribute("pickupAddress")) + " was not found in the map");
						return false;
					}
					Intersection deliveryAddress = cityMap.getIntersection(Long.valueOf(eElement.getAttribute("deliveryAddress")));
					if(deliveryAddress==null) {
						System.err.println("Error : the intersection of delivery " + Long.valueOf(eElement.getAttribute("deliveryAddress")) + " was not found in the map");
						return false;
					}
					int pickupDuration = Integer.valueOf(eElement.getAttribute("pickupDuration"));
					int deliveryDuration = Integer.valueOf(eElement.getAttribute("deliveryDuration"));
					Request request = new Request(deliveryDuration, deliveryAddress, pickupAddress, pickupDuration, index);
					//System.err.println(request);
					listRequests.add(request);
					this.index++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true ; 
	}
	
	@Override
	public String toString() {
		String res = "RequestList [departure=" + departure + ", departureTime=" + departureTime + "]\nRequests :";
		for (Request req : listRequests) {
			res+="\n"+req;
		}
		return res;
	}
	
	// Getters and Setters 

	public Intersection getDeparture() {
		return departure;
	}


	public int getDepartureTime() {
		return departureTime;
	}


	public String getFilePath() {
		return filePath;
	}

	public CityMap getCityMap() {
		return cityMap;
	}

	public List<Request> getListRequests() {
		return listRequests;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}