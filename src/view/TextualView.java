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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.sun.tools.javac.util.Pair;

import controller.Controller;
import controller.RemoveRequestState;
import model.DeliveryTour;
import model.Intersection;
import model.Request;
import model.RequestList;
import model.Segment;
import model.TimeDelivery;

/**
 * 
 */
//@SuppressWarnings("deprecation")
public class TextualView extends JPanel{ //implements Observer {

	/**
     * Default constructor
     */
	private Controller controller;
	
	private RequestList requestList;
	
	private DeliveryTour deliveryTour;
	
	private JTable requestTable;
	
	private JScrollPane scrollPane;
	
	/**
	 * To remember which cell is selected 
	 */
	private int iSelectedRequest;
	
	
    /**
     * Default constructor
     */
    public TextualView(Controller controller) {
    	super(new GridLayout(1,0));
    	setBounds(950,60,300,460);
        setBackground(Color.white);
        
        iSelectedRequest=-1;
        
        this.controller=controller;
        
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
		//Get Address from coordinates API
    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");
    	requestList.getDeparture().setAddress(jOpenCageGeocoder);
		for (Request res : requests) {
			
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
    	
    	requestList=controller.getRequestList();
    	List <TimeDelivery> times = deliveryTour.computeTime();
    	
    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");
    	
    	System.out.println(requestList.getListRequests().toString());
    	
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
		
		//at 0 is the time for the departure which we don't display 
		int p=1;
		
		for (Pair<Pair<Integer, Boolean>, Intersection> pair : intersections) {
			String address = pair.snd.getName();
			
			if(address==null) {
				address = pair.snd.setAddress(jOpenCageGeocoder);
			}
			
			str="<HTML>" + ("Request " + (pair.fst.fst+1) +" : ") + "<br>";
			if (!pair.fst.snd) {
				str += "PICKUP - " + address +" at "+times.get(p).toString()+ "</HTML>"; 
			} else {
				str+= "DELIVERY - " + address +" at "+times.get(p).toString()+ "</HTML>"; 
			}
			
			p++;
			tableModel.insertRow(tableModel.getRowCount(), new Object[] { str });
		}
		
		//MyCellRenderer MyRenderer=new MyCellRenderer();
		
		requestTable = new JTable(tableModel);
		requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		
		ListSelectionModel selectionModel = requestTable.getSelectionModel();

		selectionModel.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        handleSelectionEvent(e);
		    }
		});
		
		
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
     * TODO
     */
    public void highlightTable(int id) {
    	int []rows = {-1,-1};
    	
    	iSelectedRequest=id;
    	
    	if(id!=-1) {
    		Request r = requestList.getListRequests().get(id);
        	int k = 0 ;
        	
        	int j=0;
    		while(k < requestTable.getRowCount()) {
    			//yourString.substring(yourString.indexOf("no") + 3 , yourString.length());
    			String s = requestTable.getModel().getValueAt(k, 0).toString();
    			String idRequestS = s.substring("<HTML>Request ".length(), "<HTML>Request ".length()+3);
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
    
    //+listener on the table
    
    
    protected void handleSelectionEvent(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;

        // e.getSource() returns an object like this
        // javax.swing.DefaultListSelectionModel 1052752867 ={11}
        // where 11 is the index of selected element when mouse button is released

        String strSource= e.getSource().toString();
        
        
        int start = strSource.indexOf("{")+1,
            stop  = strSource.length()-1;
        int index = Integer.parseInt(strSource.substring(start, stop));
        
        String s = requestTable.getModel().getValueAt(index, 0).toString();
		String idRequestS = s.substring("<HTML>Request ".length(), "<HTML>Request ".length()+3);
		String str = idRequestS.replaceAll("[^-?0-9]+", ""); 
		System.out.println("we get as an id : "+str);

		iSelectedRequest = Integer.parseInt(str)-1;
		
		
		highlightTable(iSelectedRequest);
		
		
		//update graphical view ?? with iSelectedRequest ! 
		controller.getWindow().getGraphicalView().updateHighlight(iSelectedRequest);
		
		if(!(controller.getCurrentState() instanceof RemoveRequestState)) {
			controller.setCurrentState(controller.removeRequestState);
			controller.getWindow().setVisibleRemove();
			
		}
		
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
        		System.out.println("TABLE ORDERED");
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
    
    public int getISelectedRequest () {
    	return iSelectedRequest ;
    }



}