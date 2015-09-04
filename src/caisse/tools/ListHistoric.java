package caisse.tools;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Historic;
import caisse.Model;

public class ListHistoric extends AbstractTableModel {

	public static String fileName = "Historique";

	protected Model model;
	protected ArrayList<Historic> list;
	protected String[] colNames = { "Client", "Articles", "Prix", "Date" };
	protected Class<?>[] colClass = { String.class, String.class, Double.class,
			Date.class };
	protected Boolean[] colEdit = { false, false, false, false };

	public ListHistoric(Model model) {
		this.model = model;
		list = new ArrayList<Historic>();
	}

	public void addHistoric(Historic transaction) {
		list.add(transaction);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return colClass[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return list.get(rowIndex).getClient();
		case 1:
			return list.get(rowIndex).getArticleString();
		case 2:
			return ((double) list.get(rowIndex).getPrice()) / 100;
		case 3:
			return list.get(rowIndex).getDate();
		default:
			break;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return colEdit[columnIndex];
	}

}
