package caisse.product;

import java.util.HashMap;

import caisse.error.NameAlreadyTakenError;

public class ListRawMaterial {

	protected HashMap<String, RawMaterial> list;

	public ListRawMaterial() {
		this.list = new HashMap<String, RawMaterial>();
	}

	public void addRawMaterial(String product) {
		RawMaterial mat = null;
		mat = list.putIfAbsent(product, new RawMaterial(product));
		if (mat != null) {
			throw new NameAlreadyTakenError(product);
		}
	}

	public void removeRawMaterial(String product) {
		list.remove(product);
	}

	public int getStock(String product) {
		return list.get(product).getStock();
	}

	public void addStock(String product, int number) {
		list.get(product).addStock(number);
	}

	public void subStock(String product, int number) {
		list.get(product).subStock(number);
	}

	public double getAvreageCost(String product) {
		return list.get(product).getAverageCost();
	}

	public RawMaterial getRawMaterial(String product) {
		return list.get(product);
	}
	
}
