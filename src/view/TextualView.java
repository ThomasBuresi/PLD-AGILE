package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.byteowls.jopencage.JOpenCageGeocoder;

import controller.Controller;
import model.Request;
import model.RequestList;

/**
 * 
 */
//@SuppressWarnings("deprecation")
public class TextualView extends JPanel{ //implements Observer {

	/**
     * Default constructor
     */
	private RequestList requestList;
	
	private JTable requestTable;
	
	
    /**
     * Default constructor
     */
    public TextualView(Controller controller) {
    	
    	setBounds(950,60,300,460);
        setBackground(Color.white);
        requestTable = new JTable();
        //JScrollBar bar = new JScrollBar();
        //this.setVerticalScrollBar(bar);
        
        if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    		this.add(requestTable);
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
    	this.remove(requestTable);
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
//		requestTable.setAutoscrolls(this.getAutoscrolls());
		add(requestTable); 
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
    		fillTable();
    	} else {
    		requestList = null;
    		System.err.println("RequestList is null");
    	}
        
        repaint();
    }



}