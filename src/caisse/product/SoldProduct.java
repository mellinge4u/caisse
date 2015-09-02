package caisse.product;

import java.util.ArrayList;
import java.util.Map.Entry;

import caisse.tools.MaterialList;

public class SoldProduct {

	protected String name;
	protected int salePrice;
	protected MaterialList listMaterial;

	public SoldProduct(String name, int salePrice) {
		this.name = name;
		this.salePrice = salePrice;
		this.listMaterial = new MaterialList();
	}

	public void addMaterial(RawMaterial material, int quantity) {
		listMaterial.addMaterial(material, quantity);
	}

	public void removeMaterial(RawMaterial material) {
		listMaterial.removeMaterial(material);
	}

	public ArrayList<RawMaterial> getAllMaterials() {
		return listMaterial.getAllMaterial();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getNumber(RawMaterial mat) {
		return listMaterial.getNumber(mat);
	}

	public int getQuantity() {
		int quantity = Integer.MAX_VALUE;
		int cost;
		for(Entry<RawMaterial, Integer> ent : listMaterial.getEntrySet()) {
			cost = ent.getKey().getStock() / ent.getValue();
			if (cost < quantity) {
				quantity = cost;
			}
		}
		return quantity;
	}

	public void sale(int number) {
		listMaterial.sale(number);
	}

	@Override
	public String toString() {
		return name;
	}

}
