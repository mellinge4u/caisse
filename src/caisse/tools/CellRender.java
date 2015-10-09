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
	private boolean colorPrice;

	public CellRender() {
		super();
		this.totalLine = false;
		this.colorPrice = false;
	}

	public CellRender(boolean totalLine) {
		super();
		this.totalLine = totalLine;
		this.colorPrice = false;
	}

	public CellRender(boolean totalLine, boolean colorPrice) {
		super();
		this.totalLine = totalLine;
		this.colorPrice = colorPrice;
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

		try {
			if (tableModel.getColumnClass(col) == Double.class) {
				double val = Double.parseDouble(l.getText());
				l.setText(Model.doubleFormatMoney.format(val) + " €");
				if (colorPrice) {
					int cmp = Double.compare(val, 0.0);
					if (cmp == 0) {
						l.setBackground(new Color(224, 224, 224)); // LIGHT GRAY
					} else if (cmp < 0) {
						l.setBackground(new Color(255, 112, 112)); // RED
					} else {
						l.setBackground(new Color(128, 255, 0)); // GREEN
					}
				}
			}
		} catch (Exception e) {
		}
		if (table.isCellSelected(row, col)) {
			l.setBackground(new Color(176, 196, 222)); // LIGHT BLUE
		}
		return l;
	}

}
