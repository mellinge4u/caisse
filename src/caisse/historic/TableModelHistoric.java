package caisse.historic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import com.sun.org.apache.bcel.internal.generic.CALOAD;

import caisse.Model;

public class TableModelHistoric extends AbstractTableModel {

	public static String fileName = "Historique";

	protected Model model;
	protected ArrayList<Transaction> list;
	protected ArrayList<Transaction> displayList;
	protected String[] colNames = { "ID Client", "Client", "Articles", "Prix", "Date", "Paiment Espece" };
	protected Class<?>[] colClass = { Integer.class, String.class, String.class, Double.class, Date.class,
			Double.class };
	protected int watchingDays;

	public TableModelHistoric(Model model) {
		this.model = model;
		list = new ArrayList<Transaction>();
		displayList = new ArrayList<Transaction>();
		watchingDays = 1;
	}

	public void addHistoric(Transaction transaction) {
		list.add(transaction);
		displayList.add(transaction);
	}

	public void addReadHistoric(Transaction transaction) {
		list.add(transaction);
		Calendar calTran = Calendar.getInstance();
		calTran.setTime(transaction.getDate());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.DAY_OF_WEEK, -watchingDays + 1);
		if (cal.before(calTran)) {
			displayList.add(transaction);
		}
	}

	public int getWatchingDays() {
		return watchingDays;
	}

	public void setWatchingDays(int watchingDays) {
		this.watchingDays = watchingDays;
	}

	public Transaction getTransaction(int row) {
		return list.get(list.size() - row - 1);
	}

	public ArrayList<Transaction> getAllTransaction() {
		return list;
	}

	public void updateDisplayList() {
		displayList.clear();
		Calendar calTran = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.DAY_OF_WEEK, -watchingDays + 1);
		for (Transaction tran : list) {
			calTran.setTime(tran.getDate());
			if (cal.before(calTran)) {
				displayList.add(tran);
			}
		}
	}

	public int getTotalTransaction() {
		int total = 0;
		for (Transaction tran : displayList) {
			total += tran.getCashAdd();
		}
		return total;
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
		int selected = displayList.size() - rowIndex - 1;
		switch (columnIndex) {
		case 0:
			return displayList.get(selected).getClientId();
		case 1:
			int id = displayList.get(selected).getClientId();
			StringBuilder sb = new StringBuilder();
			sb.append(model.getUserName(id) + " ");
			sb.append(model.getUserFirstname(id));
			return sb.toString();
		case 2:
			return displayList.get(selected).getArticleString();
		case 3:
			return ((double) displayList.get(selected).getPrice()) / 100;
		case 4:
			return Model.dateFormatFull.format(displayList.get(selected).getDate());
		case 5:
			return ((double) displayList.get(selected).getCashAdd()) / 100;
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
