package caisse.tools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import caisse.Model;
import caisse.stock.TableModelRawMaterial;

public class CellRender extends DefaultTableCellRenderer {

	private boolean totalLine;

	public CellRender(Boolean totalLine) {
		super();
		this.totalLine = totalLine;
	}

	public CellRender() {
		super();
		this.totalLine = false;
	}

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
		if (totalLine && row + 1 == table.getRowCount()) {
			l.setBackground(new Color(180, 180, 180)); // GRAY
		}
		
		if (table.isCellSelected(row, col)) {
			l.setBackground(new Color(176, 196, 222)); // LIGHT BLUE
		}
		try {
			if (tableModel.getColumnClass(col) == Double.class) {
				double val = Double.parseDouble(l.getText());
				l.setText(Model.doubleFormatMoney.format(val) + " €");
			}
		} catch (Exception e) {
		}
		return l;
	}

}
