package caisse.product;

public class PurchasedProduct {

	protected String name;
	protected double purchasePrice;
	protected RawMaterial material;
	protected int number;

	public PurchasedProduct(String name, double price, RawMaterial material,
			int number) {
		this.name = name;
		this.purchasePrice = price;
		this.material = material;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
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

}
