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
	 * Button to load the map file 
	 */
	private JButton load_file;
	/**
	 * Button to load the file requests 
	 */
	private JButton load_requests_file;
	
	/**
	 * Label to change the descriptions of the actions to perform, indications for the user 
	 */
	private JLabel indications;
	
	/**
	 * Boolean to add the request file button only after adding a map file 
	 */
	public Boolean add_request_button = false;
	
	
	/**
	 * Main to test the window 
	 * @param args
	 */
	public static void main(String[] args) {
		//CityMap cityMap=new CityMap(); //useful citymap is created in Controller.java
		Controller controller = new Controller();
		//controller.loadFile("src/resources/largeMap.xml");
		//Frame
        Window  test = new Window(controller); //,cityMap); to be deleted
        test.setVisible(true);
    }
	
	
	/**
     * Default constructor to build the default basic window 
     */
    public Window(Controller controller) { //, CityMap cityMap) { to be deleted
    	
    	graphicalView = new GraphicalView(controller);
        textualView = new TextualView(controller);
    	
    	ButtonListener buttonListener = new ButtonListener(controller, this, graphicalView);
    	
    	// Main Frame
    	setTitle("Deliver'IF");
        setSize(1300,720);
        setLocation(100,10);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        
        // Load the files 
        load_file = new JButton("Load Map");
        load_file.addActionListener(buttonListener);
        load_file.setVisible(true);
        load_file.setBounds(950,20,145,30);
        
        // Load requests file
        load_requests_file = new JButton("Load Requests");
        load_requests_file.addActionListener(buttonListener);
        load_requests_file.setVisible(false);
        load_requests_file.setBounds(1105,20,145,30);
        
        //Indications for the user
        indications = new JLabel("Please load a map file (XML). ");
        
        
        //JScrollPane scroll = new JScrollPane();
        //for (int i = 0; i < 5; i++) {
        //    scroll.add(new JButton("Button n°" + i));
        //}
        
        //CityMap cityMap = controller.getCityMap();

        // to add the buttons in a complementary way to the textual view
//        JPanel right_panel = new JPanel();
//        right_panel.setBounds(950,100,300,500);
//        right_panel.add(load_file);
//        right_panel.add(load_requests_file);
        
        //For the bottom indication zone
        JPanel bottom_panel = new JPanel();
        bottom_panel.setBounds(20,550,900,100);
        bottom_panel.setBackground(Color.white);
        bottom_panel.add(indications);
        
        add(graphicalView);
        add(textualView);
       // add(right_panel);
        add(bottom_panel);
        add(load_file);
        add(load_requests_file);
        
        
    	
        repaint();
    	setVisible(true);
    	// Graphical and Textual views & other attributes ?
    	
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);	
    }
    
    public GraphicalView getGraphicalView () {
    	return graphicalView;
    }
    
    public TextualView getTextualView () {
    	return textualView;
    }
    
    public void setVisibleRequestButton() {
    	load_requests_file.setVisible(true);
    }



}