package caisse.sellProcuct;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.Transaction;

public class TableModelSellProductHistoric extends AbstractTableModel {

	protected Model model;
	protected ArrayList<Transaction> historic;
	protected String[] colNames = { "Quantité", "Date", "Client" };
	protected Class<?>[] colClass = { Integer.class, String.class, String.class };
	protected String product;
	protected int day;

	public TableModelSellProductHistoric(Model model, String product, int day) {
		this.model = model;
		reset(product, day);
	}

	public void reset(String product, int day) {
		this.product = product;
		this.day = day;
		historic = new ArrayList<>();
		Calendar calTran = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.DAY_OF_WEEK, -day + 1);
		for (Transaction tran : model.getAllHistoric()) {
			if (tran.getArticleString().contains(product)) {
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
			return historic.get(row).getProdQuantity(product);
		case 1:
			return Model.dateFormatFull.format(historic.get(row).getDate());
		case 2:
			int id = historic.get(row).getClientId();
			StringBuilder sb = new StringBuilder();
			sb.append(model.getUserName(id) + " ");
			sb.append(model.getUserFirstname(id) + " (" + id + ")");
			return sb.toString();
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
