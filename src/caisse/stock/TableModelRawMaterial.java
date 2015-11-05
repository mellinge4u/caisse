package caisse.stock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import caisse.Model;
import caisse.error.NameAlreadyTakenError;
import caisse.file.WriteFile;
import caisse.historic.Transaction;
import caisse.tools.CellRender;
import caisse.tools.CellRenderStock;
import caisse.tools.TableModel;

public class TableModelRawMaterial extends TableModel {

	public static String fileName = "Stock";

	protected Model model;
	protected HashMap<String, RawMaterial> list;
	protected ArrayList<RawMaterial> arrayList;

	public TableModelRawMaterial(Model model) {
		super.colNames = new String[] { "Produit", "Stock", "Prix unitaire", "Niveau d'alerte" };
		super.colClass = new Class<?>[] { String.class, Integer.class,
				Double.class, Integer.class };
		super.colEdit = new Boolean[] { false, true, false, true };
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
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 1:
			RawMaterial mat = arrayList.get(rowIndex);
			int oldStock = mat.getStock();
			int mod = (int) aValue - oldStock;
			mat.setStock((int) aValue);
			Transaction tran;
			if (mod > 0) {	// Add Stock
				tran = new Transaction(-1, 0, 0, new Date(), Model.CYAN);
				tran.addArchivedProd("Ajout Stock " + mat.getName(), mod);
			} else {	// Remove Stock
				tran = new Transaction(-1, 0, 0, new Date(), Model.RED);
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
	public CellRender getColumnModel(int col) {
		if (col == 1) {
			return new CellRenderStock();
		}
		return new CellRender(colClass[col], colEdit[col], false);
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
