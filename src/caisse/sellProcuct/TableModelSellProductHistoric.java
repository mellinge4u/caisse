package caisse.sellProcuct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.IHistoricTableModel;
import caisse.historic.Transaction;

public class TableModelSellProductHistoric extends AbstractTableModel implements IHistoricTableModel {

	protected ArrayList<Transaction> displayList;
	protected String[] colNames = { "Quantité", "Date", "Client" };
	protected Class<?>[] colClass = { Integer.class, String.class, String.class };
	protected String product;
	protected int day;
	private Date startDate;
	protected int dayDisplay;

	public TableModelSellProductHistoric(String product, int day) {
		this.product = product;
		startDate = new Date();
		dayDisplay = 1;
		displayList = new ArrayList<>();
		updateDisplayList();
	}

	public Transaction getTransaction(int row) {
		return displayList.get(displayList.size() - row - 1);
	}

	public void updateDisplayList() {
		displayList.clear();
		Calendar calTran = Calendar.getInstance();
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calStart.setTime(startDate);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.setTime(startDate);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.add(Calendar.DAY_OF_WEEK, +dayDisplay);
		for (Transaction tran : Model.getInstance().getAllHistoric()) {
			if (tran.getArticleString().contains(product)) {
				calTran.setTime(tran.getDate());
				if (calStart.before(calTran) && calEnd.after(calTran)) {
					displayList.add(tran);
				}
			}
		}
	}

	@Override
	public void setDisplay(int watchingDays, Date start) {
		this.startDate = start;
		this.dayDisplay = watchingDays;
		updateDisplayList();
		this.fireTableDataChanged();
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
		return displayList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int row = displayList.size() - rowIndex - 1;
		switch (columnIndex) {
		case 0:
			return displayList.get(row).getProdQuantity(product);
		case 1:
			return Model.dateFormatFull.format(displayList.get(row).getDate());
		case 2:
			int id = displayList.get(row).getClientId();
			Model model = Model.getInstance();
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
