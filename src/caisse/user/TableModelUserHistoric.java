package caisse.user;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.Transaction;

public class TableModelUserHistoric extends AbstractTableModel {

	protected Model model;
	protected ArrayList<Transaction> historic;
	protected String[] colNames = { "Articles", "Prix", "Date", "Débit compte" };
	protected Class<?>[] colClass = { String.class, Double.class, String.class, Double.class };
	protected int id;
	
	public TableModelUserHistoric(Model model, int id) {
		this.model = model;
		reset(id);
	}
	
	public void reset(int id) {
		this.id = id;
		historic = new ArrayList<>();
		for (Transaction tran : model.getAllHistoric()) {
			if (tran.getClientId() == id) {
				historic.add(tran);
			}
		}
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
		return historic.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return historic.get(rowIndex).getArticleString();
		case 1:
			return ((double) historic.get(rowIndex).getPrice()) / 100;
		case 2:
			return Transaction.df.format(historic.get(rowIndex).getDate());
		case 3:
			return ((double) (historic.get(rowIndex).getPrice() - historic.get(rowIndex).getCashAdd())) / 100;
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
