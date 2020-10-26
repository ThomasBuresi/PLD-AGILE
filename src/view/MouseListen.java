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
	
	private int panelWidth;
	
	private int panelHeight;

    /**
     * Default constructor
     */
    public MouseListen(Controller controller, int panelHeight, int panelWidth) {
    	this.controller = controller;
    	this.panelHeight = panelHeight;
    	this.panelWidth = panelWidth;
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
				float latMax = controller.getCityMap().getLatMax();
				float latMin = controller.getCityMap().getLatMin();
				float longMin = controller.getCityMap().getLongMin();
				float longMax = controller.getCityMap().getLongMax();
				for (Map.Entry <Long, Intersection> entry : listIntersection.entrySet()) {
					int xInter = (int)Math.round((entry.getValue().getLongitude()-longMin)/(longMax-longMin)*panelWidth);
					int yInter = panelHeight - (int)Math.round((entry.getValue().getLatitude()-latMin)/(latMax-latMin)*panelHeight);
					if ((xCoord >= xInter - 2) && (xCoord<= xInter + 2) && (yCoord >= yInter - 2) && (yCoord<= yInter + 2)) {
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}