package caisse;

public class RawMaterial {

	protected String name;
	protected int stock;
	protected double averageCost;
	
	public RawMaterial(String name) {
		this.name = name;
		this.stock = 0;
		this.averageCost = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getAverageCost() {
		return averageCost;
	}

	public void addStock(int number) {
		stock+=number;
	}

	public void subStock(int number) {
		stock-=number;
	}

}
