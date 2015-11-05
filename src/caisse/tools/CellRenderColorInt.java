package caisse.tools;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import caisse.Model;

public class CellRenderColorInt extends CellRender {

	public CellRenderColorInt(boolean totalLine) {
		super(Integer.class, false, totalLine);
	}

	@Override
	protected Color getColor(JTable table, int row, int col, JLabel l) {
		Color color = Model.WHITE;
		int val = Integer.parseInt(l.getText());
		int cmp = Integer.compare(val, 0);
		
		if (cmp <= 0) {
			color = Model.RED;
		} else {
			color = Model.LIGHT_GRAY;
		}
		return color;
	}

}
