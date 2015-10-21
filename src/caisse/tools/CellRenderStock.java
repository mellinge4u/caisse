package caisse.tools;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import caisse.stock.TableModelRawMaterial;

public class CellRenderStock extends CellRender {

	public CellRenderStock() {
		super(true);
	}

	@Override
	protected Color getColor(JTable table, int row, int col, JLabel l) {
		TableModelRawMaterial tableModel = (TableModelRawMaterial) table.getModel();
		return tableModel.getRowColor(row);
	}

}
