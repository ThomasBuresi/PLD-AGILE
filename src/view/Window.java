package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.Controller;
import model.CityMap;

/**
 * Window
 */
public class Window extends JFrame{

	/**
	 * Containing the city map graphical view 
	 */
	private GraphicalView graphicalView;
	
    /**
	 * Containing the textual view and description of the points 
	 */
	private TextualView textualView;
	
	/**
	 * Button to load the files and requests 
	 */
	private JButton load_file;
	private JButton load_requests;
	
	/**
	 * Label to change the descriptions of the actions to perform, indications for the user 
	 */
	private JLabel indications;
	
	
	/**
	 * Main to test the window 
	 * @param args
	 */
	public static void main(String[] args) {
		//CityMap cityMap=new CityMap(); //useful citymap is created in Controller.java
		Controller controller = new Controller();
		controller.loadFile("src/resources/smallMap.xml");
		//Frame
        Window  test = new Window(controller); //,cityMap); to be deleted
        test.setVisible(true);
    }
	
	
	/**
     * Default constructor to build the default basic window 
     */
    public Window(Controller controller) { //, CityMap cityMap) { to be deleted
    	
    	graphicalView = new GraphicalView(controller);
        textualView = new TextualView();
    	
    	ButtonListener buttonListener = new ButtonListener(controller, this, graphicalView);
    	
    	// Main Frame
    	setTitle("Deliver'IF");
        setSize(1300,720);
        setLocation(500,100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        
        // Load the files 
        load_file = new JButton("Load File");
        load_file.addActionListener(buttonListener);
        
        //Indications for the user
        indications = new JLabel("You may load a map file (XML). ");
        
        
        //JScrollPane scroll = new JScrollPane();
        //for (int i = 0; i < 5; i++) {
        //    scroll.add(new JButton("Button n°" + i));
        //}
        
        //CityMap cityMap = controller.getCityMap();
                

        
        

        // to add the buttons in a complementary way to the textual view
        JPanel right_panel = new JPanel();
        right_panel.setBounds(950,20,300,500);
        right_panel.add(load_file);
        
        
        
        //For the bottom indication zone
        JPanel bottom_panel = new JPanel();
        bottom_panel.setBounds(20,550,900,100);
        bottom_panel.setBackground(Color.white);
        bottom_panel.add(indications);
        
        add(graphicalView);
        add(textualView);
        add(right_panel);
        add(bottom_panel);
        
        
    	
        repaint();
    	setVisible(true);
    	// Graphical and Textual views & other attributes ?
    	
    }
    
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);	
//    }
    
    public GraphicalView getGraphicalView () {
    	return graphicalView;
    }
    
    public TextualView getTextualView () {
    	return textualView;
    }



}