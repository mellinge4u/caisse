package caisse.tools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import caisse.Model;
import caisse.historic.TableModelHistoric;
import caisse.stock.TableModelRawMaterial;

public class CellRenderHistoricProduct extends CellRender {

	public CellRenderHistoricProduct() {
		super(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);
		TableModelHistoric tableModel = (TableModelHistoric) table.getModel();
		if (row == tableModel.getRowCount() - 1) {
			return super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, col);
		}
		if (table.isCellSelected(row, col)) {
			l.setBackground(new Color(176, 196, 222)); // LIGHT BLUE
		} else {
			l.setBackground(tableModel.getTransaction(row).getColor());
		}
		return l;
	}

}
