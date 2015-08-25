package caisse.product;

import java.util.HashMap;

import caisse.error.NameAlreadyTakenError;

public class ListSoldProd {

	protected HashMap<String, SoldProduct> list;

	public ListSoldProd() {
		this.list = new HashMap<String, SoldProduct>();
	}

	public void addSoldProduct(String product, int salePrice) {
		SoldProduct mat = null;
		mat = list.putIfAbsent(product, new SoldProduct(product, salePrice));
		if (mat != null) {
			throw new NameAlreadyTakenError(product);
		}
	}

	public void removeSoldProduct(String product) {
		list.remove(product);
	}

	public void addMaterial(String product, RawMaterial material, int quantity) {
		list.get(product).addMaterial(material, quantity);
	}
	
	public void removeMaterial(String product, RawMaterial material) {
		list.get(product).removeMaterial(material);
	}
	
	public double getSalePrice(String product) {
		return list.get(product).getSalePrice();
	}

	public void setSalePrice(String product, int price) {
		list.get(product).setSalePrice(price);
	}

	public double getProfit(String product) {
		return list.get(product).getProfit();
	}

	public SoldProduct getSoldProduct(String product) {
		return list.get(product);
	}
	
}
