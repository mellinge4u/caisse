package caisse.tools;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import caisse.error.NameAlreadyTakenError;
import caisse.product.RawMaterial;

public class ListRawMaterial extends AbstractTableModel {

	protected HashMap<String, RawMaterial> list;
	protected String[] colNames = { "Produit", "Stock", "Prix unitaire" };
	protected Class<?>[] colClass = { String.class, Integer.class,
			Integer.class };
	protected Boolean[] colEdit = { false, true, false };

	public ListRawMaterial() {
		this.list = new HashMap<String, RawMaterial>();
	}

	public void addRawMaterial(String product) {
		// TODO Gérer correctement les doublons de noms
		// RawMaterial mat = null;
		// mat = list.putIfAbsent(product, new RawMaterial(product));
		list.put(product, new RawMaterial(product));
		// if (mat != null) {
		// throw new NameAlreadyTakenError(product);
		// }
	}

	public void addRawMaterial(String product, int quantity) {
		// TODO Gérer correctement les doublons de noms
		// RawMaterial mat = null;
		// mat = list.putIfAbsent(product, new RawMaterial(product));
		list.put(product, new RawMaterial(product, quantity));
		// if (mat != null) {
		// throw new NameAlreadyTakenError(product);
		// }
	}

	public void removeRawMaterial(String product) {
		list.remove(product);
	}

	public int getStock(String product) {
		return list.get(product).getStock();
	}

	public void addStock(String product, int number) {
		list.get(product).addStock(number);
	}

	public void subStock(String product, int number) {
		list.get(product).subStock(number);
	}

	public double getAvreageCost(String product) {
		return list.get(product).getAverageCost();
	}

	public RawMaterial getRawMaterial(String product) {
		return list.get(product);
	}

	public ArrayList<RawMaterial> getAllMaterials() {
		ArrayList<RawMaterial> arrayList = new ArrayList<RawMaterial>(
				list.values());
		return arrayList;
	}
	
	public void endRestock() {
		for(RawMaterial mat : getAllMaterials()) {
			if(mat.getRestockNum() > 0)
			mat.endRestock();
		}
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
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<RawMaterial> array = new ArrayList<RawMaterial>(list.values());
		switch (columnIndex) {
		case 0:
			return array.get(rowIndex).getName();
		case 1:
			return array.get(rowIndex).getStock();
		case 2:
			return ((double) array.get(rowIndex).getAverageCost() / 100);
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
		ArrayList<RawMaterial> array = new ArrayList<RawMaterial>(list.values());
		switch (columnIndex) {
		case 1:
			array.get(rowIndex).setStock((int) aValue);
			break;
		default:
			break;
		}
	}
}
