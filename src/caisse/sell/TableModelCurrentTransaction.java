package caisse.sell;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import caisse.Model;
import caisse.historic.Transaction;
import caisse.sellProcuct.SoldProduct;
import caisse.tools.CellRender;
import caisse.tools.TableModel;

public class TableModelCurrentTransaction extends TableModel {

	protected Model model;
	protected HashMap<SoldProduct, Integer> transaction;
	protected ArrayList<SoldProduct> arrayList;

	public TableModelCurrentTransaction(Model model) {
		super.colNames = new String[] { "Article", "Prix unitaire", "Quantité",
		"Prix" };
		super.colClass = new Class<?>[] { String.class, Double.class,
				Integer.class, Double.class };
		super.colEdit = new Boolean[] { false, false, true, false };
		this.model = model;
		transaction = new HashMap<SoldProduct, Integer>();
		setArratList();
	}

	public void setArratList() {
		ArrayList<SoldProduct> arrayList = new ArrayList<SoldProduct>(
				transaction.keySet());
		arrayList.sort(new Comparator<SoldProduct>() {
			@Override
			public int compare(SoldProduct o1, SoldProduct o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		this.arrayList = arrayList;
	}

	public ArrayList<SoldProduct> getAllProduct() {
		return arrayList;
	}

	public void addItem(SoldProduct product, int quantity) {
		transaction.put(product, quantity);
		setArratList();
	}

	public void removeItem(SoldProduct product) {
		transaction.remove(product);
		setArratList();
	}

	public void clear() {
		transaction.clear();
		setArratList();
	}

	public int getCost() {
		int price = 0;
		for (Entry<SoldProduct, Integer> ent : transaction.entrySet()) {
			price += ent.getValue() * ent.getKey().getSalePrice();
		}
		return price;
	}

	public void validTransaction(int clientId, int cashAdd) {
		Transaction trans = new Transaction(clientId, getCost(), cashAdd,
				new Date(), Model.GREEN);
		for (Entry<SoldProduct, Integer> entry : transaction.entrySet()) {
			entry.getKey().sale(entry.getValue());
			trans.addArchivedProd(entry.getKey().getName(), entry.getValue());
		}
		if (trans.getNumberArticle() > 0) {
			model.validTransaction(trans);
		}
		Model.getInstance().addUndoneTransaction(trans);
		this.clear();
	}

	@Override
	public int getRowCount() {
		return transaction.size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex == transaction.size()) {
			switch (columnIndex) {
			case 0:
				return "Total :";
			case 1:
				return null;
			case 2:
				return null;
			case 3:
				return (double) getCost() / 100;
			default:
				break;
			}
		} else {
			switch (columnIndex) {
			case 0:
				return arrayList.get(rowIndex).getName();
			case 1:
				return (double) arrayList.get(rowIndex).getSalePrice() / 100;
			case 2:
				return transaction.get(arrayList.get(rowIndex));
			case 3:
				double val = (double) arrayList.get(rowIndex).getSalePrice()
						/ 100 * transaction.get(arrayList.get(rowIndex));
				return val;
			default:
				break;
			}
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex < transaction.size()) {
			switch (columnIndex) {
			case 2:
				transaction.put(arrayList.get(rowIndex), (int) aValue);
				break;
			default:
				break;
			}
			model.update();
		}
	}

	public SoldProduct[] getAllProductArray() {
		return (SoldProduct[]) arrayList.toArray();
	}

	@Override
	public CellRender getColumnModel(int col) {
		return new CellRender(colClass[col], colEdit[col], true);
	}

}
