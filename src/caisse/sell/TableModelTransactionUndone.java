package caisse.sell;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.Transaction;
import caisse.tools.TableModel;

public class TableModelTransactionUndone extends TableModel {

	public static String fileName = "Historique";

	protected ArrayList<Transaction> list;

	public TableModelTransactionUndone() {
		super.colNames = new String[] { "Client", "Articles", "Prix"};
		super.colClass = new Class<?>[] { String.class,
				String.class, Double.class};
		super.colEdit = new Boolean[] {false, false, false};
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
}
