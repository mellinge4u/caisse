package caisse.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import caisse.product.RawMaterial;

public class MaterialList extends AbstractTableModel {

	protected HashMap<RawMaterial, Integer> list;
	protected String[] colNames = { "Produit", "Quantite"};
	protected Class<?>[] colClass = { String.class, Integer.class};
	protected Boolean[] colEdit = { false, true};

	public MaterialList() {
		this.list = new HashMap<RawMaterial, Integer>();
	}

	public void addMaterial(RawMaterial material, int quantity) {
		list.put(material, quantity);
	}
	
	public void removeMaterial(RawMaterial material) {
		list.remove(material);
	}
	
	public ArrayList<RawMaterial> getAllMaterial() {
		return new ArrayList<RawMaterial>(list.keySet());
	}
	
	public Set<Entry<RawMaterial,Integer>> getEntrySet() {
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
		ArrayList<RawMaterial> array = new ArrayList<RawMaterial>(list.keySet());
		switch (columnIndex) {
		case 0:
			return array.get(rowIndex).getName();
		case 1:
			return list.get(array.get(rowIndex));
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
		ArrayList<RawMaterial> array = new ArrayList<RawMaterial>(list.keySet());
		switch (columnIndex) {
		case 1:
			list.put(array.get(rowIndex), (Integer) aValue);
			break;
		default:
			break;
		}
	}
}
