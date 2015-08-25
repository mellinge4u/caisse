package caisse.product;

import java.util.HashMap;
import java.util.Map.Entry;

public class MaterialList {

	protected HashMap<RawMaterial, Integer> list;

	public MaterialList() {
		this.list = new HashMap<RawMaterial, Integer>();
	}

	public void addMaterial(RawMaterial material, int quantity) {
		list.put(material, quantity);
	}
	
	public void removeMaterial(RawMaterial material) {
		list.remove(material);
	}
	
	public void sale(int number) {
		for (Entry<RawMaterial, Integer> entry : list.entrySet()) {
			entry.getKey().subStock(entry.getValue());
		}
	}
}
