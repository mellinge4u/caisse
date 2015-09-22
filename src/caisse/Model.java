package caisse;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import caisse.file.WriteFile;
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
import sun.nio.cs.HistoricallyNamedCharset;

public class Model extends Observable {

	public static SimpleDateFormat dateFormatSimple = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat dateFormatFull = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static DecimalFormat doubleFormatMoney = new DecimalFormat("#0.00");

	public static String repository = "caisse_BDD";
	public static String extention = "cens";

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

	public void addReadRawMaterial(String product, int quantity, int alert, int unitaryPrice) {
		rawMaterials.addRawMaterial(product, quantity, alert, unitaryPrice);
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

	public void addPurchasedProduct(String product, int price, RawMaterial material, int number) {
		purchasedProd.addPurchasedProduct(product, price, material, number);
		writePurchasedProduct();
		update();
	}

	public void addReadPurchasedProduct(String product, int price, RawMaterial material, int number) {
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

	public void restock(boolean liquide) {
		purchasedProd.restock(liquide);
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

	public void addSoldProduct(String product, int salePrice, RawMaterial material) {
		soldProd.addSoldProduct(product, salePrice);
		soldProd.addMaterial(product, material, 1);
		update();
	}

	public void deleteSoldProduct(String prod) {
		soldProd.removeSoldProduct(prod);
	}
	
	public void addReadSoldProduct(String product, int salePrice) {
		soldProd.addSoldProduct(product, salePrice);
	}

	public void addReadSoldProduct(String product, int salePrice, RawMaterial material) {
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

	public SoldProduct[] getAvailableSoldProdArray() {
		ArrayList<SoldProduct> list = getAllSoldProd();
		ArrayList<SoldProduct> newList = new ArrayList<SoldProduct>();
		for (SoldProduct prod : list) {
			if (prod.getQuantity() > 0) {
				newList.add(prod);
			}
		}
		SoldProduct[] tab = new SoldProduct[newList.size()];
		int i = 0;
		for (SoldProduct prod : newList) {
			tab[i++] = prod;
		}
		return tab;
	}

	public ArrayList<SoldProduct> getAllSoldProd() {
		return soldProd.getAllProducts();
	}

	public void addMaterialToSoldProduct(String product, RawMaterial material, int quantity) {
		soldProd.addMaterial(product, material, quantity);
		update();
	}

	public void addReadMaterialToSoldProduct(String product, RawMaterial material, int quantity) {
		soldProd.addMaterial(product, material, quantity);
	}

	public void removeMaterialToSoldProduct(String product, RawMaterial material) {
		soldProd.removeMaterial(product, material);
		update();
	}

	public void writeSoldProduct() {
		soldProd.writeData();
	}

	// - - - - - - - - - - - - - - Transaction - - - - - - - - - - - - - - //

	public TableModelCurrentTransaction getCurrentTransaction() {
		return transaction;
	}

	public void addProductOnCurrentTransaction(SoldProduct product) {
		transaction.addItem(product, 1);
		update();
	}

	public void addProductOnCurrentTransaction(SoldProduct product, int quantity) {
		transaction.addItem(product, quantity);
		update();
	}

	public void removeProductOnCurrentTransaction(SoldProduct product) {
		transaction.removeItem(product);
		update();
	}

	public void validTransaction(int clientId, int cashAdd) {
		transaction.validTransaction(clientId, cashAdd);
		update();
	}

	// ////////////////////////// Historic //////////////////////////

	public void addHistoric(Transaction transaction) {
		historic.addHistoric(transaction);
		writeHistoric(transaction.toString());
	}

	public void addReadHistoric(Transaction transaction) {
		historic.addReadHistoric(transaction);
	}

	public TableModelHistoric getHistoricModel() {
		return historic;
	}

	public ArrayList<Transaction> getAllHistoric() {
		return historic.getAllTransaction();
	}

	public void writeHistoric(String transaction) {
		WriteFile.addFile(TableModelHistoric.fileName, transaction);
	}

	// ////////////////////////// Users //////////////////////////

	public TableModelUser getUsers() {
		return users;
	}

	public void addReadUser(int userId, String name, String firstname, boolean sexe, Date birthDate, String phoneNumber,
			String studies, String mailStreet, String mailPostalCode, String mailTown, String eMail,
			boolean newLetter) {
		users.addUser(userId, name, firstname, sexe, birthDate, phoneNumber, studies, mailStreet, mailPostalCode,
				mailTown, eMail, newLetter);
	}

	public void addUser(int userId, String name, String firstname, boolean sexe, Date birthDate, String phoneNumber,
			String studies, String mailStreet, String mailPostalCode, String mailTown, String eMail,
			boolean newLetter) {
		User user = users.addUser(userId, name, firstname, sexe, birthDate, phoneNumber, studies, mailStreet,
				mailPostalCode, mailTown, eMail, newLetter);
		WriteFile.addFile(TableModelUser.fileName, user.toString());
		update();
	}

	public void deleteUser(User us) {
		users.deleteUser(us);
		update();
	}

	public int getNewId() {
		return users.getNewId();
	}

	public User getUserById(int i) {
		return users.getUserById(i);
	}

	public User getUserByName(String name) {
		return users.getUserByName(name);
	}

	public String getUserName(int id) {
		User user = users.getUserById(id);
		if (user != null) {
			return user.getName();
		} else {
			return "n/a";
		}
	}

	public String getUserFirstname(int id) {
		User user = users.getUserById(id);
		if (user != null) {
			return user.getFirstname();
		} else {
			return "n/a";
		}
	}

	public int getUserSold(int id) {
		User user = users.getUserById(id);
		if (user != null) {
			return user.getAccount();
		} else {
			return 0;
		}
	}

	public void readUserAccount(int id, int account) {
		users.setAccount(id, account);
	}
	
	public void debitUser(int id, int debit) {
		users.debitUser(id, debit);
		writeAccount();
	}

	public void creditUser(int id, int credit) {
		users.creditUser(id, credit);
		writeAccount();
	}

	public void deposit(int id, int credit) {
		creditUser(id, credit);
		creditUser(-1, credit);
		writeAccount();
		Transaction tran = new Transaction(id, 0, credit, new Date());
		tran.addArchivedProd("Dépot " + doubleFormatMoney.format((double) credit / 100) + " €", 1);
		addHistoric(tran);
		update();
	}

	public void writeAccount() {
		WriteFile.writeFile(TableModelUser.fileNameAcc, users.getAccounts());
		
	}
	
	// ////////////////////////// ... //////////////////////////

	public void update() {
		setChanged();
		notifyObservers();
	}

}
