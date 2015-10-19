package caisse.sellProcuct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.IHistoricTableModel;
import caisse.historic.Transaction;

public class TableModelSellProductHistoric extends AbstractTableModel implements
		IHistoricTableModel {

	protected ArrayList<Transaction> displayList;
	protected String[] colNames = { "Client", "Quantité", "Date" };
	protected Class<?>[] colClass = { String.class, Integer.class, String.class };
	protected String product;
	protected int day;
	private Date startDate;
	protected int dayDisplay;

	public TableModelSellProductHistoric(String product) {
		this.product = product;
		startDate = new Date();
		dayDisplay = 1;
		displayList = new ArrayList<>();
		updateDisplayList();
	}

	public Transaction getTransaction(int row) {
		return displayList.get(displayList.size() - row - 1);
	}

	public int getQuantity() {
		int q = 0;
		for (Transaction tran : displayList) {
			q += tran.getProdQuantity(product);
		}
		return q;
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
			calTran.setTime(tran.getDate());
			if (calStart.before(calTran) && calEnd.after(calTran)) {
				if (tran.isContaining(product)) {
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
		return displayList.size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int row = displayList.size() - rowIndex - 1;
		if (rowIndex == displayList.size()) {
			switch (columnIndex) {
			case 0:
				return "Total : ";
			case 1:
				return getQuantity();
			default:
				break;
			}
		} else {
			switch (columnIndex) {
			case 0:
				int id = displayList.get(row).getClientId();
				Model model = Model.getInstance();
				StringBuilder sb = new StringBuilder();
				sb.append(model.getUserName(id) + " ");
				sb.append(model.getUserFirstname(id) + " (" + id + ")");
				return sb.toString();
			case 1:
				return displayList.get(row).getProdQuantity(product);
			case 2:
				return Model.dateFormatFull.format(displayList.get(row)
						.getDate());
			default:
				break;
			}
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
