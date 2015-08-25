package caisse;

import java.util.Observable;

import caisse.product.ListPurchasedProd;
import caisse.product.ListRawMaterial;
import caisse.product.ListSoldProd;

public class Model extends Observable {

	protected ListRawMaterial rawMaterials;
	protected ListPurchasedProd purchasedProd;
	protected ListSoldProd soldProd;
	
	public Model() {
		this.rawMaterials = new ListRawMaterial();
		this.purchasedProd = new ListPurchasedProd();
		this.soldProd = new ListSoldProd();
	}

	private void update() {
		setChanged();
		notifyObservers();
	}
	
}
