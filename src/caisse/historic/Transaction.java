package caisse.historic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transaction {

	protected int clientId;
	protected ArrayList<ArchivedProd> listProd;
	protected int price;
	protected int cashAdd;
	protected Date date;
	public static SimpleDateFormat df = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	protected int numberArticle;

	public Transaction(int clientId, int price, int cashAdd, Date date) {
		super();
		this.clientId = clientId;
		listProd = new ArrayList<ArchivedProd>();
		this.price = price;
		this.cashAdd = cashAdd;
		this.date = date;
		numberArticle = 0;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(clientId);
		sb.append("; ");
		sb.append("" + price);
		sb.append("; ");
		sb.append("" + cashAdd);
		sb.append("; ");
		sb.append(df.format(date));
		sb.append("; ");
		for (ArchivedProd trans : listProd) {
			sb.append(trans);
		}
		sb.append("; ");
		sb.append("\n");
		return sb.toString();
	}

}
