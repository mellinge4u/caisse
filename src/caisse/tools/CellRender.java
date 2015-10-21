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

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);

		Color newCol = getColor(table, row, col, l);
		
		if (table.isCellSelected(row, col)) {
			newCol = bluishColor(newCol);
		}
		updateText(l);
		
		l.setBackground(newCol);
		return l;
	}

	private Color bluishColor(Color color) {
		Color blueOrg = Model.BLUE_ORG;
		int r, g, b;
		r = (int) Math.sqrt((Math.pow((double) blueOrg.getRed(), 2) + Math
				.pow((double) color.getRed(), 2)) / 2);
		g = (int) Math.sqrt((Math.pow((double) blueOrg.getGreen(), 2) + Math
				.pow((double) color.getGreen(), 2)) / 2);
		b = (int) Math.sqrt((Math.pow((double) blueOrg.getBlue(), 2) + Math
				.pow((double) color.getBlue(), 2)) / 2);
		color = new Color(r, g, b);
		return color;
	}
	
	protected Color getColor(JTable table, int row, int col, JLabel l) {
		TableModel tableModel = table.getModel();
		Color newCol = Model.WHITE;

		if (tableModel.isCellEditable(row, col)) {
			newCol = Model.WHITE;
		} else {
			newCol = Model.LIGHT_GRAY;
		}

		if (totalLine && row + 1 == table.getRowCount()) {
			newCol = Model.GRAY;
		}
		return newCol;
	}

	protected void updateText(JLabel l) {
	}
	
}
