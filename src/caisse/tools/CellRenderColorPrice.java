package caisse.tools;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import caisse.Model;

public class CellRenderColorPrice extends CellRender {

	public CellRenderColorPrice(boolean editable, boolean totalLine) {
		super(Double.class, editable, totalLine);
	}

	@Override
	protected Color getColor(JTable table, int row, int col, JLabel l) {
		Color color = Model.WHITE;
		double val = Double.parseDouble(l.getText());
		int cmp = Double.compare(val, 0.0);
		
		if (cmp == 0) {
			color = Model.LIGHT_GRAY;
		} else if (cmp < 0) {
			color = Model.RED;
		} else {
			color = Model.GREEN;
		}
		return color;
	}
}
