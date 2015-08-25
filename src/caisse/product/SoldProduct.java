package caisse.product;

public class SoldProduct {

	protected String name;
	protected int salePrice;
	protected int profit; // TODO Calculer le profit fait
	protected MaterialList material;

	public SoldProduct(String name, int salePrice, MaterialList material) {
		this.name = name;
		this.salePrice = salePrice;
		this.profit = 0;
		this.material = material;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public double getProfit() {
		return profit;
	}

}
