package caisse.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.IHistoricTableModel;
import caisse.historic.Transaction;
import caisse.tools.CellRender;
import caisse.tools.CellRenderColorPrice;
import caisse.tools.TableModel;

public class TableModelUserHistoric extends TableModel implements
		IHistoricTableModel {

	private ArrayList<Transaction> displayList;
	private int id;
	private Date startDate;
	private int dayDisplay;

	public TableModelUserHistoric(int id) {
		super.colNames = new String[] { "Articles", "Prix", "Date",
				"Débit compte" };
		super.colClass = new Class<?>[] { String.class, Double.class,
				String.class, Double.class };
		super.colEdit = new Boolean[] { false, false, false, false };
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

	public int getTotalDisplayPrice() {
		int price = 0;
		for (Transaction tran : displayList) {
			price += tran.getPrice();
		}
		return price;
	}

	public int getTotalDisplayDebit() {
		int payment = 0;
		for (Transaction tran : displayList) {
			payment += tran.getCashAdd();
		}
		return getTotalDisplayPrice() - payment;
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
				return ((double) getTotalDisplayPrice()) / 100;
			case 3:
				return ((double) getTotalDisplayDebit()) / 100;
			default:
				break;
			}
		} else {
			switch (columnIndex) {
			case 0:
				return displayList.get(row).getArticleString();
			case 1:
				return ((double) displayList.get(row).getPrice()) / 100;
			case 2:
				return Model.dateFormatFull.format(displayList.get(row)
						.getDate());
			case 3:
				return ((double) (displayList.get(row).getPrice() - displayList
						.get(row).getCashAdd())) / 100;
			default:
				break;
			}
		}
		return null;
	}

	@Override
	public CellRender getColumnModel(int col) {
		if (col == 3) {
			return new CellRenderColorPrice(false, true, true);
		}
		return new CellRender(colClass[col], colEdit[col], true);
	}

}
