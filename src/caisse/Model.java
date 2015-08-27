package caisse;

import java.util.ArrayList;
import java.util.Observable;

import caisse.product.PurchasedProduct;
import caisse.product.RawMaterial;
import caisse.product.SoldProduct;
import caisse.tools.ListPurchasedProd;
import caisse.tools.ListRawMaterial;
import caisse.tools.ListSoldProd;

public class Model extends Observable {

	protected ListRawMaterial rawMaterials;
	protected ListPurchasedProd purchasedProd;
	protected ListSoldProd soldProd;

	public Model() {
		this.rawMaterials = new ListRawMaterial();
		this.purchasedProd = new ListPurchasedProd();
		this.soldProd = new ListSoldProd();
	}

	/////////////////////////////// Raw Material ///////////////////////////////
	
	public void addRawMaterial(String product) {
		rawMaterials.addRawMaterial(product);
		update();
	}
	
	public RawMaterial getRawMateriel(String product) {
		return rawMaterials.getRawMaterial(product);
	}

	public RawMaterial[] getAllMaterialsArray() {
		ArrayList<RawMaterial> list = getAllMarerials();
		RawMaterial[] tab = new RawMaterial[list.size()];
		int i = 0;
		for (RawMaterial mat : list) {
			tab[i++] = mat;
		}
		return tab;
	}
	
	public ArrayList<RawMaterial> getAllMarerials() {
		return rawMaterials.getAllMaterials();
	}
	
	public ListRawMaterial getRawMaterialTableModel() {
		return rawMaterials;
	}
	
/////////////////////////////// Purchased Product ///////////////////////////////
	
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
	
/////////////////////////////// Sold Product ///////////////////////////////
	
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
	
/////////////////////////////// ... ///////////////////////////////
	
	private void update() {
		setChanged();
		notifyObservers();
	}

}
