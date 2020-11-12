package model;

import java.io.File;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class CityMap {

	/**
	 * The path to the XML file containing the map (list of segments and
	 * intersections)
	 */
	protected String filePath;

	/**
	 * Map that stores all the intersections, the key being the id of the
	 * intersection and the value being the intersection itself
	 */
	protected HashMap<Long, Intersection> listIntersection;

	/**
	 * The minimal latitude of an intersection present in the map
	 */
	protected float latMin;

	/**
	 * The maximal latitude of an intersection present in the map
	 */
	protected float latMax;

	/**
	 * The minimal longitude of an intersection present in the map
	 */
	protected float longMin;

	/**
	 * The maximal longitude of an intersection present in the map
	 */
	protected float longMax;

	/**
	 * Default constructor
	 */
	public CityMap() {
	}

	/**
	 * Constructor of <code>CityMap</code>
	 * 
	 * @param filePath the path to the XML file containing the map
	 */
	public CityMap(String filePath) {
		this.filePath = filePath;
		this.listIntersection = new HashMap<Long, Intersection>();
		this.latMax = -200;
		this.latMin = 200;
		this.longMax = -200;
		this.longMin = 200;
	}

	/**
	 * Parses the XML file given by the attribute <code>filePath</code> and creates
	 * two list of nodes : one for the intersections and one for the segments. Then,
	 * calls the methods <code>fillIntersectionsList</code> and
	 * <code>fillSegmentsList</code> to fill the map of intersections and the list
	 * of segments for each intersection.
	 */
	public boolean fillMap() {
		try {
			File file = new File(filePath);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList intersectionsList = doc.getElementsByTagName("intersection");
			NodeList segmentsList = doc.getElementsByTagName("segment");
			return fillIntersectionsList(intersectionsList) && fillSegmentsList(segmentsList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	 * Fills the map <code>listIntersection</code> with all the intersections that
	 * are in the list of nodes given as parameter
	 * 
	 * @param intersectionsList the list of nodes of intersections from the XML file
	 */
	public boolean fillIntersectionsList(NodeList intersectionsList) {
		try {
			System.out.println("\nNumber of intersections: " + intersectionsList.getLength());
			for (int i = 0; i < intersectionsList.getLength(); i++) {
				Node node = intersectionsList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					String id_s = eElement.getAttribute("id");
					String lat_s = eElement.getAttribute("latitude");
					String long_s = eElement.getAttribute("longitude");
					if (id_s.length() > 0 && lat_s.length() > 0 && long_s.length() > 0) {
						long id = Long.parseLong(id_s);
						float lat = Float.parseFloat(lat_s);
						float longit = Float.parseFloat(long_s);
						Intersection inters = new Intersection(lat, longit, id);
						if (listIntersection.containsKey(id)) {
							Intersection conflictingInter = listIntersection.get(id);
							if (conflictingInter.getLatitude() != inters.getLatitude()
									|| conflictingInter.getLongitude() != inters.getLongitude()) {
								System.err.println("Duplicate intersection with conflicting information found");
								return false;
							}
						}
						listIntersection.put(id, inters);
						if (lat > latMax) {
							latMax = lat;
						} else if (lat < latMin) {
							latMin = lat;
						}
						if (longit > longMax) {
							longMax = longit;
						} else if (longit < longMin) {
							longMin = longit;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return !listIntersection.isEmpty();
	}

	/**
	 * Adds each <code>Segment</code> from the list of nodes given as parameter to
	 * the list of segments of the origin intersection
	 * 
	 * @param segmentsList the list of nodes of segments from the XML file
	 */
	public boolean fillSegmentsList(NodeList segmentsList) {
		try {

			System.out.println("\nNumber of segments: " + segmentsList.getLength());
			for (int i = 0; i < segmentsList.getLength(); i++) {
				Node node = segmentsList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					String idOrigin_s = eElement.getAttribute("origin");
					String idDest_s = eElement.getAttribute("destination");
					String length_s = eElement.getAttribute("length");
					if (idOrigin_s.length() > 0 && idDest_s.length() > 0 && length_s.length() > 0 ) {
						long idOrigin = Long.parseLong(idOrigin_s);
						long idDest = Long.parseLong(idDest_s);
						float length = Float.parseFloat(length_s);
						String name = eElement.getAttribute("name");
						if (name == "")
							name = "Name absent";
						Intersection origin = getIntersection(idOrigin);
						Intersection dest = getIntersection(idDest);
						if (origin != null && dest != null) {
							Segment seg = new Segment(name, origin, dest, length);
							getIntersection(idOrigin).addSegment(seg); // Add the segment to the list of segments of the origin
						}
					}													
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public HashMap<Long, Intersection> getListIntersection() {
		return listIntersection;
	}

	public float getLatMin() {
		return latMin;
	}

	public float getLatMax() {
		return latMax;
	}

	public float getLongMin() {
		return longMin;
	}

	public float getLongMax() {
		return longMax;
	}

}