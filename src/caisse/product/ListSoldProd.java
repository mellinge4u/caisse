package caisse.product;

import java.util.HashMap;

public class ListSoldProd {

	protected HashMap<String, SoldProduct> list;
	
	public ListSoldProd() {
		this.list = new HashMap<String, SoldProduct>();
	}

	public void addSoldProduct(String product, double salePrice, MaterialList material) {
		list.putIfAbsent(product, new SoldProduct(product, salePrice, material));
		// TODO Gérer le cas ou l'objet est deja présent
	}
	
	public void removeSoldProduct(String product) {
		list.remove(product);
	}
	
	public double getSalePrice(String product) {
		return list.get(product).getSalePrice();
	}
	
	public void setSalePrice(String product, double price) {
		list.get(product).setSalePrice(price);
	}
	
	public double getProfit(String product) {
		return list.get(product).getProfit();
	}
	
}
