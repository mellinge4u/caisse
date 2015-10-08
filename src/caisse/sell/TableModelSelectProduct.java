package caisse.sell;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;

public class TableModelSelectProduct extends AbstractTableModel {

	private String[] colNames = { "Article", "Prix", "Quantité" };
	private Class<?>[] colClass = { String.class, Double.class, Integer.class };
	private ArrayList<SoldProduct> arrayList;
	private SoldProduct.prodType type;
	
	public TableModelSelectProduct(SoldProduct.prodType type) {
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

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
