package caisse.product;

import java.util.HashMap;
import java.util.Map.Entry;

public class CurrentTransaction {

	protected HashMap<SoldProduct, Integer> transaction;
	
	public CurrentTransaction() {
		transaction = new HashMap<SoldProduct, Integer>();
	}

	public void addItem(SoldProduct product, int quantity) {
		transaction.put(product, quantity);
	}
	
	public void removeItem(SoldProduct product) {
		transaction.remove(product);
	}
	
	public void clear() {
		transaction.clear();
	}
	
	public void validTransaction() {
		// TODO A verifier
		for(Entry<SoldProduct, Integer> entry : transaction.entrySet()) {
			entry.getKey().sale(entry.getValue());
		}
	}
}
