package view;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyCellRenderer extends DefaultTableCellRenderer {
	

    public Component getTableCellRendererComponent (JTable table, Object value,
           boolean isSelected, boolean hasFocus,
           int row, int column)
    {
       Component c = super.getTableCellRendererComponent (table, value,
                isSelected, hasFocus, row, column);
       int height = c.getPreferredSize().height;
       if (height > table.getRowHeight (row))
       {
           table.setRowHeight (row, height);
       }
       return c;
    }
}
