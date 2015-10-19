package caisse.historic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import com.sun.org.apache.bcel.internal.generic.CALOAD;

import caisse.Model;

public class TableModelHistoric extends AbstractTableModel implements
		IHistoricTableModel {

	public static String fileName = "Historique";

	protected Model model;
	protected ArrayList<Transaction> list;
	protected ArrayList<Transaction> displayList;
	protected String[] colNames = { "ID Client", "Client", "Articles", "Prix",
			"Date", "Paiment Espece" };
	protected Class<?>[] colClass = { Integer.class, String.class,
			String.class, Double.class, Date.class, Double.class };
	private Date startDate;
	protected int dayDisplay;

	public TableModelHistoric(Model model) {
		this.model = model;
		list = new ArrayList<Transaction>();
		displayList = new ArrayList<Transaction>();
		startDate = new Date();
		dayDisplay = 1;
	}

	public void addHistoric(Transaction transaction) {
		list.add(transaction);
		updateDisplayList();
	}

	public void addReadHistoric(Transaction transaction) {
		list.add(transaction);
		Calendar calTran = Calendar.getInstance();
		calTran.setTime(transaction.getDate());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.DAY_OF_WEEK, -dayDisplay + 1);
		updateDisplayList();
	}

	public void setDisplay(int watchingDays, Date start) {
		this.startDate = start;
		this.dayDisplay = watchingDays;
		updateDisplayList();
		this.fireTableDataChanged();
	}

	public Transaction getDisplayTransaction(int row) {
		return displayList.get(displayList.size() - row - 1);
	}

	public ArrayList<Transaction> getAllTransaction() {
		return list;
	}

	public int getTotalDisplayPrice() {
		int price = 0;
		for (Transaction tran : displayList) {
			price += tran.getPrice();
		}
		return price;
	}

	public int getTotalDisplayPayment() {
		int payment = 0;
		for (Transaction tran : displayList) {
			payment += tran.getCashAdd();
		}
		return payment;
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
		for (Transaction tran : list) {
			calTran.setTime(tran.getDate());
			if (calStart.before(calTran) && calEnd.after(calTran)) {
				displayList.add(tran);
			}
		}
	}

	public Transaction getTransaction(int row) {
		if (row >= displayList.size()) {
			return null;
		} else {
			return displayList.get(displayList.size() - row - 1);
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
		return displayList.size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int selected = displayList.size() - rowIndex - 1;

		if (rowIndex == displayList.size()) {
			switch (columnIndex) {
			case 0:
				return "Total : ";
			case 3:
				return (double) getTotalDisplayPrice() / 100;
			case 5:
				return (double) getTotalDisplayPayment() / 100;
			default:
			}
		} else {
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
				return Model.dateFormatFull.format(displayList.get(selected)
						.getDate());
			case 5:
				return ((double) displayList.get(selected).getCashAdd()) / 100;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Transaction tran : list) {
			sb.append(tran.toString());
		}
		return sb.toString();
	}

}
