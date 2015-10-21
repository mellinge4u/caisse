package caisse.restock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import com.sun.javafx.geom.PickRay;

import caisse.Model;
import caisse.error.NameAlreadyTakenError;
import caisse.file.WriteFile;
import caisse.historic.Transaction;
import caisse.stock.RawMaterial;

public class TableModelPurchasedProd extends AbstractTableModel {

	public static String fileName = "Articles Achete";

	protected Model model;
	protected HashMap<String, PurchasedProduct> list;
	protected ArrayList<PurchasedProduct> arrayList;
	protected String[] colNames = { "Produit", "Magasin", "Prix Unitaire", "Quantité",
			"Prix Total" };
	protected Class<?>[] colClass = { String.class, String.class, Double.class,
			Integer.class, Double.class };
	protected Boolean[] colEdit = { true, true, true, true, false };

	public TableModelPurchasedProd(Model model) {
		this.model = model;
		this.list = new HashMap<String, PurchasedProduct>();
		setArrayList();
	}

	public void addPurchasedProduct(String product, int price,
			RawMaterial material, int number, String store) {
		if (list.containsKey(product)) {
			throw new NameAlreadyTakenError(product);
		} else {
			list.put(product, new PurchasedProduct(product, price, material,
					number, store));
		}
		setArrayList();
	}

	public void removePurchasedProduct(String product) {
		list.remove(product);
		setArrayList();
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

	public PurchasedProduct getProd(int row) {
		return arrayList.get(row);
	}
	
	public ArrayList<PurchasedProduct> getAllProducts() {
		return arrayList;
	}

	public void setArrayList() {
		arrayList = new ArrayList<PurchasedProduct>(
				list.values());
		arrayList.sort(new Comparator<PurchasedProduct>() {
			@Override
			public int compare(PurchasedProduct o1, PurchasedProduct o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
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
		Transaction trans = new Transaction(-1, -getTotalPrice(), -restockPrice, new Date(), Model.YELLOW);
		for (PurchasedProduct prod : arrayList) {
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
		switch (columnIndex) {
		case 0:
			return arrayList.get(rowIndex).getName();
		case 1:
			return arrayList.get(rowIndex).getStore();
		case 2:
			return ((double) arrayList.get(rowIndex).getPurchasePrice() / 100);
		case 3:
			return arrayList.get(rowIndex).getNumberBought();
		case 4:
			return ((double) arrayList.get(rowIndex).getPurchasePrice() / 100)
					* arrayList.get(rowIndex).getNumberBought();
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
		case 0:
			arrayList.get(rowIndex).setName((String) aValue);
			writeData();
			break;
		case 1:
			arrayList.get(rowIndex).setStore((String) aValue);
			writeData();
			break;
		case 2:
			arrayList.get(rowIndex).setPurchasePrice((int) ((double) aValue * 100));
			writeData();
			break;
		case 3:
			arrayList.get(rowIndex).setNumberBought((int) aValue);
			break;
		default:
			break;
		}
		model.update();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (PurchasedProduct prod : arrayList) {
			sb.append(prod.getName());
			sb.append("; ");
			sb.append(prod.getPurchasePrice());
			sb.append("; ");
			sb.append(prod.getMaterial().getName());
			sb.append("; ");
			sb.append(prod.getNumber());
			sb.append("; ");
			sb.append(prod.getStore());
			sb.append("\n");
		}
		return sb.toString();
	}
}
