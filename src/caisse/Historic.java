package caisse;

import java.util.ArrayList;
import java.util.Date;

public class Historic {

	protected String client;
	protected ArrayList<HistoricalProduct> listProd;
	protected int price;
	protected Date date;
	protected int numberArticle;

	public Historic(String client, int price, Date date) {
		super();
		this.client = client;
		listProd = new ArrayList<HistoricalProduct>();
		this.price = price;
		this.date = date;
		numberArticle = 0;
	}

	public void addProduct(String product, int quantity) {
		listProd.add(new HistoricalProduct(product, quantity));
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

	public ArrayList<HistoricalProduct> getListProd() {
		return listProd;
	}

	public String getArticleString() {
		StringBuilder sb = new StringBuilder();
		for (HistoricalProduct prod : listProd) {
			sb.append(prod.getName() + " x");
			sb.append(prod.getQuantity() + "; ");
		}
		return sb.toString();
	}

}
