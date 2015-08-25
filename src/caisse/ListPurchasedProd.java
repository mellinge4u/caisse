package caisse;

import java.util.HashMap;

public class ListPurchasedProd {

	protected HashMap<String, PurchasedProduct> list;
	
	public ListPurchasedProd() {
		this.list = new HashMap<String, PurchasedProduct>();
	}

	public void addPurchasedProduct(String name, double price, RawMaterial product, int number) {
		list.putIfAbsent(name, new PurchasedProduct(name, price, product, number));
		// TODO Gérer le cas ou l'objet est deja present
	}
	
}
