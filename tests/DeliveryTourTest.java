import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.byteowls.jopencage.JOpenCageGeocoder;

import controller.Controller;
import model.CityMap;
import model.DeliveryTour;
import model.Request;
import model.RequestList;
import model.Segment;
import tsp.DijkstraGraph;
import tsp.TSP;
import tsp.TSP1;
import tsp.TSP2;

class DeliveryTourTest {

	@Test
	void testDeliveryTour() {
		Controller c = new Controller();
		CityMap map = new CityMap("tests/resourcesTest/smallMap.xml");
		map.fillMap();
		RequestList reqlist = new RequestList("tests/resourcesTest/requestsSmall1.xml", map);
		reqlist.fillRequests();
		c.setRequestList(reqlist);
		c.setCityMap(map);

		DeliveryTour d = new DeliveryTour(c);

		TSP tsp = d.getTsp();
		d.addDeparture(reqlist.getDeparture());
		DijkstraGraph g = d.getG();
		d.addIntersectionDetail("");
		d.fillDeliveryTour(2000);
		for (int l = 1; l < 1 + 2 * reqlist.getListRequests().size(); l++) {
			int currentsolution = tsp.getSolution(l);

			List<Segment> segs = g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l - 1)];
			for (int i1 = 1; i1 < segs.size(); i1++) {
				System.out.println(segs.get(i1).toString());
			}

			if (currentsolution % 2 != 0) {
				System.out.println("even");
				System.out.println(currentsolution + "   " + (tsp.getSolution(l) / 2));
				System.out.println("Pickup Address :"
						+ reqlist.getListRequests().get(tsp.getSolution(l) / 2).getPickupAddress().getIdIntersection());
				d.addIntersectionDetail("Pickup Address");
				d.addStep(reqlist.getListRequests().get(tsp.getSolution(l) / 2).getPickupAddress(),
						g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l - 1)]);
			} else {
				System.out.println("odd");
				System.out.println(currentsolution + "   " + (tsp.getSolution(l) / 2 - 1));
				System.out.println("Delivery Address :" + reqlist.getListRequests().get(tsp.getSolution(l) / 2 - 1)
						.getDeliveryAddress().getIdIntersection());
				d.addIntersectionDetail("Delivery Address");
				d.addStep(reqlist.getListRequests().get(tsp.getSolution(l) / 2 - 1).getDeliveryAddress(),
						g.getSegmentPaths()[tsp.getSolution(l)][tsp.getSolution(l - 1)]);
			}

		}
		d.addIntersectionDetail("Return to Departure");
		d.addStep(reqlist.getDeparture(),
				g.getSegmentPaths()[tsp.getSolution(2 * reqlist.getListRequests().size())][tsp.getSolution(0)]);
		d.affiche();
		
		// Look up the addresses for points of interest before generating the roadmap :
		JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");
		reqlist.getDeparture().setAddress(jOpenCageGeocoder);
		for(Request r : reqlist) {
			r.getDeliveryAddress().setAddress(jOpenCageGeocoder);
			r.getPickupAddress().setAddress(jOpenCageGeocoder);
		}
		
		d.writeDeliveryTourToFile("test.txt");
	}

}
