package caisse.stock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

public class TableModelListRawMaterial extends AbstractTableModel {

	private HashMap<RawMaterial, Integer> list;
	private ArrayList<RawMaterial> arrayList;
	private String[] colNames = { "Produit", "Quantite" };
	private Class<?>[] colClass = { String.class, Integer.class };
	private Boolean[] colEdit = { false, true };

	public TableModelListRawMaterial() {
		this.list = new HashMap<RawMaterial, Integer>();
		arrayList = new ArrayList<RawMaterial>();
		setArrayList();
	}

	public void addMaterial(RawMaterial material, int quantity) {
		list.put(material, quantity);
		setArrayList();
	}

	public void removeMaterial(RawMaterial material) {
		list.remove(material);
		setArrayList();
	}

	public void removeSelectedRows(int[] select) {
		for (int i = 0; i < select.length; i++) {
			removeMaterial(arrayList.get(select[i]));
		}
		setArrayList();
	}

	public ArrayList<RawMaterial> getAllMaterial() {
		return arrayList;
	}

	public Set<Entry<RawMaterial, Integer>> getEntrySet() {
		return list.entrySet();
	}

	public int getNumber(RawMaterial mat) {
		return list.get(mat);
	}

	public void sale(int number) {
		for (Entry<RawMaterial, Integer> entry : list.entrySet()) {
			entry.getKey().subStock(entry.getValue() * number);
		}
	}

	private void setArrayList() {
		arrayList = new ArrayList<RawMaterial>(list.keySet());
		arrayList.sort(new Comparator<RawMaterial>() {
			@Override
			public int compare(RawMaterial o1, RawMaterial o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
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
			return list.get(arrayList.get(rowIndex));
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
			list.put(arrayList.get(rowIndex), (Integer) aValue);
			break;
		default:
			break;
		}
	}
}
