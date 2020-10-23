package view;

import java.awt.Color;
import java.util.*;

import javax.swing.JPanel;

import controller.Controller;
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
    }



    /**
     * 
     */
    public void update() {
        // TODO implement here
    }



	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}