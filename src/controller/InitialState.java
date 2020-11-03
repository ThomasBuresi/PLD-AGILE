package controller;

import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;

import model.CityMap;
import view.GraphicalView;
import view.TextualView;
import view.Window;

/**
 * 
 */
public class InitialState implements State {
	
	/**
	 * To open a dialog window to load the files 
	 */
	private JFileChooser fc;
	
	@Override
	public void loadMap(Controller c,  Window w) {
		fc = new JFileChooser();
    	fc.setCurrentDirectory( new File ( System.getProperty("user.dir") + 
    			System.getProperty("file.separator")+ "src" + 
    			System.getProperty("file.separator")+ "resources"));
		
		int val_ret = fc.showOpenDialog(null);

        if (val_ret == JFileChooser.APPROVE_OPTION) {
           File file = fc.getSelectedFile();
           
           //to print the absolute path of the file
           //System.out.println("Chemin absolu : "+file.getAbsolutePath()+"\n");
           
           CityMap cityMap = new CityMap(file.getAbsolutePath());
       	   cityMap.fillMap();
       	   
       	   c.setCityMap(cityMap);
       	   System.out.println("map loaded");
           
           GraphicalView graphicalView = w.getGraphicalView();
           TextualView textualView = w.getTextualView();
           
           graphicalView.updateGraphicalCityMap(c);
           //window.repaint();
           w.setVisibleRequestButton();
           w.removeLegend();
           textualView.update(c);
           
           c.setCurrentState(c.mapLoadedState);
        		   
        } else {
             System.out.println("L'ouverture est annul�e\n");
        }
		
	}

	

}