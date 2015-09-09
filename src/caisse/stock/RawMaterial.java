package caisse.stock;

import java.awt.Color;

public class RawMaterial {

	protected String name;
	protected int stock;
	protected int unitaryPrice;
	protected int alert;
	private int restockNum; // Utilisé pour calculer le prix moyen
	private int restockCost; // Utilisé pour calculer le prix moyen

	public RawMaterial(String name) {
		this.name = name;
		this.stock = 0;
		this.unitaryPrice = 0;
		this.alert = 0;
		this.restockNum = 0;
		this.restockCost = 0;
	}

	public RawMaterial(String name, int quantity, int unitaryPrice) {
		this.name = name;
		this.stock = quantity;
		this.unitaryPrice = unitaryPrice;
		this.alert = 0;
		this.restockNum = 0;
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

	public int getUnitaryPrice() {
		return unitaryPrice;
	}

	public int getRestockNum() {
		return restockNum;
	}

	public Color getColor() {
		Color c = Color.WHITE;
		if (stock < alert) {
			c = Color.ORANGE;
		}
		if (stock <= 0) {
			c = new Color(255, 51, 51); // RED
		}
		return c;
	}

	public void restock(int number, int price) {
		restockNum += number;
		stock += number;
		restockCost += price;
	}

	public void endRestock() {
		unitaryPrice = restockCost / restockNum;
		restockNum = 0;
		restockCost = 0;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getName().equals(((RawMaterial) obj).getName());
	}

	@Override
	public String toString() {
		return name;
	}

}
