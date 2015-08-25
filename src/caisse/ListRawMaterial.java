package caisse;

import java.util.HashMap;

public class ListRawMaterial {

	protected HashMap<String, RawMaterial> list;
	
	public ListRawMaterial() {
		this.list = new HashMap<String, RawMaterial>();
	}
	
	
	public void addRawMaterial(String name) {
		list.putIfAbsent(name, new RawMaterial(name));
		//TODO G�rer les cas ou l'objet est deja pr�sent
	}

	public void addStock(String product, int number) {
		list.get(product).addStock(number);
	}
	
	public void subStock(String product, int number) {
		list.get(product).subStock(number);
	}
	
}
