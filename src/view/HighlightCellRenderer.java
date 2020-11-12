package view;

import java.awt.Color;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * Customed Cell Renderer for the JTable in the TextualView. 
 * To define the modifications of the table if a cell is clicked. 
 * In our application we just change the background color for selected rows. 
 * 
 * @authors H4112
 *
 */
public class HighlightCellRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The rows of the JTable. 
	 */
	private int[] rows;
	
	/**
	 * Def rows to highlight
	 * @param row
	 */	
	public HighlightCellRenderer(int[] rows) {
		super();
		this.rows=rows;
	}
	
	/**
	 * Get the modified table. 
	 */
	public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
		Component c = 
		super.getTableCellRendererComponent(table, value,
		     isSelected, hasFocus,
		     row, column);
		
		if (isSelected) {
            // cell (and perhaps other cells) are selected
			this.setForeground(Color.black);
        }
		
		// Only for specific cell
		if (row == rows[0] || row== rows[1] && column == 0) {
		
			c.setBackground(Color.lightGray);
		}else {
			c.setBackground(Color.white);
		}
		return c;
		}
}
