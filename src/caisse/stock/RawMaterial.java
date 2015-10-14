package caisse.stock;

import java.awt.Color;

/**
 * This class represent one kind of basic item.
 * 
 * @author Raph
 * @version 1.0
 *
 */
public class RawMaterial {

	private String name;
	private int stock;
	private int unitPrice;
	private int alert;
	private int restockNum; // Used for average cost calculation
	private int restockCost; // Used for average cost calculation

	/**
	 * Simple constructor. Take only the name and initialize everything else to
	 * 0.
	 * 
	 * @param name
	 *            {@link String} - The name of the material.
	 * 
	 * @see String
	 */
	public RawMaterial(String name) {
		this.name = name;
		this.stock = 0;
		this.unitPrice = 0;
		this.alert = 0;
		this.restockNum = 0;
		this.restockCost = 0;
	}

	/**
	 * Full constructor. This should mainly used for reading items.
	 * 
	 * @param name
	 *            {@link String} - The name of the material.
	 * @param quantity
	 *            int - The current amount of this material.
	 * @param alert
	 *            int - The amount above witch the color turn to orange, and
	 *            determine the amount to bye for the shopping list.
	 * @param unitPrice int - The last unit price calculate. 
	 * 
	 * @see String
	 */
	public RawMaterial(String name, int quantity, int alert, int unitPrice) {
		this.name = name;
		this.stock = quantity;
		this.unitPrice = unitPrice;
		this.alert = alert;
		this.restockNum = 0;
		this.restockCost = 0;
	}

	/**
	 * Return the name of the material. 
	 * 
	 * @return The name of the material ({@link String}).
	 */
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
		return unitPrice;
	}

	public int getRestockNum() {
		return restockNum;
	}

	public Color getColor() {
		Color c = Color.WHITE;
		if (stock <= alert) {
			c = new Color(255, 153, 51); // ORANGE
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
		unitPrice = restockCost / restockNum;
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

	public int getAlert() {
		return alert;
	}

	public void setAlert(int alert) {
		this.alert = alert;
	}

}
