package caisse;

import java.util.ArrayList;
import java.util.Observable;

import caisse.historic.TableModelHistoric;
import caisse.historic.Transaction;
import caisse.restock.PurchasedProduct;
import caisse.restock.TableModelPurchasedProd;
import caisse.sell.TableModelCurrentTransaction;
import caisse.sellProcuct.SoldProduct;
import caisse.sellProcuct.TableModelSoldProd;
import caisse.stock.RawMaterial;
import caisse.stock.TableModelRawMaterial;
import caisse.user.TableModelUser;
import caisse.user.User;

public class Model extends Observable {

	protected TableModelRawMaterial rawMaterials;
	protected TableModelPurchasedProd purchasedProd;
	protected TableModelSoldProd soldProd;
	protected TableModelCurrentTransaction transaction;
	protected TableModelUser users;
	protected TableModelHistoric historic;

	public Model() {
		this.rawMaterials = new TableModelRawMaterial();
		this.purchasedProd = new TableModelPurchasedProd(this);
		this.soldProd = new TableModelSoldProd();
		this.transaction = new TableModelCurrentTransaction(this);
		this.users = new TableModelUser();
		this.historic = new TableModelHistoric(this);
	}

	// ////////////////////////// Raw Material //////////////////////////

	public void addRawMaterial(String product) {
		rawMaterials.addRawMaterial(product);
		updateRawMaterial();
	}

	public void addReadRawMaterial(String product, int quantity,
			int unitaryPrice) {
		rawMaterials.addRawMaterial(product, quantity, unitaryPrice);
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

	public TableModelRawMaterial getRawMaterialTableModel() {
		return rawMaterials;
	}

	public void writeStock() {
		rawMaterials.writeData();
	}

	public void updateRawMaterial() {
		rawMaterials.fireTableDataChanged();
		rawMaterials.writeData();
	}

	// ////////////////////////// Purchased Product //////////////////////////

	public void addPurchasedProduct(String product, int price,
			RawMaterial material, int number) {
		purchasedProd.addPurchasedProduct(product, price, material, number);
		writePurchasedProduct();
		update();
	}

	public void addReadPurchasedProduct(String product, int price,
			RawMaterial material, int number) {
		purchasedProd.addPurchasedProduct(product, price, material, number);
	}

	public PurchasedProduct getPurchasedProduct(String product) {
		return purchasedProd.getPurchasedProduct(product);
	}

	public TableModelPurchasedProd getPurchasedProdModel() {
		return purchasedProd;
	}

	public int getTotalPriceRestock() {
		return purchasedProd.getTotalPrice();
	}

	public void clearRestock() {
		purchasedProd.clearRestock();
		update();
	}

	public void restock() {
		purchasedProd.restock();
		rawMaterials.endRestock();
		writeStock();
		update();
	}

	public void writePurchasedProduct() {
		purchasedProd.writeData();
	}

	// ////////////////////////// Sold Product //////////////////////////

	public void addSoldProduct(String product, int salePrice) {
		soldProd.addSoldProduct(product, salePrice);
		update();
	}

	public void addSoldProduct(String product, int salePrice,
			RawMaterial material) {
		soldProd.addSoldProduct(product, salePrice);
		soldProd.addMaterial(product, material, 1);
		update();
	}

	public void addReadSoldProduct(String product, int salePrice) {
		soldProd.addSoldProduct(product, salePrice);
	}

	public void addReadSoldProduct(String product, int salePrice,
			RawMaterial material) {
		soldProd.addSoldProduct(product, salePrice);
		soldProd.addMaterial(product, material, 1);
	}

	public SoldProduct getSoldProduct(String product) {
		return soldProd.getSoldProduct(product);
	}

	public TableModelSoldProd getSoldProdModel() {
		return soldProd;
	}

	public SoldProduct[] getAllSoldProdArray() {
		ArrayList<SoldProduct> list = getAllSoldProd();
		SoldProduct[] tab = new SoldProduct[list.size()];
		int i = 0;
		for (SoldProduct prod : list) {
			tab[i++] = prod;
		}
		return tab;
	}

	public ArrayList<SoldProduct> getAllSoldProd() {
		return soldProd.getAllSoldProd();
	}

	public void addMaterialToSoldProduct(String product, RawMaterial material,
			int quantity) {
		soldProd.addMaterial(product, material, quantity);
		update();
	}

	public void removeMaterialToSoldProduct(String product, RawMaterial material) {
		soldProd.removeMaterial(product, material);
		update();
	}

	public void addReadMaterialToSoldProduct(String product,
			RawMaterial material, int quantity) {
		soldProd.addMaterial(product, material, quantity);
	}

	public void removeReadMaterialToSoldProduct(String product,
			RawMaterial material) {
		soldProd.removeMaterial(product, material);
	}

	public void writeSoldProduct() {
		soldProd.writeData();
	}

	// - - - - - - - - - - - - - - Transaction - - - - - - - - - - - - - - //

	public TableModelCurrentTransaction getCurrentTransaction() {
		return transaction;
	}

	public void addProductOnTransaction(SoldProduct product) {
		transaction.addItem(product, 1);
		update();
	}

	public void addProductOnTransaction(SoldProduct product, int quantity) {
		transaction.addItem(product, quantity);
		update();
	}

	public void validTransaction(String client) {
		transaction.validTransaction(client);
		update();
	}

	// ////////////////////////// Historic //////////////////////////

	public void addHistoric(Transaction transaction) {
		historic.addHistoric(transaction);
		writeHistoric();
	}

	public void addReadHistoric(Transaction transaction) {
		historic.addHistoric(transaction);
	}

	public TableModelHistoric getHistoricModel() {
		return historic;
	}

	public void writeHistoric() {
		historic.writeData();
	}

	// ////////////////////////// Users //////////////////////////

	public TableModelUser getUsers() {
		return users;
	}

	public void addUser(User us) {
		users.addUser(us);
		update();
	}

	public void deleteUser(User us) {
		users.deleteUser(us);
		update();
	}

	public User getUserById(int i) {
		return users.getUserById(i);
	}

	public User getUserByName(String name) {
		return users.getUserByName(name);
	}

	// ////////////////////////// ... //////////////////////////

	public void update() {
		setChanged();
		notifyObservers();
	}

}
