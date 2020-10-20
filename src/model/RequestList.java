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
 * 
 */
public class RequestList {

	/**
	 * Default constructor
	 */
	public RequestList(String filePath, CityMap cityMap) {
		this.filePath = filePath;
		this.cityMap = cityMap;
	}

	/**
	 * 
	 */
	protected Intersection departure;

	/**
	 * 
	 */
	protected int departureTime;

	/**
	 * 
	 */
	protected String filePath;

	/**
	 * 
	 */
	protected CityMap cityMap;
	
	/**
	 * 
	 */
	protected List<Request> listRequests;
	
	/**
	 * 
	 */
	public void fillRequests() {
		// TODO implement here
		try {
			File file = new File(filePath);
			System.err.println("Loading requests file " + filePath);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.err.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList depotList = doc.getElementsByTagName("depot");
			if(depotList.getLength() != 1) {
				System.err.println("Error : found " + depotList.getLength() + " depots instead of one");
				return;
			}
			Element depot = (Element) depotList.item(0);
			departure = cityMap.listIntersection.get(Long.parseLong(depot.getAttribute("address")));
			departureTime = TimeUtils.timeToSeconds(depot.getAttribute("departureTime"));
			NodeList nodeList = doc.getElementsByTagName("request");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					Intersection pickupAddress = cityMap.getIntersection(Long.valueOf(eElement.getAttribute("pickupAddress")));
					Intersection deliveryAddress = cityMap.getIntersection(Long.valueOf(eElement.getAttribute("deliveryAddress")));
					int pickupDuration = Integer.valueOf(eElement.getAttribute("pickupDuration"));
					int deliveryDuration = Integer.valueOf(eElement.getAttribute("deliveryDuration"));
					Request request = new Request(deliveryDuration, deliveryAddress, pickupAddress, pickupDuration);
					System.err.println(request);
					listRequests.add(request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}