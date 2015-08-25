package caisse.product;

import java.util.HashMap;
import java.util.Map.Entry;

public class MaterialList {

	protected HashMap<RawMaterial, Integer> list;

	public MaterialList() {
		this.list = new HashMap<RawMaterial, Integer>();
	}

	public MaterialList(HashMap<RawMaterial, Integer> list) {
		this.list = list;
	}

	public void sale(int number) {
		for (Entry<RawMaterial, Integer> entry : list.entrySet()) {
			entry.getKey().subStock(entry.getValue());
		}
	}
}
