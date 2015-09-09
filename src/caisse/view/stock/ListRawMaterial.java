package caisse.view.stock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import caisse.WriteFile;
import caisse.error.NameAlreadyTakenError;
import caisse.product.RawMaterial;

public class ListRawMaterial extends AbstractTableModel {

	public static String fileName = "Stock";
	
	protected HashMap<String, RawMaterial> list;
	protected String[] colNames = { "Produit", "Stock", "Prix unitaire" };
	protected Class<?>[] colClass = { String.class, Integer.class,
			Integer.class };
	protected Boolean[] colEdit = { false, true, false };
	protected ArrayList<RawMaterial> arrayList;

	public ListRawMaterial() {
		this.list = new HashMap<String, RawMaterial>();
		arrayList = new ArrayList<RawMaterial>();
	}

	public void addRawMaterial(String product) {
		// TODO Gérer correctement les doublons de noms
		// RawMaterial mat = null;
		// mat = list.putIfAbsent(product, new RawMaterial(product));
		list.put(product, new RawMaterial(product));
		setArrayList();
		// if (mat != null) {
		// throw new NameAlreadyTakenError(product);
		// }
	}

	public void addRawMaterial(String product, int quantity, int unitaryPrice) {
		// TODO Gérer correctement les doublons de noms
		// RawMaterial mat = null;
		// mat = list.putIfAbsent(product, new RawMaterial(product));
		list.put(product, new RawMaterial(product, quantity, unitaryPrice));
		setArrayList();
		// if (mat != null) {
		// throw new NameAlreadyTakenError(product);
		// }
	}

	public void removeRawMaterial(String product) {
		list.remove(product);
		setArrayList();
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
		return list.get(product).getUnitaryPrice();
	}

	public RawMaterial getRawMaterial(String product) {
		return list.get(product);
	}

	protected void setArrayList() {
		ArrayList<RawMaterial> arrayList = new ArrayList<RawMaterial>(
				list.values());
		arrayList.sort(new Comparator<RawMaterial>() {
			@Override
			public int compare(RawMaterial o1, RawMaterial o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		this.arrayList = arrayList;
	}
	
	public ArrayList<RawMaterial> getAllMaterials() {
		return arrayList;
	}
	
	public void endRestock() {
		for(RawMaterial mat : arrayList) {
			if(mat.getRestockNum() > 0)
			mat.endRestock();
		}
	}

	public void writeData() {
		WriteFile.writeFile(fileName, this.toString());
	}
	
	public Color getRowColor(int row) {
		return arrayList.get(row).getColor();
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
		switch (columnIndex) {
		case 0:
			return arrayList.get(rowIndex).getName();
		case 1:
			return arrayList.get(rowIndex).getStock();
		case 2:
			return ((double) arrayList.get(rowIndex).getUnitaryPrice() / 100);
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
		switch (columnIndex) {
		case 1:
			arrayList.get(rowIndex).setStock((int) aValue);
			writeData();
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (RawMaterial mat : getAllMaterials()) {
			sb.append(mat.getName());
			sb.append("; ");
			sb.append(mat.getStock());
			sb.append("; ");
			sb.append(mat.getUnitaryPrice());
			sb.append("\n");
		}
		return sb.toString();
	}
}
