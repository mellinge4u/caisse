package caisse.sellProcuct;

import java.util.ArrayList;
import java.util.Map.Entry;

import caisse.stock.RawMaterial;
import caisse.stock.TableModelListRawMaterial;

public class SoldProduct {

	public enum prodType {FOOD, DRINK, MISC};
	protected String name;
	protected int salePrice;
	protected TableModelListRawMaterial listMaterial;
	protected prodType type; 

	public SoldProduct(String name, int salePrice, prodType type) {
		this.name = name;
		this.salePrice = salePrice;
		this.listMaterial = new TableModelListRawMaterial();
		this.type = type;
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

	public prodType getType() {
		return type;
	}
	
	public String getTypeName() {
		switch (type) {
		case DRINK:
			return "Boisson";
		case FOOD:
			return "Nourriture";
		case MISC:
			return "Divers";
		default:
			return "Divers";
		}
	}
	
	public void setType(prodType type) {
		this.type = type;
	}
	
	public int getSalePrice() {
		return salePrice;
	}

	public int getCost() {
		int price = 0;
		for (RawMaterial mat : getAllMaterials()) {
			price += mat.getUnitaryPrice() * getNumber(mat);
		}
		return price;
	}
	
	public int getProfit() {
		return getSalePrice() - getCost();
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
		for (Entry<RawMaterial, Integer> ent : listMaterial.getEntrySet()) {
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

	public TableModelListRawMaterial getTableModel() {
		return listMaterial;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
