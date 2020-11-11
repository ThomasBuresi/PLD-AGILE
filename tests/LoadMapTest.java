import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.CityMap;
import model.Intersection;
import model.RequestList;

public class LoadMapTest {

	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadFileWorking() {
		try{  

			  File file = new File("test/resourcesTest/smallMap.xml");  
			
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			  DocumentBuilder db = dbf.newDocumentBuilder();  
			  Document doc = db.parse(file);  
			  doc.getDocumentElement().normalize();  			
			  NodeList nodeList = doc.getElementsByTagName("intersection");
			  float latmin = 99.9f;
			  float latmax = 0.0f;
			  float longmin = 99.9f;
			  float longmax = 0.0f;
			  for (int i = 0; i < nodeList.getLength(); i++) {  
				Node node = nodeList.item(i);  
				//System.out.println("\nNode Name :" + node.getNodeName());  
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
			  		//System.out.println(inter.toString());  

			  	}  
			  }
			  
			assertEquals(45.75400,latmin,0.00001 );
			assertEquals(4.857415,longmax,0.00001 );
			assertEquals( 4.879188,latmax,0.00001 );
			assertEquals(4.857400,longmin,0.00001 );

		  	}   		
			  catch (Exception e){  
			  e.printStackTrace(); 
			  }  
		
	}
	
	@Test
	public void testLoadMapWorking() {
		try{  

			  CityMap map = new CityMap("test/resourcesTest/smallMap.xml");
			  map.fillMap();
			  
			  System.out.println("\n"+map.getIntersection(25175791).toString());
			  
			  System.out.println("\nintersections "+map.getListIntersection().size());
			  
			  HashMap <Long, Intersection> list = map.getListIntersection();
			  
			  int i=0;
			  
			  //Iterate through the HashMap (to get all the segments for example)
			  for (Map.Entry <Long, Intersection> entry : list.entrySet()) {
				  i+= entry.getValue().getListSegments().size();
				  //System.out.println(entry.getKey() + "/" + entry.getValue().toString());
			  }
			  
			  System.out.println("segments " + i);
			  
			float longituteTest = map.getIntersection(1).getLongitude();
			float latitudeTest = map.getIntersection(1).getLatitude();
			
			// Lat and long of Intersection id 25175791
			assertEquals(4.857400,longituteTest,0.00001);
			assertEquals(45.75400,latitudeTest,0.00001);
			// Size and number of entry 
			assertEquals(5,map.getListIntersection().size());
			assertEquals(6,i);
			
		  	}   		
			  catch (Exception e){  
			  e.printStackTrace(); 
			  }  
		
	}
	
	@Test
	public void testLoadMapNotWorking() {
		try{  

			  CityMap map = new CityMap("tests/resourcesTest/smallMap.xml");
			  map.fillMap();
			  
			  System.out.println("\n"+map.getIntersection(25175791).toString());
			  
			  System.out.println("\nintersections "+map.getListIntersection().size());
			  
			  HashMap <Long, Intersection> list = map.getListIntersection();
			  
			  int i=0;
			  
			  //Iterate through the HashMap (to get all the segments for example)
			  for (Map.Entry <Long, Intersection> entry : list.entrySet()) {
				  i+= entry.getValue().getListSegments().size();
				  //System.out.println(entry.getKey() + "/" + entry.getValue().toString());
			  }
			  
			  System.out.println("segments " + i);
			  

			
			assertEquals(null,map.getIntersection(25175791).getLatitude());
			
		  	}   		
			  catch (Exception e){  
			  e.printStackTrace(); 
			  }  
		
	}

}
