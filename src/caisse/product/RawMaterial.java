package caisse.product;

public class RawMaterial {

	protected String name;
	protected int stock;
	protected int averageCost;
	private int restock; // Utilisé pour calculer le prix moyen
	private int restockCost; // Utilisé pour calculer le prix moyen

	public RawMaterial(String name) {
		this.name = name;
		this.stock = 0;
		this.averageCost = 0;
		this.restock = 0;
		this.restockCost = 0;
	}

	public String getName() {
		return name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void addStock(int number) {
		stock += number;
	}

	public void subStock(int number) {
		stock -= number;
	}

	public int getAverageCost() {
		return averageCost;
	}

	public void restock(int number, int price) {
		restock += number;
		stock += number;
		restockCost += price;
	}

	public void endRestock() {
		averageCost = restockCost / restock;
		restock = 0;
		restockCost = 0;
	}
	
}
