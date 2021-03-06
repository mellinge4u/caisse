package caisse.sellProcuct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.historic.IHistoricTableModel;
import caisse.historic.Transaction;
import caisse.tools.CellRender;
import caisse.tools.TableModel;

public class TableModelSellProductHistoric extends TableModel implements
		IHistoricTableModel {

	protected ArrayList<Transaction> displayList;
	protected String product;
	protected int day;
	private Date startDate;
	protected int dayDisplay;

	public TableModelSellProductHistoric(String product) {
		super.colNames = new String[] { "Client", "Quantit�", "Date" };
		super.colClass = new Class<?>[] { String.class, Integer.class, String.class };
		super.colEdit = new Boolean[] {false, false, false};
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
	public CellRender getColumnModel(int col) {
		return new CellRender(colClass[col], colEdit[col], true);
	}
}
