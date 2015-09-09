package caisse.historic;

public class ArchivedProd {

	protected String name;
	protected int quantity;

	public ArchivedProd(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" | ");
		sb.append(quantity);
		sb.append(" | ");
		return sb.toString();
	}
}
