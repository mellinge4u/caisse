package caisse.tools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class NonEditableSellProdCellRender extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);
		ListSoldProd tableModel = (ListSoldProd) table.getModel();
		if (tableModel.isCellEditable(row, col)) {
			l.setBackground(Color.WHITE);
		} else {
			l.setBackground(Color.LIGHT_GRAY);
		}
		return l;
	}

}
