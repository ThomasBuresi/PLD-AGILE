package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controller.Controller;


/**
 * Mouse listener to react from the user's mouse interactions with the application.
 * 
 * @author H4112
 */
public class MouseListen extends MouseAdapter{

	/**
	 * Controller
	 */
	private Controller controller;
	
	/**
	 * Graphical representation of the map and the zooms actions.
	 */
	private GraphicalView graphicalView;
	
	/**
	 * X coordinate when mouse pressed for zoom in
	 */
	private int pressedX;
	
	/**
	 * Y coordinate when mouse pressed for zoom in
	 */
	private int pressedY;

    /**
     * Contructor of MouseListen
     * 
     * @param controller
     * @param graphicalView
     * @param window
     */
    public MouseListen(Controller controller, GraphicalView graphicalView, Window window) {
    	this.graphicalView = graphicalView;
    	this.controller = controller;
    }

    @Override
	public void mouseEntered(MouseEvent e) {
		
    }

    /**
     * Get the coordinates for the click that will be processed in the states classes. 
     */
	@Override
	public void mouseClicked(MouseEvent e) {
		int xCoord = e.getX();
		int yCoord = e.getY();
		
		controller.leftClick(xCoord,yCoord);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int currentX = e.getX();
		int currentY = e.getY();
		int leftX = (pressedX < currentX ? pressedX : currentX);
    	int rightX = (pressedX < currentX ? currentX : pressedX);
    	int upY = (pressedY < currentY ? pressedY : currentY);
    	int downY = (pressedY < currentY ? currentY : pressedY);
    	int width = rightX - leftX;
    	int height = downY - upY;
    	if (width >= 20 && height >= 20) {
			graphicalView.setDrawRect(true);
			graphicalView.setRectCoord(leftX, upY, width, height);
			graphicalView.repaint();
    	} else {
    		graphicalView.setDrawRect(false);
			graphicalView.repaint();
    	}
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
		graphicalView.setDrawRect(false);
		int relX = e.getX();
		int relY = e.getY();
		if (Math.abs(relX - pressedX) >= 20 && Math.abs(relY - pressedY) >= 20) {
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
			longMin = longMin + leftX*(longMax - longMin)/panelWidth;
			longMax = longMin + rightX*(longMax - longMin)/panelWidth;
			latMin = latMax - downY*(latMax - latMin)/panelHeight;
			latMax = latMax - upY*(latMax - latMin)/panelHeight;

			controller.zoomIn(pressedX, pressedY, longMin, longMax, latMin, latMax);
		}
	}


}