package caisse;

import java.util.ArrayList;
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
		update();
	}
	
	public RawMaterial getRawMateriel(String product) {
		return rawMaterials.getRawMaterial(product);
	}

	public ArrayList<RawMaterial> getAllMarerials() {
		// TODO Peu peu-etre etre supprimer, elle ne sert plus pour le moment
		return rawMaterials.getAllMaterials();
	}
	
	public ListRawMaterial getRawMaterialTableModel() {
		return rawMaterials;
	}
	
	public void addPurchasedProduct(String product, int price, RawMaterial material, int number) {
		purchasedProd.addPurchasedProduct(product, price, material, number);
		update();
	}
	
	public PurchasedProduct getPurchasedProduct(String product) {
		return purchasedProd.getPurchasedProduct(product);
	}

	public ListPurchasedProd getPurchasedProdModel() {
		return purchasedProd;
	}
	
	public void getSoldProduct(String product, int salePrice) {
		soldProd.addSoldProduct(product, salePrice);
	}
	
	public SoldProduct getSoldProduct(String product) {
		return soldProd.getSoldProduct(product);
	}

	public void addMaterialToSoldProduct(String product, RawMaterial material, int quantity) {
		soldProd.addMaterial(product, material, quantity);
		update();
	}
	
	public void removeMaterialToSoldProduct(String product, RawMaterial material) {
		soldProd.removeMaterial(product, material);
		update();
	}
	
	private void update() {
		setChanged();
		notifyObservers();
	}

}
