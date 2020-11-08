package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HighlightCellRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int[] rows;
	
	/**
	 * Def rows to highlight
	 * @param row
	 */	
	public HighlightCellRenderer(int[] rows) {
		super();
		this.rows=rows;
	}
	
	
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
		
		// you may want to address isSelected here too
		//c.setForeground(/*special foreground color*/);
			c.setBackground(Color.lightGray);
		}else {
			c.setBackground(Color.white);
		}
		return c;
		}
}
