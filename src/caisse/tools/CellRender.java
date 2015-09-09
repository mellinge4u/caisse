package caisse.tools;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import caisse.historic.Transaction;
import caisse.stock.TableModelRawMaterial;

public class CellRender extends DefaultTableCellRenderer {

	protected DecimalFormat df = new DecimalFormat("#0.00");

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);
		TableModel tableModel = table.getModel();
		if (tableModel.isCellEditable(row, col)) {
			l.setBackground(Color.WHITE);
		} else {
			l.setBackground(new Color(224, 224, 224)); // LIGHT GRAY
		}
		if (tableModel.getColumnName(col).equals("Stock")) {
			TableModelRawMaterial rm = (TableModelRawMaterial) tableModel;
			l.setBackground(rm.getRowColor(row));
		}
		if (table.isCellSelected(row, col)) {
			l.setBackground(new Color(176, 196, 222)); // LIGHT BLUE
		}
		try {
			if (tableModel.getColumnClass(col) == Double.class) {
				double val = Double.parseDouble(l.getText());
				l.setText(df.format(val) + " €");
			}
		} catch (Exception e) {
		}
		return l;
	}

}
