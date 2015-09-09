package caisse.tools;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import caisse.view.stock.ListRawMaterial;

public class StockColumnCellRender extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);
		ListRawMaterial tableModel = (ListRawMaterial) table.getModel();
		l.setBackground(tableModel.getRowColor(row));
		return l;
	}

}
