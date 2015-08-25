package caisse.product;

public class PurchasedProduct {

	protected String name;
	protected double purchasePrice;
	protected RawMaterial product;
	protected int number;
	
	public PurchasedProduct(String name, double price, RawMaterial product, int number) {
		this.name = name;
		this.purchasePrice = price;
		this.product = product;
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

	public RawMaterial getProduct() {
		return product;
	}

	public void setProduct(RawMaterial product) {
		this.product = product;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
