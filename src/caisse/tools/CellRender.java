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
	private boolean colorInt;

	public CellRender() {
		super();
		this.totalLine = false;
		this.colorPrice = false;
	}

	public CellRender(boolean totalLine) {
		super();
		this.totalLine = totalLine;
		this.colorPrice = false;
		this.colorInt = false;
	}

	public CellRender(boolean totalLine, boolean colorPrice) {
		super();
		this.totalLine = totalLine;
		this.colorPrice = colorPrice;
		this.colorInt = false;
	}

	public CellRender(boolean totalLine, boolean colorPrice, boolean colorInt) {
		super();
		this.totalLine = totalLine;
		this.colorPrice = colorPrice;
		this.colorInt = colorInt;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);

		Color newCol = getColor(table, row, col, l);
		
		if (table.isCellSelected(row, col)) {
			Color blueOrg = Model.BLUE_ORG;
			int r, g, b;
			r = (int) Math.sqrt((Math.pow((double) blueOrg.getRed(), 2) + Math
					.pow((double) newCol.getRed(), 2)) / 2);
			g = (int) Math.sqrt((Math.pow((double) blueOrg.getGreen(), 2) + Math
					.pow((double) newCol.getGreen(), 2)) / 2);
			b = (int) Math.sqrt((Math.pow((double) blueOrg.getBlue(), 2) + Math
					.pow((double) newCol.getBlue(), 2)) / 2);
			newCol = new Color(r, g, b);
		}

		l.setBackground(newCol);
		return l;
	}

	protected Color getColor(JTable table, int row, int col, JLabel l) {
		TableModel tableModel = table.getModel();
		Color newCol = Model.WHITE;

		if (tableModel.isCellEditable(row, col)) {
			newCol = Model.WHITE;
		} else {
			newCol = Model.LIGHT_GRAY;
		}

		// TODO Change this
		if (colorInt) {
		try {
			if (tableModel.getColumnClass(col) == Integer.class) {
				int val = Integer.parseInt(l.getText());
					int cmp = Integer.compare(val, 0);
					if (cmp <= 0) {
						newCol = Model.RED;
					} else {
						newCol = Model.LIGHT_GRAY;
					}
				}
			} catch (Exception e) {
			}
		}

		// TODO Change this
		if (colorPrice) {
		try {
			if (tableModel.getColumnClass(col) == Double.class) {
				double val = Double.parseDouble(l.getText());
				l.setText(Model.doubleFormatMoney.format(val) + " €");
					int cmp = Double.compare(val, 0.0);
					if (cmp == 0) {
						newCol = Model.LIGHT_GRAY;
					} else if (cmp < 0) {
						newCol = Model.RED;
					} else {
						newCol = Model.GREEN;
					}
				}
			} catch (Exception e) {
			}
		}

		if (totalLine && row + 1 == table.getRowCount()) {
			newCol = Model.GRAY;
		}
		return newCol;
	}
	
}
