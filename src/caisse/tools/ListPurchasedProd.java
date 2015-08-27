package caisse.tools;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import caisse.error.NameAlreadyTakenError;
import caisse.product.PurchasedProduct;
import caisse.product.RawMaterial;

public class ListPurchasedProd extends AbstractTableModel {

	protected HashMap<String, PurchasedProduct> list;
	protected String[] colNames = { "Produit", "Prix", "Quantité" };
	protected Class<?>[] colClass = { String.class, Double.class, Integer.class };
	protected Boolean[] colEdit = { false, true, true };

	public ListPurchasedProd() {
		this.list = new HashMap<String, PurchasedProduct>();
	}

	public void addPurchasedProduct(String product, int price,
			RawMaterial material, int number) {
		// TODO Gérer correctement les doublons de noms
		// PurchasedProduct mat = null;
		// mat = list.putIfAbsent(product, new PurchasedProduct(product, price,
		// material, number));
		list.put(product,
				new PurchasedProduct(product, price, material, number));
		// if (mat != null) {
		// throw new NameAlreadyTakenError(product);
		// }
	}

	public void removePurchasedProduct(String product) {
		list.remove(product);
	}

	public double getPurchasePrice(String product) {
		return list.get(product).getPurchasePrice();
	}

	public void setPurchasePrice(String product, int purchasePrice) {
		list.get(product).setPurchasePrice(purchasePrice);
	}

	public RawMaterial getProduct(String product) {
		return list.get(product).getMaterial();
	}

	public void setMaterial(String product, RawMaterial material) {
		list.get(material).setMaterial(material);
	}

	public int getNumber(String product) {
		return list.get(product).getNumber();
	}

	public void setNumber(String product, int number) {
		list.get(product).setNumber(number);
	}

	public int getNumberBought(String product) {
		return list.get(product).getNumberBought();
	}

	public void setNumberBought(String product, int number) {
		list.get(product).setNumberBought(number);
	}

	public PurchasedProduct getPurchasedProduct(String product) {
		return list.get(product);
	}

	public ArrayList<PurchasedProduct> getAllProducts() {
		ArrayList<PurchasedProduct> arrayList = new ArrayList<PurchasedProduct>(
				list.values());
		return arrayList;
	}

	public void clearRestock() {
		for (PurchasedProduct prod : getAllProducts()) {
			prod.clearRestock();
		}
	}

	public void restock() {
		for (PurchasedProduct prod : getAllProducts()) {
			if (prod.getNumberBought() > 0) {
				prod.restock();
			}
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
		ArrayList<PurchasedProduct> array = new ArrayList<PurchasedProduct>(
				list.values());
		switch (columnIndex) {
		case 0:
			return array.get(rowIndex).getName();
		case 1:
			return ((double) array.get(rowIndex).getPurchasePrice() / 100);
		case 2:
			return array.get(rowIndex).getNumberBought();
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
		ArrayList<PurchasedProduct> array = new ArrayList<PurchasedProduct>(
				list.values());
		switch (columnIndex) {
		case 1:
			array.get(rowIndex).setPurchasePrice((int) ((double) aValue * 100));
			break;
		case 2:
			array.get(rowIndex).setNumberBought((int) aValue);
			break;
		default:
			break;
		}
	}

}
