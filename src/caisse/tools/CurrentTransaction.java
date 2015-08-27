package caisse.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import caisse.product.RawMaterial;
import caisse.product.SoldProduct;

public class CurrentTransaction extends AbstractTableModel {

	protected HashMap<SoldProduct, Integer> transaction;
	protected String[] colNames = { "Article", "Prix", "Quantité" };
	protected Class<?>[] colClass = { String.class, Double.class,
			Integer.class };
	protected Boolean[] colEdit = { false, false, true };
	
	public CurrentTransaction() {
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
	
	public void validTransaction() {
		// TODO A verifier
		for(Entry<SoldProduct, Integer> entry : transaction.entrySet()) {
			entry.getKey().sale(entry.getValue());
		}
		// TODO Ajouter le fin de la transaction pout le calcul du prix unitaire des matieres premieres
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
		ArrayList<Entry<SoldProduct, Integer>> list = new ArrayList<Entry<SoldProduct, Integer>>(transaction.entrySet());
		switch (columnIndex) {
		case 0:
			return list.get(rowIndex).getKey().getName();
		case 1:
			return list.get(rowIndex).getKey().getSalePrice();
		case 2:
			return list.get(rowIndex).getValue();
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
		ArrayList<Entry<SoldProduct, Integer>> list = new ArrayList<Entry<SoldProduct, Integer>>(transaction.entrySet());
		switch (columnIndex) {
		case 2:
			list.get(rowIndex).setValue((int) aValue);
			break;
		default:
			break;
		}
	}
}
