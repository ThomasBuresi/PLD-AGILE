import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File; 
import model.CityMap;
import model.Intersection;
import model.Segment;


public class testxml {
  public static void main(String[] args) {
	  try{  
		  CityMap cityMap = new CityMap("C:\\Disk D\\INSA\\4IF\\AGILE\\fichiersXML2020\\smallMap.xml");
		  
		  File file = new File("C:\\Disk D\\INSA\\4IF\\AGILE\\fichiersXML2020\\smallMap.xml");  
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		  DocumentBuilder db = dbf.newDocumentBuilder();  
		  Document doc = db.parse(file);  
		  doc.getDocumentElement().normalize();  
		  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
		  //Element map = (Element)( doc.getElementsByTagName("map").item(0));  
		  NodeList intersectionsList = doc.getElementsByTagName("intersection");
		  System.out.println("\nNumber of intersections: " + intersectionsList.getLength());
		  for (int i = 0; i < 5; i++) {  
			  Node node = intersectionsList.item(i);  
			  System.out.println("\nNode Name : " + node.getNodeName());  
			  if (node.getNodeType() == Node.ELEMENT_NODE){ 
				  Element eElement = (Element) node;  
	  			  System.out.println("id: "+ eElement.getAttribute("id"));  
	  			  System.out.println("Latitude: "+ eElement.getAttribute("latitude"));  
	  			  System.out.println("Longitude: "+ eElement.getAttribute("longitude"));
	  		  }  
	  	  }
		  
		  NodeList segmentsList = doc.getElementsByTagName("segment");
		  System.out.println("\nNumber of segments: " + segmentsList.getLength());
		  for (int i = 0; i < 5; i++) {  
			  Node node = segmentsList.item(i);  
			  System.out.println("\nNode Name : " + node.getNodeName());  
			  if (node.getNodeType() == Node.ELEMENT_NODE){ 
				  Element eElement = (Element) node;  
				  System.out.println("Name: "+ eElement.getAttribute("name"));
	  			  System.out.println("Length: "+ eElement.getAttribute("length") + " m.");  
	  			  System.out.println("Origin: "+ eElement.getAttribute("origin"));  
	  			  System.out.println("Destination: "+ eElement.getAttribute("destination")); 
	  		  }  
	  	  }  
	  }   
	  catch (Exception e){  
		  e.printStackTrace(); 
	  }  

	  
    //Bienvenue sur le projet AGILE 
  }
  
}
