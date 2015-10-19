package caisse.stock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.error.NameAlreadyTakenError;
import caisse.file.WriteFile;
import caisse.historic.Transaction;

public class TableModelRawMaterial extends AbstractTableModel {

	public static String fileName = "Stock";

	protected Model model;
	protected HashMap<String, RawMaterial> list;
	protected String[] colNames = { "Produit", "Stock", "Prix unitaire", "Niveau d'alerte" };
	protected Class<?>[] colClass = { String.class, Integer.class,
			Double.class, Integer.class };
	protected Boolean[] colEdit = { false, true, false, true };
	protected ArrayList<RawMaterial> arrayList;

	public TableModelRawMaterial(Model model) {
		this.model = model;
		this.list = new HashMap<String, RawMaterial>();
		setArrayList();
	}

	public void addRawMaterial(String product) {
		if (list.containsKey(product)) {
			throw new NameAlreadyTakenError(product);
		} else {
			list.put(product, new RawMaterial(product));
			setArrayList();
		}
	}

	public void addRawMaterial(String product, int quantity, int alert,
			int unitaryPrice) {
		if (list.containsKey(product)) {
			throw new NameAlreadyTakenError(product);
		} else {
			list.put(product, new RawMaterial(product, quantity, alert,
					unitaryPrice));
			setArrayList();
		}
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
		for (RawMaterial mat : arrayList) {
			if (mat.getRestockNum() > 0)
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
		case 3:
			return arrayList.get(rowIndex).getAlert();
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
			RawMaterial mat = arrayList.get(rowIndex);
			int oldStock = mat.getStock();
			int mod = (int) aValue - oldStock;
			mat.setStock((int) aValue);
			Transaction tran;
			if (mod > 0) {	// Add Stock
				tran = new Transaction(-1, 0, 0, new Date(), Transaction.CYAN);
				tran.addArchivedProd("Ajout Stock " + mat.getName(), mod);
			} else {	// Remove Stock
				tran = new Transaction(-1, 0, 0, new Date(), Transaction.RED);
				tran.addArchivedProd("Retrait Stock " + mat.getName(), -mod);
			}
			model.addHistoric(tran);
			writeData();
			model.update();
			break;
		case 3:
			arrayList.get(rowIndex).setAlert((int) aValue);
			writeData();
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (RawMaterial mat : arrayList) {
			sb.append(mat.getName());
			sb.append("; ");
			sb.append(mat.getStock());
			sb.append("; ");
			sb.append(mat.getAlert());
			sb.append("; ");
			sb.append(mat.getUnitaryPrice());
			sb.append("\n");
		}
		return sb.toString();
	}
}
