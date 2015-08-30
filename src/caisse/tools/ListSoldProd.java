package caisse.tools;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import caisse.product.RawMaterial;
import caisse.product.SoldProduct;

public class ListSoldProd extends AbstractTableModel {

	protected HashMap<String, SoldProduct> list;
	protected String[] colNames = { "Produit", "Prix de vente", "Prix d'achat", "Benefise"};
	protected Class<?>[] colClass = { String.class, Integer.class, Integer.class, Integer.class};
	protected Boolean[] colEdit = { false, true, false, false};

	public ListSoldProd() {
		this.list = new HashMap<String, SoldProduct>();
	}

	public void addSoldProduct(String product, int salePrice) {
		SoldProduct mat = null;
		// TODO
		//mat = list.putIfAbsent(product, new SoldProduct(product, salePrice));
		mat = list.put(product, new SoldProduct(product, salePrice));
//		if (mat != null) {
//			throw new NameAlreadyTakenError(product);
//		}
	}

	public void removeSoldProduct(String product) {
		list.remove(product);
	}

	public ArrayList<SoldProduct> getAllSoldProd() {
		return new ArrayList<SoldProduct>(list.values());
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

	public double getProfit(String product) {
		return list.get(product).getProfit();
	}

	public SoldProduct getSoldProduct(String product) {
		return list.get(product);
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
		ArrayList<SoldProduct> array = new ArrayList<SoldProduct>(list.values());
		switch (columnIndex) {
		case 0:
			return array.get(rowIndex).getName();
		case 1:
			return array.get(rowIndex).getSalePrice();
		case 2:
			int price = 0;
			for(RawMaterial mat : array.get(rowIndex).)
			// TODO calculer le prix d'achat
			return 0;
		case 3:
			return array.get(rowIndex).getProfit();
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
		ArrayList<SoldProduct> array = new ArrayList<SoldProduct>(list.values());
		switch (columnIndex) {
		case 1:
			array.get(rowIndex).setSalePrice((Integer) aValue);
			break;
		default:
			break;
		}
	}
	
}
