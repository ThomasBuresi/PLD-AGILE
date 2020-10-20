import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File;  


public class testxml {
  public static void main(String[] args) {
	  try{  
		  File file = new File("src/resources/smallMap.xml");  
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		  DocumentBuilder db = dbf.newDocumentBuilder();  
		  Document doc = db.parse(file);  
		  doc.getDocumentElement().normalize();  
		  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
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
		  	}  
		  }   
		  catch (Exception e){  
		  e.printStackTrace(); 
		  }  

	  
    //Bienvenue sur le projet AGILE 
  }
  
}
