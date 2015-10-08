package caisse;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.sun.javafx.binding.SelectBinding.AsBoolean;

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

/**
 * The Model class is the center of all data of the program. This class is a
 * singleton, use the function getInstance().
 * 
 * @author Raph
 * @version 1.0
 * 
 */
public class Model extends Observable {

	private static Model model = new Model();

	/**
	 * {@link SimpleDateFormat}, only date, French representation :
	 * "dd/MM/yyyy".
	 */
	public static SimpleDateFormat dateFormatSimple = new SimpleDateFormat(
			"dd/MM/yyyy");
	/**
	 * {@link SimpleDateFormat}, date + hour, French representation :
	 * "dd/MM/yyyy". HH:mm:ss
	 */
	public static SimpleDateFormat dateFormatFull = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	/**
	 * {@link DecimalFormat} with 2 decimal digits.
	 */
	public static DecimalFormat doubleFormatMoney = new DecimalFormat("#0.00");

	public static String repository = "caisse_BDD";
	public static String extention = "cens";

	private TableModelRawMaterial listeRawMaterial;
	private TableModelPurchasedProd purchasedProd;
	private TableModelSoldProd soldProd;
	private TableModelCurrentTransaction transaction;
	private TableModelUser users;
	private TableModelHistoric historic;

	/**
	 * Model constructor. Create all {@link AbstractTableModel} needed.
	 * 
	 * @see TableModelRawMaterial
	 * @see TableModelPurchasedProd
	 * @see TableModelSoldProd
	 * @see TableModelCurrentTransaction
	 * @see TableModelUser
	 * @see TableModelHistoric
	 */
	private Model() {
		this.listeRawMaterial = new TableModelRawMaterial(this);
		this.purchasedProd = new TableModelPurchasedProd(this);
		this.soldProd = new TableModelSoldProd();
		this.transaction = new TableModelCurrentTransaction(this);
		this.users = new TableModelUser();
		this.historic = new TableModelHistoric(this);
	}

	/**
	 * Return the instance of the class {@link Model}.
	 * 
	 * @return The instance of the class {@link Model}
	 * @see Model
	 */
	public static Model getInstance() {
		return model;
	}

	// ////////////////////////// Raw Material //////////////////////////

	/**
	 * Create a new {@link RawMaterial} and add it to the collection. This
	 * function send a update request to all the {@link Observer} and rewrite
	 * the RawMaterial file. This call
	 * {@link TableModelRawMaterial#addRawMaterial(String)},
	 * {@link #writeStock()} and {@link #update()} methods.
	 * 
	 * @param material
	 *            {@link String} - The name of the new material.
	 * 
	 * @see RawMaterial
	 * @see Observer
	 */
	public void addRawMaterial(String material) {
		listeRawMaterial.addRawMaterial(material);
		writeStock();
		update();
	}

	/**
	 * Create a new {@link RawMaterial} and add it to the collection. This
	 * should be used by a file reader. This call
	 * {@link TableModelRawMaterial#addRawMaterial(String, int, int, int)}
	 * method.
	 * 
	 * @param material
	 *            {@link String} - The name of the new material.
	 * @param quantity
	 *            int - The quantity of this material in the stock.
	 * @param alert
	 *            int - The alert level.
	 * @param price
	 *            int - The unite price of the material
	 * 
	 * @see RawMaterial
	 */
	public void addReadRawMaterial(String material, int quantity, int alert,
			int price) {
		listeRawMaterial.addRawMaterial(material, quantity, alert, price);
	}

	/**
	 * Get the object {@link RawMaterial} corresponding to the {@link String}
	 * product. This call the
	 * {@link TableModelRawMaterial#getRawMaterial(String)} method.
	 * 
	 * @param product
	 *            {@link String} - The name of Object {@link RawMaterial} to
	 *            return
	 * @return The instance of {@link RawMaterial} corresponding to the name
	 * 
	 * @see RawMaterial
	 */
	public RawMaterial getRawMateriel(String product) {
		return listeRawMaterial.getRawMaterial(product);
	}

	/**
	 * Get all {@link RawMaterial} in a {@link ArrayList}. This call the
	 * {@link TableModelRawMaterial#getAllMaterials()} method.
	 * 
	 * @return An {@link ArrayList} contening all {@link RawMaterial}
	 * 
	 * @see RawMaterial
	 * @see ArrayList
	 */
	public ArrayList<RawMaterial> getAllMarerials() {
		return listeRawMaterial.getAllMaterials();
	}

	/**
	 * Get all {@link RawMaterial} in a {@link ArrayList}. This call the
	 * {@link TableModelRawMaterial#getAllMaterials()} method.
	 * 
	 * @return An {@link ArrayList} contening all {@link RawMaterial}
	 * 
	 * @see RawMaterial
	 * @see ArrayList
	 */
	public ArrayList<RawMaterial> getAllMarerialsArray() {
		return listeRawMaterial.getAllMaterials();
	}

	/**
	 * Get a {@link AbstractTableModel} showing all {@link RawMaterial}.
	 * 
	 * @return An {@link AbstractTableModel} for all {@link RawMaterial}
	 * 
	 * @see AbstractTableModel
	 * @see RawMaterial
	 */
	public TableModelRawMaterial getTableModelRawMaterial() {
		return listeRawMaterial;
	}

	/**
	 * This method add the {@link Transaction} in the historic and rewrite the
	 * stock file. This call {@link #addHistoric(Transaction)} and
	 * {@link #writeStock()} methods.
	 * 
	 * @param transaction
	 *            {@link Transaction} - the transaction to archive
	 * 
	 * @see Transaction
	 */
	public void validTransaction(Transaction transaction) {
		addHistoric(transaction);
		writeStock();
	}

	/**
	 * Rewrite all the stock file using the
	 * {@link TableModelRawMaterial#writeData()} method.
	 * 
	 */
	private void writeStock() {
		listeRawMaterial.writeData();
	}

	// ////////////////////////// Purchased Product //////////////////////////

	public void addPurchasedProduct(String product, int price,
			RawMaterial material, int number, String store) {
		purchasedProd.addPurchasedProduct(product, price, material, number,
				store);
		writePurchasedProduct();
		update();
	}

	public void addReadPurchasedProduct(String product, int price,
			RawMaterial material, int number, String store) {
		purchasedProd.addPurchasedProduct(product, price, material, number,
				store);
	}

	public void deletePurchasedProduct(String prod) {
		purchasedProd.removePurchasedProduct(prod);
		writePurchasedProduct();
		update();
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
		listeRawMaterial.endRestock();
		writeStock();
		update();
	}

	public void writePurchasedProduct() {
		purchasedProd.writeData();
	}

	// ////////////////////////// Sold Product //////////////////////////

	public void addSoldProduct(String product, int salePrice,
			SoldProduct.prodType type) {
		soldProd.addSoldProduct(product, salePrice, type);
		update();
	}

	public void addSoldProduct(String product, int salePrice,
			RawMaterial material, SoldProduct.prodType type) {
		soldProd.addSoldProduct(product, salePrice, type);
		soldProd.addMaterial(product, material, 1);
		update();
	}

	public void deleteSoldProduct(String prod) {
		soldProd.removeSoldProduct(prod);
		writeSoldProduct();
		update();
	}

	public void addReadSoldProduct(String product, int salePrice,
			SoldProduct.prodType type) {
		soldProd.addSoldProduct(product, salePrice, type);
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

	public SoldProduct[] getAvailableSoldProdArray(SoldProduct.prodType type) {
		ArrayList<SoldProduct> list = getAllSoldProd();
		ArrayList<SoldProduct> newList = new ArrayList<SoldProduct>();
		for (SoldProduct prod : list) {
			if (prod.getQuantity() > 0 && prod.getType() == type) {
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

	public ArrayList<SoldProduct> getAvailableSoldProd(SoldProduct.prodType type) {
		ArrayList<SoldProduct> list = getAllSoldProd();
		ArrayList<SoldProduct> newList = new ArrayList<SoldProduct>();
		for (SoldProduct prod : list) {
			if (prod.getQuantity() > 0 && prod.getType() == type) {
				newList.add(prod);
			}
		}
		return newList;
	}

	public ArrayList<SoldProduct> getAllSoldProd() {
		return soldProd.getAllProducts();
	}

	public void addMaterialToSoldProduct(String product, RawMaterial material,
			int quantity) {
		soldProd.addMaterial(product, material, quantity);
		update();
	}

	public void addReadMaterialToSoldProduct(String product,
			RawMaterial material, int quantity) {
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
		WriteFile.addFile(TableModelHistoric.fileName + getActualYear(),
				transaction);
	}

	// ////////////////////////// Users //////////////////////////

	public TableModelUser getUsers() {
		return users;
	}

	public void addReadUser(int userId, String name, String firstname,
			boolean sexe, Date birthDate, String phoneNumber, String studies,
			String mailStreet, String mailPostalCode, String mailTown,
			String eMail, boolean newLetter) {
		users.addUser(userId, name, firstname, sexe, birthDate, phoneNumber,
				studies, mailStreet, mailPostalCode, mailTown, eMail, newLetter);
	}

	public void addUser(int userId, String name, String firstname,
			boolean sexe, Date birthDate, String phoneNumber, String studies,
			String mailStreet, String mailPostalCode, String mailTown,
			String eMail, boolean newLetter) {
		User user = users.addUser(userId, name, firstname, sexe, birthDate,
				phoneNumber, studies, mailStreet, mailPostalCode, mailTown,
				eMail, newLetter);
		WriteFile.addFile(TableModelUser.fileName + getActualYear(),
				user.toString());
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
		if (id != 0) {
			users.debitUser(id, debit);
			writeAccount();
		}
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
		tran.addArchivedProd(
				"Dépot " + doubleFormatMoney.format((double) credit / 100)
						+ " €", 1);
		addHistoric(tran);
		update();
	}

	public String getMailList() {
		return users.getMailList();
	}

	public void writeAccount() {
		WriteFile.writeFile(TableModelUser.fileNameAcc, users.getAccounts());
	}

	// ////////////////////////// ... //////////////////////////

	static public int getActualYear() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR) - 2000;
		if (month < 8) {
			year--;
		}
		return year;
	}

	public void update() {
		setChanged();
		notifyObservers();
	}

}
