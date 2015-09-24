package caisse.user;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.Transaction;

public class TableModelUserHistoric extends AbstractTableModel {

	protected Model model;
	protected ArrayList<Transaction> historic;
	protected String[] colNames = { "Articles", "Prix", "Date", "Débit compte" };
	protected Class<?>[] colClass = { String.class, Double.class, String.class,
			Double.class };
	protected int id;
	protected int day;

	public TableModelUserHistoric(Model model, int id, int day) {
		this.model = model;
		reset(id, day);
	}

	public void reset(int id, int day) {
		this.id = id;
		this.day = day;
		historic = new ArrayList<>();
		Calendar calTran = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.DAY_OF_WEEK, -day + 1);
		for (Transaction tran : model.getAllHistoric()) {
			if (tran.getClientId() == id) {
				calTran.setTime(tran.getDate());
				if (cal.before(calTran)) {
					historic.add(tran);
				}

			}
		}
	}

	public Transaction getTransaction(int row) {
		return historic.get(historic.size() - row - 1);
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
		int row = historic.size() - rowIndex - 1;
		switch (columnIndex) {
		case 0:
			return historic.get(row).getArticleString();
		case 1:
			return ((double) historic.get(row).getPrice()) / 100;
		case 2:
			return Model.dateFormatFull.format(historic.get(row).getDate());
		case 3:
			return ((double) (historic.get(row).getPrice() - historic.get(row)
					.getCashAdd())) / 100;
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
