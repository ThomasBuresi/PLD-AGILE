package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.sun.tools.javac.util.Pair;

import controller.Controller;
import model.DeliveryTour;
import model.Intersection;
import model.Request;
import model.RequestList;
import model.Segment;

/**
 * 
 */
//@SuppressWarnings("deprecation")
public class TextualView extends JPanel{ //implements Observer {

	/**
     * Default constructor
     */
	private RequestList requestList;
	
	private DeliveryTour deliveryTour;
	
	private JTable requestTable;
	
	private JScrollPane scrollPane;
	
	
    /**
     * Default constructor
     */
    public TextualView(Controller controller) {
    	super(new GridLayout(1,0));
    	setBounds(950,60,300,460);
        setBackground(Color.white);
        //requestTable = new JTable();
    	//super(new GridLayout(1,0));
        
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

//    public void repaint(Graphics g) {
//		super.repaint();
//		paintComponent(g);
//	}
    
    public void fillTable() {
    	this.remove(scrollPane);
    	
    	List<Request> requests = requestList.getListRequests();
		DefaultTableModel tableModel = new DefaultTableModel();   
		tableModel.addColumn("Requests");
		String str ="";
		int i = 1;
		for (Request res : requests) {
			//Get Address from coordinates API
	    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");
	    	//Set the address in the intersection to later not call the API again and get it back to show it in the table
			String deliveryAddress = res.getDeliveryAddress().setAddress(jOpenCageGeocoder);
			String pickupAddress = res.getPickupAddress().setAddress(jOpenCageGeocoder);

			str="<HTML>" + ("Request "+i+" : ") + "<br>" + ("PICKUP - "+pickupAddress) + "<br>" + ("DELIVERY - "+deliveryAddress) + "</HTML>";
			
			tableModel.insertRow(tableModel.getRowCount(), new Object[] { str });
			
			i++;
		}
		
		//MyCellRenderer MyRenderer=new MyCellRenderer();
		
		requestTable = new JTable(tableModel);
		//JScrollPane tableSP = new JScrollPane(requestTable);
		//tableSP.setPreferredSize(new Dimension(400,800));
		//requestTable.setAutoscrolls(true);
		//requestTable.setLayout(null);
		requestTable.getColumnModel().getColumn(0).setMinWidth(300);
		//requestTable.getColumnModel().getColumn(0).setCellRenderer(MyRenderer);
		int j = 0 ;
		while(j < tableModel.getRowCount()) {
			requestTable.setRowHeight(j, 110);
			j++;
		}
		//requestTable.setBounds(0, 0, 300,460);
		//requestTable.setPreferredScrollableViewportSize(new Dimension(300, 460));
		//requestTable.setVisible(true);
		//add(new JScrollPane(requestTable));
//		requestTable.setAutoscrolls(this.getAutoscrolls());
		//add(requestTable);
		scrollPane = new JScrollPane(requestTable);
        this.add(scrollPane);
    }
    
    public void orderTable() {
    	this.remove(scrollPane);
    	
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
    					intersections.add(new Pair<Pair<Integer, Boolean>, Intersection>(new Pair<Integer, Boolean>(j, false),pair.fst));
    				}
    				if(requestsNotOrdered.get(j).getDeliveryAddress().getIdIntersection()==intersectionId) {
    					intersections.add(new Pair<Pair<Integer, Boolean>, Intersection>(new Pair<Integer, Boolean>(j, true),pair.fst));
    				}
    			}
    		}
    		
    		i++;
    	}

    	  	
		DefaultTableModel tableModel = new DefaultTableModel();   
		tableModel.addColumn("Requests");
		String str;
		
		for (Pair<Pair<Integer, Boolean>, Intersection> pair : intersections) {
			String address = pair.snd.getName();
			str="<HTML>" + ("Request " + (pair.fst.fst+1) +" : ") + "<br>";
			if (!pair.fst.snd) {
				str += "PICKUP - " + address + "</HTML>"; 
			} else {
				str+= "DELIVERY - " + address + "</HTML>"; 
			}
			
			
			tableModel.insertRow(tableModel.getRowCount(), new Object[] { str });
		}
		
		//MyCellRenderer MyRenderer=new MyCellRenderer();
		
		requestTable = new JTable(tableModel);
		//JScrollPane tableSP = new JScrollPane(requestTable);
		//tableSP.setPreferredSize(new Dimension(400,800));
		//requestTable.setAutoscrolls(true);
		//requestTable.setLayout(null);
		requestTable.getColumnModel().getColumn(0).setMinWidth(300);
		//requestTable.getColumnModel().getColumn(0).setCellRenderer(MyRenderer);
		int k = 0 ;
		while(k < tableModel.getRowCount()) {
			requestTable.setRowHeight(k, 80);
			k++;
		}
		//requestTable.setBounds(0, 0, 300,460);
		//requestTable.setPreferredScrollableViewportSize(new Dimension(300, 460));
		//requestTable.setVisible(true);
		//add(new JScrollPane(requestTable));
//		requestTable.setAutoscrolls(this.getAutoscrolls());
		//add(requestTable);
		scrollPane = new JScrollPane(requestTable);
        this.add(scrollPane);
    }
    
    

    /**
     * 
     */
    public void update(Controller controller) {
        
    	System.out.println("update textual view");
    	if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    		if (controller.getDeliveryTour() != null) {
        		deliveryTour = controller.getDeliveryTour();
        		orderTable();
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



}