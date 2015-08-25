package caisse;

import java.util.HashMap;

public class MaterialList {

	protected HashMap<RawMaterial, Integer> list;
	
	public MaterialList() {
		this.list = new HashMap<RawMaterial, Integer>();
	}

	public MaterialList(HashMap<RawMaterial, Integer> list) {
		this.list = list;
	}

}
