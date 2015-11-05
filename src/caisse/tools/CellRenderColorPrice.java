package caisse.tools;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import caisse.Model;

public class CellRenderColorPrice extends CellRender {

	private int mult;
	
	public CellRenderColorPrice(boolean editable, boolean totalLine, boolean colorReverse) {
		super(Double.class, editable, totalLine);
		if (colorReverse) {
			mult = -1;
		} else {
			mult = 1;
		}
	}

	@Override
	protected Color getColor(JTable table, int row, int col, JLabel l) {
		Color start = super.getColor(table, row, col, l);
		Color color = start;
		double val = Double.parseDouble(l.getText());
		int cmp = Double.compare(val, 0.0) * mult;
		
		if (cmp == 0) {
			color = start;
		} else if (cmp < 0) {
			color = Model.RED;
		} else {
			color = Model.GREEN;
		}
		return color;
	}
}
