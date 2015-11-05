package caisse.restock;

import caisse.stock.RawMaterial;

public class PurchasedProduct {

	protected String name;
	protected int purchasePrice;
	protected RawMaterial material;
	protected int number;
	protected int numberBought;
	protected String store;

	public PurchasedProduct(String name, int price, RawMaterial material,
			int number, String store) {
		this.name = name;
		this.purchasePrice = price;
		this.material = material;
		this.number = number;
		this.store = store;
		this.numberBought = 0;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public RawMaterial getMaterial() {
		return material;
	}

	public void setMaterial(RawMaterial product) {
		this.material = product;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberBought() {
		return numberBought;
	}

	public void setNumberBought(int numberBought) {
		this.numberBought = numberBought;
	}

	public void restock() {
		material.restock(numberBought * number, purchasePrice * numberBought);
		numberBought = 0;
	}

	public void clearRestock() {
		numberBought = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName());
		sb.append(';');
		sb.append(getPurchasePrice());
		sb.append(';');
		sb.append(getMaterial().getName());
		sb.append(';');
		sb.append(getNumber());
		sb.append(';');
		sb.append(getStore());
		sb.append(';');
		return sb.toString();
	}

}
