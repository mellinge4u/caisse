package caisse.sell;

import java.util.ArrayList;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;
import caisse.tools.TableModel;

public class TableModelSelectProduct extends TableModel {

	private ArrayList<SoldProduct> arrayList;
	private SoldProduct.prodType type;
	
	public TableModelSelectProduct(SoldProduct.prodType type) {
		super.colNames = new String[] { "Article", "Prix" };
		super.colClass = new Class<?>[] { String.class, Double.class};
		super.colEdit = new Boolean[] {false, false};
		this.type = type;
		arrayList = Model.getInstance().getAvailableSoldProd(type);
	}

	public SoldProduct getProduct(int row) {
		return arrayList.get(row);
	}
	
	public void updateArrayList() {
		arrayList = Model.getInstance().getAvailableSoldProd(type);
		fireTableStructureChanged();
	}
	
	@Override
	public int getRowCount() {
		return arrayList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return arrayList.get(rowIndex).getName();
		case 1:
			return arrayList.get(rowIndex).getSalePriceDouble();
		case 2:
			return arrayList.get(rowIndex).getQuantity();
		default:
			break;
		}
		return null;
	}
}
