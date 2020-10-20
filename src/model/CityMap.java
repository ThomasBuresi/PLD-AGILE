package model;

import java.io.File;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import src.tsp.*;

/**
 * 
 */
public class CityMap {

	/**
	* The path to the XML file containing the map (list of segments 
	* and intersections)
	*/
	protected String filePath;

	/**
	 * Map that stores all the intersections, the key being the id of
	 * the intersection and the value being the intersection itself
	 */
	protected HashMap<Long, Intersection> listIntersection;

	/**
	 * Default constructor
	 */
	public CityMap() {
	}

	/**
	 * Constructor of <code>CityMap</code>
	 * @param _filePath
	 */
	public CityMap(String _filePath) {
		this.filePath = _filePath;
		this.listIntersection = new HashMap<Long, Intersection>();
	}

	/**
     * Parses the XML file given by the attribute <code>filePath</code>
     * and creates two list of nodes : one for the intersections and one
     * for the segments. Then, calls the methods <code>fillIntersectionsList</code>
     * and <code>fillSegmentsList</code> to fill the map of intersections
     * and the list of segments for each intersection.
     */
    public void fillMap() {
        try {
        	File file = new File(filePath);  
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    		DocumentBuilder db = dbf.newDocumentBuilder();  
    		Document doc = db.parse(file);  
    		doc.getDocumentElement().normalize();  
    		NodeList intersectionsList = doc.getElementsByTagName("intersection");
    		fillIntersectionsList(intersectionsList);
    		NodeList segmentsList = doc.getElementsByTagName("segment");
    		fillSegmentsList(segmentsList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	/**
	 * Returns the <code>Intersection</code> with the specified ID
	 * 
	 * @param id
	 */
	public Intersection getIntersection(long id) {
		return listIntersection.get(id);
	}

	/**
	 * Fills the map <code>listIntersection</code> with all the intersections
	 * that are in the list of nodes given as parameter
	 * @param intersectionsList
	 */
	public void fillIntersectionsList(NodeList intersectionsList) {
		try {
			System.out.println("\nNumber of intersections: " + intersectionsList.getLength());
			for (int i = 0; i < intersectionsList.getLength(); i++) {
				Node node = intersectionsList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					long id = Long.parseLong(eElement.getAttribute("id"));
					float lat = Float.parseFloat(eElement.getAttribute("latitude"));
					float longit = Float.parseFloat(eElement.getAttribute("longitude"));
					Intersection inters = new Intersection(lat, longit, id);
					listIntersection.put(id, inters);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds each <code>Segment</code> from the list of nodes given as parameter to the
	 * list of segments of the origin intersection
	 * @param segmentsList
	 */
	public void fillSegmentsList(NodeList segmentsList) {
		try {
			
			System.out.println("\nNumber of segments: " + segmentsList.getLength());
			for (int i = 0; i < segmentsList.getLength(); i++) {
				Node node = segmentsList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					long idOrigin = Long.parseLong(eElement.getAttribute("origin"));
					long idDest = Long.parseLong(eElement.getAttribute("destination"));
					float length = Float.parseFloat(eElement.getAttribute("length"));
					String name = eElement.getAttribute("name");
					Segment seg = new Segment(name, getIntersection(idOrigin), getIntersection(idDest), length);
					getIntersection(idOrigin).addSegment(seg); // Add the segment to the list of segments of the origin intersection
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<Long, Intersection> getListIntersection() {
		return listIntersection;
	}

}