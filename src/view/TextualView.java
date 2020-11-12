package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.sun.tools.javac.util.Pair;

import controller.Controller;
import controller.MapRequestsLoadedState;
import controller.RemoveRequestState;
import model.DeliveryTour;
import model.Intersection;
import model.Request;
import model.RequestList;
import model.Segment;
import model.TimeDelivery;

/**
 * View of the requests in a textual form : detailed in a table. 
 * @authors H4112 
 */

public class TextualView extends JPanel{ 

	/**
	 * Controller
	 */
	private Controller controller;
	
	/**
	 * List of the requests corresponding to the map that will be highlighted on it if necessary. 
	 */
	private RequestList requestList;
	
	/**
	 * Delivery tour that we will have to print on the map. 
	 */
	private DeliveryTour deliveryTour;
	
	/**
	 * Table used to display the requests and tour details. 
	 */
	private JTable requestTable;
	
	/**
	 * Scroll pane used to add a scroll bar to the table.
	 */
	private JScrollPane scrollPane;
	
	/**
	 * To remember which cell is selected 
	 */
	private int iSelectedRequest;
	
	
    /**
     * Contructor of the textual view
     * @param controller
     */
    public TextualView(Controller controller) {
    	super(new GridLayout(1,0));
    	setBounds(950,60,300,460);
        setBackground(Color.white);
        
        iSelectedRequest = -1;
        
        this.controller = controller;
        
    	requestTable = new JTable();
    	requestTable.setPreferredScrollableViewportSize(new Dimension(300, 460));
    	requestTable.setFillsViewportHeight(true);
    	scrollPane = new JScrollPane(requestTable);
        this.add(scrollPane);
        
        if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    		//this.add(requestTable);
    		fillTable();
    		
    		requestTable.setVisible(true);
    	} else {
    		requestList = null;
    		System.err.println("RequestList is null");
    	}
        
        
        
        repaint();
    }

    /**
     * Fill the table when the requests data is loaded in the application.
     */
    public void fillTable() {
    	this.remove(scrollPane);
    	
    	List<Request> requests = requestList.getListRequests();
		DefaultTableModel tableModel = new DefaultTableModel();   
		tableModel.addColumn("Requests");
		String str ="";
		int i = 1;
		//Get Address from coordinates API
    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");
    	requestList.getDeparture().setAddress(jOpenCageGeocoder);
		for (Request res : requests) {
			
	    	//Set the address in the intersection to later not call the API again and get it back to show it in the table
			String deliveryAddress = res.getDeliveryAddress().setAddress(jOpenCageGeocoder);
			String pickupAddress = res.getPickupAddress().setAddress(jOpenCageGeocoder);

			str="<HTML>" + ("Request Id - "+i+" : ") + "<br>" + ("PICKUP - "+pickupAddress) + "<br>" + ("DELIVERY - "+deliveryAddress) + "</HTML>";
			
			tableModel.insertRow(tableModel.getRowCount(), new Object[] { str });
			
			i++;
		}
		
		requestTable = new JTable(tableModel);
		requestTable.getColumnModel().getColumn(0).setMinWidth(300);
		
		int j = 0 ;
		while(j < tableModel.getRowCount()) {
			requestTable.setRowHeight(j, 110);
			j++;
		}
		
		scrollPane = new JScrollPane(requestTable);
        this.add(scrollPane);
    }
    
    /**
     * Fill the table when the requests are re ordered according to the calculated delivery tour.
     */
    public void orderTable() {
    	this.remove(scrollPane);
    	
    	requestList=controller.getRequestList();
    	
    	deliveryTour.setReqlist(requestList);
    	
    	List <TimeDelivery> times = deliveryTour.computeTime();
    	
    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");
    	
    	
    	List <Pair<Intersection, List<Segment>>> tour=deliveryTour.getTour();
    	List<Request> requestsNotOrdered = requestList.getListRequests();
    	
    	//Boolean which is false if it's a pickup, true if it's a delivery
    	List<Pair<Pair<Integer, Boolean>, Intersection>> intersections = new ArrayList<Pair<Pair<Integer, Boolean>, Intersection>>();
    	
    	//Re order the requests corresponding to the tour 
    	//go through the tour once and through the request list as many times as necessary to order them 
    	int i=0;
    	for(Pair<Intersection, List<Segment>> pair : tour) {
    		//we don't want to process the intersections corresponding to the warehouse
    		//we assume that the requests can't have the same pick up point/delivery point
    		if((i!=0) && (i!=tour.size()-1)) {
    			long intersectionId = pair.fst.getIdIntersection();

    			for (int j=0; j<requestsNotOrdered.size(); ++j) {
    				if(requestsNotOrdered.get(j).getPickupAddress().getIdIntersection()==intersectionId) {
    					intersections.add(new Pair<Pair<Integer, Boolean>, Intersection>(new Pair<Integer, Boolean>(requestsNotOrdered.get(j).getId(), false),pair.fst)); 
    				}
    				if(requestsNotOrdered.get(j).getDeliveryAddress().getIdIntersection()==intersectionId) {
    					intersections.add(new Pair<Pair<Integer, Boolean>, Intersection>(new Pair<Integer, Boolean>(requestsNotOrdered.get(j).getId(), true),pair.fst)); 
    				}
    			}
    		}
    		
    		i++;
    	}

    	  	
		DefaultTableModel tableModel = new DefaultTableModel();   
		tableModel.addColumn("Requests");
		String str;
		
		//at 0 is the time for the departure which we don't display 
		int p=1;
		
		for (Pair<Pair<Integer, Boolean>, Intersection> pair : intersections) {
			String address = pair.snd.getName();
			
			if(address==null) {
				address = pair.snd.setAddress(jOpenCageGeocoder);
			}

			str="<HTML>" + ("Request Id - " + (pair.fst.fst+1) +" : ") + "<br>";
			if (!pair.fst.snd) {
				str += "PICKUP - " + address +" at "+times.get(p).toString()+ "</HTML>"; 
			} else {
				str+= "DELIVERY - " + address +" at "+times.get(p).toString()+ "</HTML>"; 
			}
			
			p++;
			tableModel.insertRow(tableModel.getRowCount(), new Object[] { str });
		}
		
		requestTable = new JTable(tableModel);
		requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		requestTable.getColumnModel().getColumn(0).setMinWidth(300);
		
		int k = 0 ;
		while(k < tableModel.getRowCount()) {
			requestTable.setRowHeight(k, 80);
			k++;
		}
		
		ListSelectionModel selectionModel = requestTable.getSelectionModel();

		selectionModel.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        handleSelectionEvent(e);
		    }
		});
		
		scrollPane = new JScrollPane(requestTable);
        this.add(scrollPane);
    }
    
    
    /**
     * Highligh tin the table the request corresponding to a given id. 
     * @param id
     */
    public void highlightTable(int id) {
    	int []rows = {-1,-1};
    	iSelectedRequest=id;
    	
    	if(id!=-1) {
    		
        	int k = 0 ;
        	
        	int j=0;
    		while(k < requestTable.getRowCount()) {
    			
    			String s = requestTable.getModel().getValueAt(k, 0).toString();
    			String idRequestS = s.substring("<HTML>Request Id - ".length(), "<HTML>Request Id - ".length()+3);
    			String str = idRequestS.replaceAll("[^-?0-9]+", ""); 
    			System.out.println("we get as an id : "+str);

    			int idRequest = Integer.parseInt(str);
    			
    			if(id==(idRequest-1)) {
    				//we need a custom cell renderer s
    				rows[j]=k;
    				j++;
    			}
    			k++;
    		}
    	}

		requestTable.getColumnModel().getColumn(0).setCellRenderer(new HighlightCellRenderer(rows));

		repaint();
    }
    
 
    /**
     * Listener that reacts if a row of the table is clicked but only during specific stated of the controller
     * highlights this given request. 
     * @param e event
     */
    protected void handleSelectionEvent(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;

        String strSource= e.getSource().toString();
        
        int start = strSource.indexOf("{")+1,
            stop  = strSource.length()-1;
        int index = Integer.parseInt(strSource.substring(start, stop));
        
        String s = requestTable.getModel().getValueAt(index, 0).toString();
		String idRequestS = s.substring("<HTML>Request Id - ".length(), "<HTML>Request Id - ".length()+3);
		String str = idRequestS.replaceAll("[^-?0-9]+", ""); 
		System.out.println("we get as an id : "+str);

		iSelectedRequest = Integer.parseInt(str)-1;
		
		if(!(controller.getCurrentState() instanceof MapRequestsLoadedState)){
			highlightTable(iSelectedRequest);
			//update graphical view ?? with iSelectedRequest ! 
			controller.getWindow().getGraphicalView().updateHighlight(iSelectedRequest);
			if(!(controller.getCurrentState() instanceof RemoveRequestState)) {
				controller.setCurrentState(controller.removeRequestState);
				controller.getWindow().setVisibleRemove();
				
			}
		}
		
    }

    /**
     * Update the content of the table and so the textual view. 
     * @param controller
     */
    public void update(Controller controller) {
        
    	System.out.println("update textual view");
    	if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    		if (controller.getDeliveryTour() != null) {
        		deliveryTour = controller.getDeliveryTour();
        		orderTable();
        		System.out.println("TABLE ORDERED ");
        		this.updateUI(); // This helps for the undo / redo
        	} else {
        		deliveryTour = null;
        		fillTable();
        		System.err.println("DeliveryTour is null");
        	}
    	} else {
    		requestList = null;
    		this.remove(scrollPane);
    		System.err.println("RequestList is null");
    	}
        
        repaint();
    }
    
    /**
     * Getter of the id of the selected request in the table when cliqued. 
     * @return id 
     */
    public int getISelectedRequest () {
    	return iSelectedRequest ;
    }



}