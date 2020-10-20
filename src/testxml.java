import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;

import model.Intersection;

import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File; 
import model.CityMap;
import model.Intersection;
import model.Segment;


public class testxml {
  public static void main(String[] args) {
	  try{  
<<<<<<< HEAD
		  File file = new File("src/resources/smallMap.xml");  
=======
		  File file = new File("C:\\Users\\agath\\OneDrive\\Documents\\IF\\4IF\\Mas\\fichiersXML2020\\smallMap.xml");  
>>>>>>> agathe
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		  DocumentBuilder db = dbf.newDocumentBuilder();  
		  Document doc = db.parse(file);  
		  doc.getDocumentElement().normalize();  
		  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
<<<<<<< HEAD
		  NodeList nodeList = doc.getElementsByTagName("map");  
		  	for (int i = 0; i < nodeList.getLength(); i++) {  
		  		Node node = nodeList.item(i);  
		  		System.out.println("\nNode Name :" + node.getNodeName());  
		  		if (node.getNodeType() == Node.ELEMENT_NODE){  
		  			Element eElement = (Element) node;  
		  			System.out.println("id: "+ eElement.getAttribute("id"));  
		  			System.out.println("Latitude: "+ eElement.getAttribute("latitude"));  
		  			System.out.println("Longitude: "+ eElement.getAttribute("longitude"));
		   
		  		}  
=======
		  NodeList nodeList = doc.getElementsByTagName("intersection");
		  float latmin = 99.9f;
		  float latmax = 0.0f;
		  float longmin = 99.9f;
		  float longmax = 0.0f;
		  for (int i = 0; i < nodeList.getLength(); i++) {  
		  	Node node = nodeList.item(i);  
		  	System.out.println("\nNode Name :" + node.getNodeName());  
		  	if (node.getNodeType() == Node.ELEMENT_NODE){  
		  		Element eElement = (Element) node;
		  		Intersection inter = new Intersection(Float.parseFloat(eElement.getAttribute("latitude")),Float.parseFloat(eElement.getAttribute("longitude")),Long.parseLong(eElement.getAttribute("id")));
		  		if (inter.getLatitude()> latmax){
		  			latmax = inter.getLatitude();
		  		}
		  		else if(inter.getLatitude()< latmin) {
		  			latmin = inter.getLatitude();
		  		}
		  		if (inter.getLongitude()> longmax){
		  			longmax = inter.getLongitude();
		  		}
		  		else if(inter.getLongitude()< longmin) {
		  			longmin = inter.getLongitude();
		  		}
		  		System.out.println(inter.toString());  
		  		
		  		
>>>>>>> agathe
		  	}  
		  }
		System.out.println("Latitude min = " + latmin);
	  	System.out.println("Latitude max = " + latmax);
	  	System.out.println("Longitude min = " + longmin);
  		System.out.println("Longitude max = " + longmax);
		 }   
		  catch (Exception e){  
		  e.printStackTrace(); 
	  }  

	  
    //Bienvenue sur le projet AGILE 
  }
  
}
