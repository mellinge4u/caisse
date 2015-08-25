package caisse.product;

import java.util.HashMap;

public class ListPurchasedProd {

	protected HashMap<String, PurchasedProduct> list;
	
	public ListPurchasedProd() {
		this.list = new HashMap<String, PurchasedProduct>();
	}

	public void addPurchasedProduct(String product, double price, RawMaterial material, int number) {
		list.putIfAbsent(product, new PurchasedProduct(product, price, material, number));
		// TODO G�rer le cas ou l'objet est deja present
	}

	public void removePurchasedProduct(String product) {
		list.remove(product);
	}

	public double getPurchasePrice(String product) {
		return list.get(product).getPurchasePrice();
	}

	public void setPurchasePrice(String product, double purchasePrice) {
		list.get(product).setPurchasePrice(purchasePrice);
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
	
}
