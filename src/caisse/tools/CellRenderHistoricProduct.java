package caisse.tools;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTable;
import caisse.Model;
import caisse.historic.TableModelHistoric;

public class CellRenderHistoricProduct extends CellRender {

	public CellRenderHistoricProduct() {
		super(String.class, false, true);
	}

	@Override
	protected Color getColor(JTable table, int row, int col, JLabel l) {
		TableModelHistoric tableModel = (TableModelHistoric) table.getModel();
		if (row == tableModel.getRowCount() - 1) {
			return Model.GRAY;
		} else {
			return tableModel.getTransaction(row).getColor();
		}
	}

}
