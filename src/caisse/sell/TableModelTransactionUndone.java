package caisse.sell;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import com.sun.org.apache.bcel.internal.generic.CALOAD;

import caisse.Model;
import caisse.historic.IHistoricTableModel;
import caisse.historic.Transaction;

public class TableModelTransactionUndone extends AbstractTableModel {

	public static String fileName = "Historique";

	protected ArrayList<Transaction> list;
	protected String[] colNames = { "Client", "Articles", "Prix"};
	protected Class<?>[] colClass = { String.class,
			String.class, Double.class};

	public TableModelTransactionUndone() {
		list = new ArrayList<Transaction>();
	}

	public void addTransaction(Transaction transaction) {
		list.add(transaction);
	}

	public void removeTransaction(int row) {
		list.remove(getTransaction(row));
	}

	public Transaction getTransaction(int row) {
		return list.get(row);
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

			switch (columnIndex) {
			case 0:
				Model model = Model.getInstance();
				int id = list.get(rowIndex).getClientId();
				StringBuilder sb = new StringBuilder();
				sb.append(model.getUserName(id) + " ");
				sb.append(model.getUserFirstname(id));
				return sb.toString();
			case 1:
				return list.get(rowIndex).getArticleString();
			case 2:
				return ((double) list.get(rowIndex).getPrice()) / 100;
			default:
				break;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
