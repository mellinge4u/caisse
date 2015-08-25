package caisse.product;

public class SoldProduct {

	protected String name;
	protected double salePrice;
	protected double profit;
	protected MaterialList material;
	
	public SoldProduct(String name, double salePrice, MaterialList material) {
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

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getProfit() {
		return profit;
	}

}
