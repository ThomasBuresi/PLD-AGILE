package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import controller.AddRequestState;
import controller.Controller;
import controller.DeliveryTourState;
import controller.RemoveRequestState;
import controller.State;
import model.Intersection;

/**
 * 
 */
public class MouseListen extends MouseAdapter{

	private Controller controller;
	
	private GraphicalView graphicalView;
	
//	private Window window;
//	
//	private int panelWidth;
//	
//	private int panelHeight;
	
	private int pressedX;
	
	private int pressedY;

    /**
     * Default constructor
     */
    public MouseListen(Controller controller, GraphicalView graphicalView, Window window) {
//    	this.window = window;
    	this.graphicalView = graphicalView;
    	this.controller = controller;
//    	this.panelHeight = this.graphicalView.getHeight();
//    	this.panelWidth = this.graphicalView.getWidth();
    }

    @Override
	public void mouseEntered(MouseEvent e) {
		
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int xCoord = e.getX();
		int yCoord = e.getY();
		
		controller.leftClick(xCoord,yCoord);
		
//		State currentState = controller.getCurrentState();
//		if(currentState instanceof DeliveryTourState) {
//			
//			//only the clicks to remove are detected in this state 
//			
//			controller.leftClick(xCoord,yCoord);
//		} else if(currentState instanceof RemoveRequestState){
//				
//			int xCoord = e.getX();
//			int yCoord = e.getY();
//			//only the clicks to remove are detected in this state 
//			
//			//on the click if it's on the same point or not different actions
//			// go bakc to other state if on the same 
//			// other changes the display 
//			
//			
//		} else if (currentState instanceof AddRequestState) {
//			//4 points to detect ? how ? 
//		}
//		
		
		//controller.addRequest(xCoord, yCoord);
		/*HashMap<Long, Intersection> listIntersection;
		if (controller.getCityMap() != null) {
			listIntersection = controller.getCityMap().getListIntersection();
			if (listIntersection != null) {
				float latMax = graphicalView.graphicalCityMap.graphicalSegment.getLatMaxMap();
				float latMin = graphicalView.graphicalCityMap.graphicalSegment.getLatMinMap();
				float longMin = graphicalView.graphicalCityMap.graphicalSegment.getLongMinMap();
				float longMax = graphicalView.graphicalCityMap.graphicalSegment.getLongMaxMap();
				for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
					int xInter = (int)Math.round((entry.getValue().getLongitude()-longMin)/(longMax-longMin)*panelWidth);
					int yInter = panelHeight - (int)Math.round((entry.getValue().getLatitude()-latMin)/(latMax-latMin)*panelHeight);
					if ((xCoord >= xInter - 3) && (xCoord<= xInter + 3) && (yCoord >= yInter - 3) && (yCoord<= yInter + 3)) {
						System.out.println(entry.getValue());
					}
				}
			}
		} */
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedX = e.getX();
		pressedY = e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int relX = e.getX();
		int relY = e.getY();
		if (Math.abs(relX - pressedX)>20 && Math.abs(relY - pressedY) > 20) {
			int panelHeight = graphicalView.getHeight();
	    	int panelWidth = graphicalView.getWidth();
	    	
	    	float leftX = (pressedX < relX ? pressedX : relX);
	    	float rightX = (pressedX < relX ? relX : pressedX);
	    	float upY = (pressedY < relY ? pressedY : relY);
	    	float downY = (pressedY < relY ? relY : pressedY);
	    	
	    	//Get the coordinates of the map at the moment
			float latMax = graphicalView.graphicalCityMap.getGraphicalSegment().getLatMaxMap();
			float latMin = graphicalView.graphicalCityMap.getGraphicalSegment().getLatMinMap();
			float longMin = graphicalView.graphicalCityMap.getGraphicalSegment().getLongMinMap();
			float longMax = graphicalView.graphicalCityMap.getGraphicalSegment().getLongMaxMap();

			//Calculate the new coordinates of the zone to zoom in
			longMin = longMin + leftX/panelWidth*(longMax - longMin);
			longMax = longMin + rightX/panelWidth*(longMax - longMin);
			latMin = latMax - downY/panelHeight*(latMax - latMin);
			latMax = latMax - upY/panelHeight*(latMax - latMin);

			controller.zoomIn(pressedX, pressedY, longMin, longMax, latMin, latMax);
		}
	}


}