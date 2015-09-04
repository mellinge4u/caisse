package caisse.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import caisse.Historic;
import caisse.Model;
import caisse.product.RawMaterial;
import caisse.product.SoldProduct;

public class CurrentTransaction extends AbstractTableModel {

	protected Model model;
	protected HashMap<SoldProduct, Integer> transaction;
	protected String[] colNames = { "Article", "Prix unitaire", "Quantité",
			"Prix" };
	protected Class<?>[] colClass = { String.class, Double.class,
			Integer.class, Double.class };
	protected Boolean[] colEdit = { false, false, true, false };

	public CurrentTransaction(Model model) {
		this.model = model;
		transaction = new HashMap<SoldProduct, Integer>();
	}

	public void addItem(SoldProduct product, int quantity) {
		transaction.put(product, quantity);
	}

	public void removeItem(SoldProduct product) {
		transaction.remove(product);
	}

	public void clear() {
		transaction.clear();
	}

	public int getCost() {
		int price = 0;
		for (Entry<SoldProduct, Integer> ent : transaction.entrySet()) {
			price += ent.getValue() * ent.getKey().getSalePrice();
		}
		return price;
	}

	public void validTransaction(String client) {
		Historic trans = new Historic(client, getCost(), new Date());
		for (Entry<SoldProduct, Integer> entry : transaction.entrySet()) {
			entry.getKey().sale(entry.getValue());
			trans.addProduct(entry.getKey().getName(), entry.getValue());
		}
		if (trans.getNumberArticle() > 0) {
			model.addHistoric(trans);
		}
		this.clear();
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
		return transaction.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<Entry<SoldProduct, Integer>> list = new ArrayList<Entry<SoldProduct, Integer>>(
				transaction.entrySet());
		switch (columnIndex) {
		case 0:
			return list.get(rowIndex).getKey().getName();
		case 1:
			return (double) list.get(rowIndex).getKey().getSalePrice() / 100;
		case 2:
			return list.get(rowIndex).getValue();
		case 3:
			return (double) list.get(rowIndex).getKey().getSalePrice() / 100
					* list.get(rowIndex).getValue();
		default:
			break;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return colEdit[columnIndex];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ArrayList<Entry<SoldProduct, Integer>> list = new ArrayList<Entry<SoldProduct, Integer>>(
				transaction.entrySet());
		switch (columnIndex) {
		case 2:
			list.get(rowIndex).setValue((int) aValue);
			break;
		default:
			break;
		}
		model.update();
	}

}
