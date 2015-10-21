package caisse.historic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import com.sun.org.apache.regexp.internal.RE;

import caisse.Model;

public class Transaction extends AbstractTableModel {

	protected int clientId;
	protected ArrayList<ArchivedProd> listProd;
	protected String[] colNames = { "Produit", "Quantité" };
	protected Class<?>[] colClass = { String.class, Integer.class };

	protected Color col;
	protected int price;
	protected int cashAdd;
	protected Date date;
	protected int numberArticle;

	public Transaction(int clientId, int price, int cashAdd, Date date,
			Color col) {
		super();
		this.clientId = clientId;
		listProd = new ArrayList<ArchivedProd>();
		this.price = price;
		this.cashAdd = cashAdd;
		this.date = date;
		numberArticle = 0;
		this.col = col;
	}

	public int getCashAdd() {
		return cashAdd;
	}

	public void setCashAdd(int cashAdd) {
		this.cashAdd = cashAdd;
	}

	public void addArchivedProd(String product, int quantity) {
		listProd.add(0, new ArchivedProd(product, quantity));
		numberArticle += quantity;
	}

	public int getProdQuantity(String prodName) {
		for (ArchivedProd prod : listProd) {
			if (prod.getName().equals(prodName)) {
				return prod.getQuantity();
			}
		}
		return 0;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumberArticle() {
		return numberArticle;
	}

	public ArrayList<ArchivedProd> getListTransaction() {
		return listProd;
	}

	public String getArticleString() {
		StringBuilder sb = new StringBuilder();
		for (ArchivedProd prod : listProd) {
			int q = prod.getQuantity();
			sb.append(prod.getName());
			if (q > 1) {
				sb.append(" x" + q);
			}
			sb.append("; ");
		}
		return sb.toString();
	}

	public String getColorName() {
		if (col.equals(Model.GREEN)) {
			return "GREEN";
		} else if (col.equals(Model.RED)) {
			return "RED";
		} else if (col.equals(Model.CYAN)) {
			return "CYAN";
		} else if (col.equals(Model.BLUE)) {
			return "BLUE";
		} else if (col.equals(Model.YELLOW)) {
			return "YELLOW";
		} else {
			return "GRAY";
		}
	}

	public boolean isContaining(String product) {
		boolean contain = false;
		for (ArchivedProd ap : listProd) {
			if (ap.name.equals(product)) {
				contain = true;
				break;
			}
		}
		return contain;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(clientId);
		sb.append("; ");
		sb.append("" + price);
		sb.append("; ");
		sb.append("" + cashAdd);
		sb.append("; ");
		sb.append(Model.dateFormatFull.format(date));
		sb.append("; ");
		for (ArchivedProd trans : listProd) {
			sb.append(trans);
		}
		sb.append("; ");
		sb.append(getColorName());
		sb.append("\n");
		return sb.toString();
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
		return listProd.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return listProd.get(rowIndex).getName();
		case 1:
			return listProd.get(rowIndex).getQuantity();
		default:
			break;
		}
		return null;
	}

	public Color getColor() {
		return col;
	}

}
