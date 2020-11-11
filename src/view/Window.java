package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

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
	 * Containing indications and legend
	 */
	private JPanel bottom_panel;
	
	/**
	 * Button to load the map file 
	 */
	private JButton load_file;
	/**
	 * Button to load the file requests 
	 */
	private JButton load_requests_file;
	/**
	 * Button to start the calculation of the delivery tour 
	 */
	private JButton calculate_tour;
	
	/**
	 * Button to continue the calculation if it's not over
	 */
	private JButton continue_calculation;
	
	/**
	 * Button to skip to continue the calculation if it's not over
	 */
	private JButton skip_continue_calculation;
	
	
	/**
	 * Button to switch to add mode 
	 */
	private JButton add_request;
	
	/**
	 * Button to confirm the add of a request to the tour
	 */
	private JButton add_confirm;
	
	/**
	 * Button to re initialise the selection of the intersections for the adding
	 */
	private JButton re_initialise;
	
	/**
	 * Button to remove a request from the tour
	 */
	private JButton remove_request;
	
	/**
	 * Button to cancel the removal or adding a request to the tour
	 */
	private JButton cancel_remove_request;
	
	/**
	 * Button to export the delivery tour
	 */
	private JButton export_tour;
	
	/**
	 * Button to undo the add/remove
	 */
	private JButton undo_button;
	
	/**
	 * Button to redo the add/remove
	 */
	private JButton redo_button;
	
	/**
	 * Button to zoom out the map to the initial state
	 */
	private JButton zoomOut;
	
	/**
	 * Text area to change the descriptions of the actions to perform (indications for the user)
	 */
	private JTextArea indications;
	
	/**
	 * Text area to show the legend of the map
	 */
	private JTextArea legend;
	
	/**
	 * Boolean to add the request file button only after adding a map file 
	 */
	public Boolean add_request_button = false;
	
	
	
	/**
	 * Main to test the window 
	 * @param args
	 */
	/*public static void main(String[] args) {
		//CityMap cityMap=new CityMap(); //useful citymap is created in Controller.java
		Controller controller = new Controller();
		//controller.loadFile("src/resources/largeMap.xml");
		//Frame
        Window  test = new Window(controller); //,cityMap); to be deleted
        test.setVisible(true);
    }*/
	
	
	/**
     * Default constructor to build the default basic window 
     */
    public Window(Controller controller) { //, CityMap cityMap) { to be deleted
    	
    	graphicalView = new GraphicalView(controller);
        textualView = new TextualView(controller);
        
    	ButtonListener buttonListener = new ButtonListener(controller, this, graphicalView, textualView);
    	MouseListen mouseListen = new MouseListen(controller, graphicalView, this);
    	
    	graphicalView.addMouseListener(mouseListen);
    	graphicalView.addMouseMotionListener(mouseListen);
    	// Main Frame
    	setTitle("Deliver'IF");
        setSize(1300,750);
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
        
        // Calculate the delivery tour 
        calculate_tour = new JButton("Calculate Delivery Tour");
        calculate_tour.addActionListener(buttonListener);
        calculate_tour.setVisible(false);
        calculate_tour.setBounds(950,550,300,30);
        
        // Continue to calculate the delivery tour for 20sec
        continue_calculation = new JButton("Continue calculation (20sec more)");
        continue_calculation.addActionListener(buttonListener);
        continue_calculation.setVisible(false);
        continue_calculation.setBounds(950,550,300,30);
     
        // Skip to continue to calculate the delivery tour for 20sec
        skip_continue_calculation = new JButton("Skip");
        skip_continue_calculation.addActionListener(buttonListener);
        skip_continue_calculation.setVisible(false);
        skip_continue_calculation.setBounds(950,590,300,30);  
        
     
        // add mode on
        add_request = new JButton("Add");
        add_request.addActionListener(buttonListener);
        add_request.setVisible(false);
        add_request.setBounds(950,550,300,30);
        
        // add request to the tour
        add_confirm = new JButton("Confirm Add");
        add_confirm.addActionListener(buttonListener);
        add_confirm.setVisible(false);
        add_confirm.setBounds(950,550,300,30);
        
        // re initialise the intersections selected to be added 
        re_initialise = new JButton("Re initialise the selection");
        re_initialise.addActionListener(buttonListener);
        re_initialise.setVisible(false);
        re_initialise.setBounds(950,630,300,30);
        
        // remove requests from the tour
        remove_request = new JButton("Remove");
        remove_request.addActionListener(buttonListener);
        remove_request.setVisible(false);
        remove_request.setBounds(950,550,300,30);
        
        // cancel remove requests from the tour
        cancel_remove_request = new JButton("Cancel");
        cancel_remove_request.addActionListener(buttonListener);
        cancel_remove_request.setVisible(false);
        cancel_remove_request.setBounds(950,590,300,30);
        
        
        // export the tour
        export_tour = new JButton("Export Tour File");
        export_tour.addActionListener(buttonListener);
        export_tour.setVisible(false);
        export_tour.setBounds(950,590,300,30);
        
        // undo
        undo_button = new JButton("Undo");
        undo_button.addActionListener(buttonListener);
        undo_button.setVisible(false);
        undo_button.setBounds(950,630,145,30);
        
        // redo
        redo_button = new JButton("Redo");
        redo_button.addActionListener(buttonListener);
        redo_button.setVisible(false);
        redo_button.setBounds(1105,630,145,30);
        
        
        
        //Indications for the user
        indications = new JTextArea("Your Next Step : \r\n"
        		+ "Please load your City Map File (XML). \r\n"
        		);
        indications.setEditable(false);
        indications.setLineWrap(true);
        indications.setWrapStyleWord(true);
        indications.setBounds(10, 10, 610, 140);
        //indications.setBorder(BorderFactory.createLineBorder(Color.black));
        
        legend = new JTextArea();
        

        
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
        bottom_panel = new JPanel();
        bottom_panel.setLayout(null);
        bottom_panel.setBounds(20,550,900,150);
        bottom_panel.setBackground(Color.white);
        bottom_panel.add(indications);
        
        zoomOut = new JButton("-");
        zoomOut.setBounds(860, 0, 40, 30);
        zoomOut.addActionListener(buttonListener);
        zoomOut.setVisible(false);
        graphicalView.add(zoomOut);
        
        
        
        this.add(graphicalView);
        this.add(textualView);
        this.add(bottom_panel);
        
        this.add(load_file);
        this.add(load_requests_file);
        this.add(calculate_tour);
        this.add(continue_calculation);
        this.add(skip_continue_calculation);
        this.add(add_request);
        this.add(add_confirm);
        this.add(re_initialise);
        this.add(remove_request);
        this.add(cancel_remove_request);
        this.add(export_tour);
        this.add(undo_button);
        this.add(redo_button);
        

    	
        this.repaint();
    	this.setVisible(true);
    	// Graphical and Textual views & other attributes ?
    	
    	/*
    	//Get Address from coordinates API
    	JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("fbedb322032b496e89461ac6473217a4");

    	JOpenCageReverseRequest request = new JOpenCageReverseRequest(45.760174, 4.877455); //latitude, longitude
    	request.setLanguage("fr"); // prioritize results in a specific language using an IETF format language code
    	request.setNoDedupe(true); // don't return duplicate results
    	request.setLimit(5); // only return the first 5 results (default is 10)
    	request.setNoAnnotations(true); // exclude additional info such as calling code, timezone, and currency
    	request.setMinConfidence(3); // restrict to results with a confidence rating of at least 3 (out of 10)

    	JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

    	// get the formatted address of the first result:
    	String formattedAddress = response.getResults().get(0).getFormatted(); 
    	System.out.println(formattedAddress);
    	// formattedAddress is now '12 Rue Fr�d�ric Passy, 69100 Villeurbanne, France'
    	*/
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);	
        if (bottom_panel.getComponentCount() > 1) {
        	g.setColor(Color.black);
        	g.fillRect(715, 611, 10, 10);
        	g.setColor(Color.red);
        	g.fillRect(715, 627, 10, 10);
        	g.fillOval(715, 643, 10, 10);
        }
        
    }
    
    public GraphicalView getGraphicalView () {
    	return graphicalView;
    }
    
    public TextualView getTextualView () {
    	return textualView;
    }
    
    public void setVisibleRequestButton() {
    	indications.setText("Your Next Step : \r\n"
    			+ "Please load your Requests File (XML).\r\n "
    			+"--> Zoom in : draw a rectangle by clicking on the map where you want to zoom.\r\n"
        		+"--> Zoom out : button at the right upper corner of the map. ");
    	load_requests_file.setVisible(true);
    	skip_continue_calculation.setVisible(false);
    	add_request.setVisible(false);
    	export_tour.setVisible(false);
    	undo_button.setVisible(false);
    	redo_button.setVisible(false);
    	zoomOut.setVisible(true);
    	this.repaint();
    	
    }
    
    public void setLoadingRequests() {
    	indications.setText("\n\n\n                  Loading the requests... ");
    }
    
    public void setErrorAtOpening() {

//    	//indications.setText(indications.getText()+ " \n\r Please load a compatible file");
//    	JFrame popup = new JFrame ("Warning");
//        popup.setSize(200,100);
//        popup.setLocation(500,300);
//        popup.setResizable(false);
//        
//        JTextArea textPopup = new JTextArea ("            ---Error--- \n   Please load a compatible file. ");
//        
//        //textPopup.setBounds(20,10,150,80);
//        
//        textPopup.setForeground(Color.red);
//        textPopup.setEditable(false);
//        
//        popup.add(textPopup);
//        popup.setVisible(true);
    	
    	JOptionPane.showMessageDialog(this, "Please load a compatible file");
    }
    
    public void setVisibleCalculateButton() {
    	indications.setText("Your Next Step : \r\n"
    			+ "Click on Calculate Delivery Tour to run the algorithm and get an optimized delivery tour on the map. \r\n"
    			+"--> Zoom in : draw a rectangle by clicking on the map where you want to zoom.\r\n"
        		+"--> Zoom out : button at the right upper corner of the map. ");
    	calculate_tour.setVisible(true);
    	continue_calculation.setVisible(false);
    	skip_continue_calculation.setVisible(false);
    	add_request.setVisible(false);
    	export_tour.setVisible(false);
    	undo_button.setVisible(false);
    	redo_button.setVisible(false);
    }
    
    public void setContinueCalculation() {
    	indications.setText("Your Next Step : \r\n"
    			+ "Click on Continue to a more optimized tour since it will be calculated for 20 additional seconds. \r\n"
    			+"--> Zoom in : draw a rectangle by clicking on the map where you want to zoom.\r\n"
        		+"--> Zoom out : button at the right upper corner of the map. ");
    	calculate_tour.setVisible(false);
    	continue_calculation.setVisible(true);
    	skip_continue_calculation.setVisible(true);
    	this.repaint();
    }
    
    public void setVisibleAddExport () {
    	indications.setText("Your Next Step : \r\n"
    			+ "Your Delivery Tour has been computed, you have 3 options : \r\n"
    			+"1. You can export the Delivery Tour.\r\n"
    			+"2. You can go add request by clickin on Add.\r\n" 
    			+"3. You can remove a request by selecting one of its elements on the map or in the list.\r\n"
    			+"--> Zoom in : draw a rectangle by clicking on the map where you want to zoom.\r\n"
        		+"--> Zoom out : button at the right upper corner of the map. ");
    	calculate_tour.setVisible(false);
    	continue_calculation.setVisible(false);
    	skip_continue_calculation.setVisible(false);
    	remove_request.setVisible(false);
    	cancel_remove_request.setVisible(false);
    	add_request.setVisible(true);
    	export_tour.setVisible(true);
    	re_initialise.setVisible(false);
    	undo_button.setVisible(true);
    	redo_button.setVisible(true);
    	load_file.setEnabled(true);
    	load_requests_file.setEnabled(true);
    	this.repaint();
    }
    
    public void setVisibleRemove () {
    	indications.setText("Your Next Step : \r\n"
    			+ "You can click on a point and its request elements are highlighted.\r\n"
    			+"If you wish to cancel your selection reclick on one of its elements, otherwise click on \"Remove\" to delete the request from the Delivery Tour. \r\n"
    			+"--> Zoom in : draw a rectangle by clicking on the map where you want to zoom.\r\n"
        		+"--> Zoom out : button at the right upper corner of the map. ");
    	remove_request.setVisible(true);
    	add_confirm.setVisible(false);
    	cancel_remove_request.setVisible(true);
    	load_file.setEnabled(false);
    	load_requests_file.setEnabled(false);
    	add_request.setVisible(false);
    	re_initialise.setVisible(false);
    	export_tour.setVisible(false);
    	undo_button.setVisible(false);
    	redo_button.setVisible(false);
    	this.repaint();
    }
    
    
    public void setVisibleAddMode() {
    	indications.setText("Your Next Step : \r\n"
    			+ "To add a request, please select 4 points in the following order : \r\n"
    			+"1. The new pickup point.         "
    			+"2. The point in the Tour preceding this pickup.\r\n" 
    			+"3. The new delivery point.       "
    			+"4. The point in the Tour preceding this delivery.\r\n"
    			+"Then confirm addition by clicking \"Confirm Add\" or select others and click on \"Re initialise the selection\".\r\n"
    			+"--> Zoom in : draw a rectangle by clicking on the map where you want to zoom.\r\n"
        		+"--> Zoom out : button at the right upper corner of the map. ");
    	
    	calculate_tour.setVisible(false);
    	continue_calculation.setVisible(false);
    	skip_continue_calculation.setVisible(false);
    	
    	add_request.setVisible(false);
    	export_tour.setVisible(false);
    	undo_button.setVisible(false);
    	redo_button.setVisible(false);
    	
    	//can't load when adding 
    	load_file.setVisible(true);
    	load_requests_file.setVisible(true);
    	load_file.setEnabled(false);
    	load_requests_file.setEnabled(false);
    	
    	//button to confirm the Add 
    	add_confirm.setVisible(true);
    	cancel_remove_request.setVisible(true);
    	re_initialise.setVisible(true);
    	
    	
    	
    	repaint();
    
    }
    
    public void addLegend() {
    	bottom_panel.remove(legend);
    	legend = new JTextArea("        LEGEND :\n"
    			+ "-   Deposit (Start point)\n" 
    			+ "-   Pickup address\n"
    			+ "-   Delivery address");
    	legend.setBounds(710, 10, 200, 80);
    	legend.setEditable(false);
    	bottom_panel.add(legend);
    	this.repaint();
    }
    
    public void removeLegend() {
    	bottom_panel.remove(legend);
    	this.repaint();
    }
    
    public void setPopUpExport() {
    	JOptionPane.showMessageDialog(this, "The delivery route was exported correctly.");
    }

    public void setVisiblePopUpMultipleRequests() {
    	JOptionPane.showMessageDialog(this, "Many requests on the clicked intersection, \n please select the one you want in the table.");
    }


}
