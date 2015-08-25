package caisse;

public class SoldProduct {

	protected String name;
	protected double salePrice;
	protected double profit;
	protected MaterialList material;
	
	public SoldProduct(String name, double salePrice, MaterialList material) {
		this.name = name;
		this.salePrice = salePrice;
		this.material = material;
	}

}
