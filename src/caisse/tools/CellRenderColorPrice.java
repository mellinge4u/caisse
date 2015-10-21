package caisse.tools;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import caisse.Model;

public class CellRenderColorPrice extends CellRender {

	public CellRenderColorPrice(boolean totalLine) {
		super(totalLine);
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

	@Override
	protected void updateText(JLabel l) {
		double val = Double.parseDouble(l.getText());
		l.setText(Model.doubleFormatMoney.format(val) + " €");
	}

}
