package caisse.historic;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.file.WriteFile;

public class TableModelHistoric extends AbstractTableModel {

	public static String fileName = "Historique";

	protected Model model;
	protected ArrayList<Transaction> list;
	protected String[] colNames = { "ID Client", "Client", "Articles", "Prix", "Date", "Paiment Espece" };
	protected Class<?>[] colClass = { Integer.class, String.class, String.class, Double.class,
			Date.class, Double.class };

	public TableModelHistoric(Model model) {
		this.model = model;
		list = new ArrayList<Transaction>();
	}

	public void addHistoric(Transaction transaction) {
		list.add(transaction);
	}

	public void addReadHistoric(Transaction transaction) {
		list.add(transaction);
	}

	public ArrayList<Transaction> getAllTransaction() {
		return list;
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
		int selected = list.size() - rowIndex - 1;
		switch (columnIndex) {
		case 0:
			return list.get(selected).getClientId();
		case 1:
			int id = list.get(selected).getClientId();
			StringBuilder sb = new StringBuilder();
			sb.append(model.getUserName(id) + " ");
			sb.append(model.getUserFirstname(id));
			return sb.toString();
		case 2:
			return list.get(selected).getArticleString();
		case 3:
			return ((double) list.get(selected).getPrice()) / 100;
		case 4:
			return Model.dateFormatFull.format(list.get(selected).getDate());
		case 5:
			return ((double) list.get(selected).getCashAdd()) / 100;
		default:
			break;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Transaction tran : list) {
			sb.append(tran.toString());
		}
		return sb.toString();
	}

}
