package caisse.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.IHistoricTableModel;
import caisse.historic.Transaction;

public class TableModelUserHistoric extends AbstractTableModel implements IHistoricTableModel {

	private ArrayList<Transaction> displayList;
	private String[] colNames = { "Articles", "Prix", "Date", "Débit compte" };
	private Class<?>[] colClass = { String.class, Double.class, String.class, Double.class };
	private int id;
	private Date startDate;
	private int dayDisplay;

	public TableModelUserHistoric(int id) {
		this.id = id;
		startDate = new Date();
		dayDisplay = 1;
		displayList = new ArrayList<>();
	}

	public void setDisplay(int watchingDays, Date start) {
		this.startDate = start;
		this.dayDisplay = watchingDays;
		updateDisplayList();
		this.fireTableDataChanged();
	}

	public void setId(int id) {
		this.id = id;
		updateDisplayList();
		this.fireTableDataChanged();
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
			if (tran.getClientId() == id) {
				calTran.setTime(tran.getDate());
				if (calStart.before(calTran) && calEnd.after(calTran)) {
					displayList.add(tran);
				}
			}
		}
	}

	public Transaction getTransaction(int row) {
		return displayList.get(displayList.size() - row - 1);
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
			return displayList.get(row).getArticleString();
		case 1:
			return ((double) displayList.get(row).getPrice()) / 100;
		case 2:
			return Model.dateFormatFull.format(displayList.get(row).getDate());
		case 3:
			return ((double) (displayList.get(row).getPrice() - displayList.get(row).getCashAdd())) / 100;
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
