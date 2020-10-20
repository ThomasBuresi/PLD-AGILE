package view;

import java.awt.Color;
import java.util.*;

import javax.swing.JPanel;

/**
 * 
 */
@SuppressWarnings("deprecation")
public class TextualView extends JPanel implements Observer {

    /**
     * Default constructor
     */
    public TextualView() {
    	
    	setBounds(950,20,300,500);
        setBackground(Color.white);
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