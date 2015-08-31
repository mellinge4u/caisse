package caisse.product;

import java.util.ArrayList;

import caisse.tools.MaterialList;

public class SoldProduct {

	protected String name;
	protected int salePrice;
	protected int profit; 		// TODO Calculer le profit fait
	protected MaterialList listMaterial;

	public SoldProduct(String name, int salePrice) {
		this.name = name;
		this.salePrice = salePrice;
		this.profit = 0;
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

	public int getProfit() {
		return profit;
	}

	public int getNumber(RawMaterial mat) {
		return listMaterial.getNumber(mat);
	}
	
	public void sale(int number) {
		listMaterial.sale(number);
	}

	@Override
	public String toString() {
		return name;
	}
	
}
