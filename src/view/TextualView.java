package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import model.Request;
import model.RequestList;

/**
 * 
 */
@SuppressWarnings("deprecation")
public class TextualView extends JPanel implements Observer {

	/**
     * Default constructor
     */
	private RequestList requestList;
	
    /**
     * Default constructor
     */
    public TextualView(Controller controller) {
    	
    	setBounds(950,60,300,460);
        setBackground(Color.white);
        
        if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    	} else {
    		requestList = null;
    		System.err.println("RequestList is null");
    	}
        
        repaint();
    }

    public void repaint(Graphics g) {
		super.repaint();
		paintComponent(g);
	}
    
    @Override
    public void paintComponent(Graphics g) 
    {     
    	super.paintComponent(g);
    	if (requestList != null) {
    		List<Request> requests = requestList.getListRequests();
    		DefaultTableModel tableModel = new DefaultTableModel();    		
    		tableModel.addColumn("Requests");
    		for (Request res : requests) {
    			tableModel.insertRow(tableModel.getRowCount(), new Object[] { res.toString() });
    		}
    		JTable requestTable = new JTable(tableModel);
    		add(requestTable); // TABLE DOES NOT DISPLAY IN PANEL, HELP !! 
    	}
    }


    /**
     * 
     */
    public void update(Controller controller) {
        // TODO implement here
    	System.out.println("update textual view");
    	if (controller.getRequestList() != null) {
    		requestList = controller.getRequestList();
    	} else {
    		requestList = null;
    		System.err.println("RequestList is null");
    	}
        
        repaint();
    }



	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}