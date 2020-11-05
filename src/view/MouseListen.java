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
		//System.out.println(e.getX() + " " + e.getY());
		
		State currentState = controller.getCurrentState();
		if(currentState instanceof DeliveryTourState) {
			int xCoord = e.getX();
			int yCoord = e.getY();
			//only the clicks to remove are detected in this state 
			
			controller.leftClick(xCoord,yCoord);
		} else if(currentState instanceof RemoveRequestState){
				
	    
			
		} else if (currentState instanceof AddRequestState) {
			//4 points to detect ? how ? 
		}
		
		
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
			controller.zoomIn(pressedX, pressedY, relX, relY);
		}
	}


}