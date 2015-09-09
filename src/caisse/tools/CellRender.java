package caisse.tools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import caisse.stock.TableModelRawMaterial;

public class CellRender extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);
		TableModel tableModel = table.getModel();
		if (tableModel.isCellEditable(row, col)) {
			l.setForeground(Color.BLACK);
			l.setBackground(Color.WHITE);
		} else {
			l.setForeground(new Color(150, 150, 150));
			l.setBackground(new Color(224, 224, 224)); // LIGHT GRAY
		}
		if (tableModel.getColumnName(col).equals("Stock")) {
			TableModelRawMaterial rm = (TableModelRawMaterial) tableModel;
			l.setBackground(rm.getRowColor(row));
		}
		if (table.isCellSelected(row, col)) {
			l.setBackground(new Color(176, 196, 222)); // LIGHT BLUE
		}
		return l;
	}

}
