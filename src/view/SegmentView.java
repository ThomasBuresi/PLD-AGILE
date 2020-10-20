package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JPanel;

/**
 * 
 */
public class SegmentView {

	
    /**
     * Default constructor
     */
    public SegmentView() {
    	
    	
    }

    public void paintComponent(Graphics g) 
    {     
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(50, 70, 300, 350);
        g2.draw(lin);
        g2.drawLine(40, 50, 300, 300);
    }

}