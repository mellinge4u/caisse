package caisse;

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

}
