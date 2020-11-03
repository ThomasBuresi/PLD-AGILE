package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
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
    	//this.remove(requestTable);
    	this.remove(scrollPane);
    	List<Request> requests = requestList.getListRequests();
		DefaultTableModel tableModel = new DefaultTableModel();   
		tableModel.addColumn("Requests");
		String str ="";
		int i = 1;
		for (Request res : requests) {
			//Get Address from coordinates API
	    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");

			String deliveryAddress = res.getDeliveryAddress().toAddress(jOpenCageGeocoder);
			String pickupAddress = res.getPickupAddress().toAddress(jOpenCageGeocoder);
			//str= "Request "+i+" : \n PICKUP - "+pickupAddress+"\n DELIVERY - "+deliveryAddress;
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
		while(j<=(i-1)) {
			requestTable.setRowHeight(j, 100);
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
    	
    	List <Pair<Intersection, List<Segment>>> tour=deliveryTour.getTour();
    	
    	
    	this.remove(scrollPane);
    	
    	List<Request> requestsNotOrdered = requestList.getListRequests();
    	List<Request> requests = null;
    	
    	//Re order the requests corresponding to the tour 
    	//go through the tour once and through the request to order them as many times as necessary 
    	int i=0;
    	for(Pair<Intersection, List<Segment>> pair : tour) {
    		//we don't want to process the intersections corresponding to the warehouse
    		//we process only one over the two intersections in a request and 
    		//then check if the two correspond to add it in the request list ordered
    		if((i!=0) && (i!=tour.size())&&(i%2==0)) {
    			long intersectionId = pair.fst.getIdIntersection();
    			for(Request r:requestsNotOrdered) {
    				if(r.getDeliveryAddress().getIdIntersection()==intersectionId) {
    					if(r.getPickupAddress().getIdIntersection()==tour.get(i-1).fst.getIdIntersection()) {
    						requests.add(r);
    						requestsNotOrdered.remove(r);
    					}
    				}
    			}
    		}
    		
    		i++;
    	}
    	
    	
		DefaultTableModel tableModel = new DefaultTableModel();   
		tableModel.addColumn("Requests");
		String str ="";
		int j = 1;
		for (Request res : requests) {
			//Get Address from coordinates API
	    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");

			String deliveryAddress = res.getDeliveryAddress().toAddress(jOpenCageGeocoder);
			String pickupAddress = res.getPickupAddress().toAddress(jOpenCageGeocoder);
			//str= "Request "+i+" : \n PICKUP - "+pickupAddress+"\n DELIVERY - "+deliveryAddress;
			str="<HTML>" + ("Request "+j+" : ") + "<br>" + ("PICKUP - "+pickupAddress) + "<br>" + ("DELIVERY - "+deliveryAddress) + "</HTML>";
			
			tableModel.insertRow(tableModel.getRowCount(), new Object[] { str });
			
			j++;
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
		while(k<=(j-1)) {
			requestTable.setRowHeight(k, 100);
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
    
    /*
    @Override
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	if (requestList != null) {
    		List<Request> requests = requestList.getListRequests();
    		DefaultTableModel tableModel = new DefaultTableModel();   
    		tableModel.addColumn("Requests");
    		String str ="";
    		int i = 1;
    		for (Request res : requests) {
    			//Get Address from coordinates API
    	    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");

    			String deliveryAddress = res.getDeliveryAddress().toAddress(jOpenCageGeocoder);
    			String pickupAddress = res.getPickupAddress().toAddress(jOpenCageGeocoder);
    			//str= "Request "+i+" : \n PICKUP - "+pickupAddress+"\n DELIVERY - "+deliveryAddress;
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
    		while(j<=(i-1)) {
    			requestTable.setRowHeight(j, 100);
    			j++;
    		}
    		requestTable.setBounds(0, 0, 300,460);
    		//requestTable.setPreferredScrollableViewportSize(new Dimension(300, 460));
    		requestTable.setVisible(true);
    		//add(new JScrollPane(requestTable));
//    		requestTable.setAutoscrolls(this.getAutoscrolls());
    		add(requestTable); 
    		
    	}
    }

    */
    

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