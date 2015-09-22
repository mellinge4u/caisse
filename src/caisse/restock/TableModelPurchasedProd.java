package caisse.restock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.error.NameAlreadyTakenError;
import caisse.file.WriteFile;
import caisse.historic.Transaction;
import caisse.stock.RawMaterial;

public class TableModelPurchasedProd extends AbstractTableModel {

	public static String fileName = "Articles Achete";

	protected Model model;
	protected HashMap<String, PurchasedProduct> list;
	protected String[] colNames = { "Produit", "Prix Unitaire", "Quantité",
			"Prix Total" };
	protected Class<?>[] colClass = { String.class, Double.class,
			Integer.class, Double.class };
	protected Boolean[] colEdit = { true, true, true, false };

	public TableModelPurchasedProd(Model model) {
		this.model = model;
		this.list = new HashMap<String, PurchasedProduct>();
	}

	public void addPurchasedProduct(String product, int price,
			RawMaterial material, int number) {
		if (list.containsKey(product)) {
			throw new NameAlreadyTakenError(product);
		} else {
			list.put(product, new PurchasedProduct(product, price, material,
					number));
		}
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

	public int getTotalPrice() {
		int total = 0;
		for (PurchasedProduct prod : getAllProducts()) {
			total += prod.getNumberBought() * prod.getPurchasePrice();
		}
		return total;
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
		arrayList.sort(new Comparator<PurchasedProduct>() {
			@Override
			public int compare(PurchasedProduct o1, PurchasedProduct o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return arrayList;
	}

	public void clearRestock() {
		for (PurchasedProduct prod : getAllProducts()) {
			prod.clearRestock();
		}
	}

	public void restock(boolean liquide) {
		int restockPrice = 0;
		if (liquide) {
			restockPrice = getTotalPrice();
			model.debitUser(-1, restockPrice);
			model.writeAccount();
		}
		Transaction trans = new Transaction(-1, -getTotalPrice(), -restockPrice, new Date());
		for (PurchasedProduct prod : getAllProducts()) {
			if (prod.getNumberBought() > 0) {
				trans.addArchivedProd(prod.getName(), prod.getNumberBought());
				prod.restock();
			}
		}
		if (trans.getNumberArticle() > 0) {
			model.addHistoric(trans);
		}
	}

	public void writeData() {
		WriteFile.writeFile(fileName, this.toString());
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
		ArrayList<PurchasedProduct> array = getAllProducts();
		switch (columnIndex) {
		case 0:
			return array.get(rowIndex).getName();
		case 1:
			return ((double) array.get(rowIndex).getPurchasePrice() / 100);
		case 2:
			return array.get(rowIndex).getNumberBought();
		case 3:
			return ((double) array.get(rowIndex).getPurchasePrice() / 100)
					* array.get(rowIndex).getNumberBought();
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
		ArrayList<PurchasedProduct> array = getAllProducts();
		switch (columnIndex) {
		case 0:
			array.get(rowIndex).setName((String) aValue);
			writeData();
			break;
		case 1:
			array.get(rowIndex).setPurchasePrice((int) ((double) aValue * 100));
			writeData();
			break;
		case 2:
			array.get(rowIndex).setNumberBought((int) aValue);
			break;
		default:
			break;
		}
		model.update();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (PurchasedProduct prod : getAllProducts()) {
			sb.append(prod.getName());
			sb.append("; ");
			sb.append(prod.getPurchasePrice());
			sb.append("; ");
			sb.append(prod.getMaterial().getName());
			sb.append("; ");
			sb.append(prod.getNumber());
			sb.append("\n");
		}
		return sb.toString();
	}
}
