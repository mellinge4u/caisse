package caisse.historic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transaction {

	protected String client;
	protected ArrayList<ArchivedProd> listProd;
	protected int price;
	protected Date date;
	public static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy.MM.dd G 'at' HH:mm:ss z");
	protected int numberArticle;

	public Transaction(String client, int price, Date date) {
		super();
		this.client = client;
		listProd = new ArrayList<ArchivedProd>();
		this.price = price;
		this.date = date;
		numberArticle = 0;
	}

	public void addArchivedProd(String product, int quantity) {
		listProd.add(0, new ArchivedProd(product, quantity));
		numberArticle += quantity;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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
			sb.append(prod.getName() + " x");
			sb.append(prod.getQuantity() + "; ");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(client);
		sb.append("; ");
		sb.append("" + price);
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
