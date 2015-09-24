package caisse.sellProcuct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import caisse.error.NameAlreadyTakenError;
import caisse.file.WriteFile;
import caisse.stock.RawMaterial;

public class TableModelSoldProd extends AbstractTableModel {

	public static String fileName = "Articles Vendus";

	protected HashMap<String, SoldProduct> list;
	protected String[] colNames = { "Produit", "Prix de vente", "Prix d'achat",
			"Benefice", "Quantite disponible" };
	protected Class<?>[] colClass = { String.class, Double.class, Double.class,
			Double.class, Integer.class };
	protected Boolean[] colEdit = { true, true, false, false, false };
	protected ArrayList<SoldProduct> arrayList;

	public TableModelSoldProd() {
		this.list = new HashMap<String, SoldProduct>();
		setArrayList();
	}

	public void addSoldProduct(String product, int salePrice, SoldProduct.prodType type) {
		if (list.containsKey(product)) {
			throw new NameAlreadyTakenError(product);
		} else {
			list.put(product, new SoldProduct(product, salePrice, type));
			setArrayList();
		}
	}

	public void removeSoldProduct(String product) {
		list.remove(product);
		setArrayList();
	}

	public void setArrayList() {
		ArrayList<SoldProduct> arrayList = new ArrayList<SoldProduct>(
				list.values());
		arrayList.sort(new Comparator<SoldProduct>() {
			@Override
			public int compare(SoldProduct o1, SoldProduct o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		this.arrayList = arrayList;
	}
	
	public void addMaterial(String product, RawMaterial material, int quantity) {
		list.get(product).addMaterial(material, quantity);
	}

	public void removeMaterial(String product, RawMaterial material) {
		list.get(product).removeMaterial(material);
	}

	public double getSalePrice(String product) {
		return list.get(product).getSalePrice();
	}

	public void setSalePrice(String product, int price) {
		list.get(product).setSalePrice(price);
	}

	public SoldProduct getSoldProduct(String product) {
		return list.get(product);
	}

	public SoldProduct getSoldProduct(int row) {
		return arrayList.get(row);
	}

	public void writeData() {
		WriteFile.writeFile(fileName, this.toString());
	}

	public ArrayList<SoldProduct> getAllProducts() {
		return arrayList;
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
		ArrayList<SoldProduct> array = getAllProducts();
		int price;
		SoldProduct prod;
		switch (columnIndex) {
		case 0:
			return array.get(rowIndex).getName();
		case 1:
			return (double) array.get(rowIndex).getSalePrice() / 100;
		case 2:
			return (double) array.get(rowIndex).getCost() / 100;
		case 3:
			return (double) array.get(rowIndex).getProfit() / 100;
		case 4:
			return array.get(rowIndex).getQuantity();
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
		ArrayList<SoldProduct> array = getAllProducts();
		switch (columnIndex) {
		case 0:
			array.get(rowIndex).setName((String) aValue);
			writeData();
			break;
		case 1:
			array.get(rowIndex).setSalePrice((int) ((double) aValue * 100));
			writeData();
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SoldProduct prod : arrayList) {
			sb.append(prod.getName());
			sb.append("; ");
			sb.append(prod.getSalePrice());
			sb.append("; ");
			sb.append(prod.getType());
			sb.append("; ");
			for (RawMaterial mat : prod.getAllMaterials()) {
				sb.append(mat.getName() + " | ");
				sb.append(prod.getNumber(mat) + " | ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
