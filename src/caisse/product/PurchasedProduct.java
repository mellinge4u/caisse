package caisse.product;

public class PurchasedProduct {

	protected String name;
	protected int purchasePrice;
	protected RawMaterial material;
	protected int number;
	protected int numberBought;

	public PurchasedProduct(String name, int price, RawMaterial material,
			int number) {
		this.name = name;
		this.purchasePrice = price;
		this.material = material;
		this.number = number;
		this.numberBought = 0;
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

	public void restock(int number) {
		material.restock(number, purchasePrice*number);
	}
	
}
