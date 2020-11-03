package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import controller.Controller;
import model.Intersection;

/**
 * 
 */
public class MouseListen implements MouseListener{

	private Controller controller;
	
	private GraphicalView graphicalView;
	
	private Window window;
	
	private int panelWidth;
	
	private int panelHeight;
	
	private int pressedX;
	
	private int pressedY;

    /**
     * Default constructor
     */
    public MouseListen(Controller controller, GraphicalView graphicalView, Window window) {
    	this.window = window;
    	this.graphicalView = graphicalView;
    	this.controller = controller;
    	this.panelHeight = this.graphicalView.getHeight();
    	this.panelWidth = this.graphicalView.getWidth();
    }

    @Override
	public void mouseEntered(MouseEvent e) {
		
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());
		int xCoord = e.getX();
		int yCoord = e.getY();
		HashMap<Long, Intersection> listIntersection;
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
		} 
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		pressedX = e.getX();
		pressedY = e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int relX = e.getX();
		int relY = e.getY();
		
		if (graphicalView.graphicalCityMap.graphicalSegment != null && Math.abs(relX - pressedX)>20 && Math.abs(relY - pressedY) > 20) {
			System.out.println(pressedX + " " + pressedY + " " + relX + " " + relY);
			float latMax = graphicalView.graphicalCityMap.graphicalSegment.getLatMaxMap();
			float latMin = graphicalView.graphicalCityMap.graphicalSegment.getLatMinMap();
			float longMin = graphicalView.graphicalCityMap.graphicalSegment.getLongMinMap();
			float longMax = graphicalView.graphicalCityMap.graphicalSegment.getLongMaxMap();
			//System.out.println(latMax + " " + latMin + " " + longMax + " " + longMin);
			longMin = longMin + (float)pressedX/panelWidth*(longMax - longMin);
			longMax = longMax - ((float)(panelWidth - relX))/panelWidth*(longMax - longMin);
			latMin = latMin + (float)pressedY/panelHeight*(latMax - latMin);
			latMax = latMax - ((float)(panelHeight-relY))/panelHeight*(latMax - latMin);
			//System.out.println(latMax + " " + latMin + " " + longMax + " " + longMin);
			graphicalView.graphicalCityMap.graphicalSegment.setLatMaxMap(latMax);
			graphicalView.graphicalCityMap.graphicalSegment.setLatMinMap(latMin);
			graphicalView.graphicalCityMap.graphicalSegment.setLongMaxMap(longMax);
			graphicalView.graphicalCityMap.graphicalSegment.setLongMinMap(longMin);
			if (graphicalView.graphicalCityMap.graphicalIntersection != null) {
				//System.out.println("updating...");
				graphicalView.graphicalCityMap.graphicalIntersection.setLatMaxMap(latMax);
				graphicalView.graphicalCityMap.graphicalIntersection.setLatMinMap(latMin);
				graphicalView.graphicalCityMap.graphicalIntersection.setLongMaxMap(longMax);
				graphicalView.graphicalCityMap.graphicalIntersection.setLongMinMap(longMin);
			}
		}
		window.repaint();
	}


}