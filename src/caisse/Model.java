package caisse;

import java.util.Observable;

import caisse.product.ListPurchasedProd;
import caisse.product.ListRawMaterial;
import caisse.product.ListSoldProd;
import caisse.product.PurchasedProduct;
import caisse.product.RawMaterial;
import caisse.product.SoldProduct;

public class Model extends Observable {

	protected ListRawMaterial rawMaterials;
	protected ListPurchasedProd purchasedProd;
	protected ListSoldProd soldProd;

	public Model() {
		this.rawMaterials = new ListRawMaterial();
		this.purchasedProd = new ListPurchasedProd();
		this.soldProd = new ListSoldProd();
	}

	public void addRawMaterial(String product) {
		rawMaterials.addRawMaterial(product);
	}
	
	public RawMaterial getRawMateriel(String product) {
		return rawMaterials.getRawMaterial(product);
	}

	public void addPurchasedProduct(String product, int price, RawMaterial material, int number) {
		purchasedProd.addPurchasedProduct(product, price, material, number);
	}
	
	public PurchasedProduct getPurchasedProduct(String product) {
		return purchasedProd.getPurchasedProduct(product);
	}

	public void getSoldProduct(String product, int salePrice) {
		soldProd.addSoldProduct(product, salePrice);
	}
	
	public SoldProduct getSoldProduct(String product) {
		return soldProd.getSoldProduct(product);
	}

	public void addMaterialToSoldProduct(String product, RawMaterial material, int quantity) {
		soldProd.addMaterial(product, material, quantity);
	}
	
	public void removeMaterialToSoldProduct(String product, RawMaterial material) {
		soldProd.removeMaterial(product, material);
	}
	
	private void update() {
		setChanged();
		notifyObservers();
	}

}
