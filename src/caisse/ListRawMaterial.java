package caisse;

import java.util.HashMap;

public class ListRawMaterial {

	protected HashMap<String, RawMaterial> list;
	
	public ListRawMaterial() {
		this.list = new HashMap<String, RawMaterial>();
	}
	
	public void addRawMaterial(String name) {
		list.putIfAbsent(name, new RawMaterial(name));
		// TODO Gérer le cas ou l'objet est deja présent
	}

	public void removeRawMaterial(String name) {
		list.remove(name);
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
	
}
