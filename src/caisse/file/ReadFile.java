package caisse.file;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.tree.RowMapper;

import caisse.Model;
import caisse.historic.TableModelHistoric;
import caisse.historic.Transaction;
import caisse.restock.TableModelPurchasedProd;
import caisse.sellProcuct.SoldProduct;
import caisse.sellProcuct.TableModelSoldProd;
import caisse.stock.RawMaterial;
import caisse.stock.TableModelRawMaterial;
import caisse.user.TableModelUser;

public class ReadFile {

	public static void readAll(Model model) {
		ReadFile.readStock(model);
		ReadFile.readPurchasedProduct(model);
		ReadFile.readSellProduct(model);
		ReadFile.readHistoric(model);
		ReadFile.readUser(-1);
		ReadFile.readUser(0);
		ReadFile.readUserAccounts(model);
	}

	public static void readStock(Model model) {
		String fileName = TableModelRawMaterial.fileName;
		try {
			InputStream f = new FileInputStream(Model.repository + "/"
					+ fileName + "." + Model.extension);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;

			int quantity, alert, unitaryPrice;
			String prod;

			line = d.readLine();
			while (line != null) {
				data = line.split("; ");

				prod = data[0];
				quantity = Integer.parseInt(data[1]);
				alert = Integer.parseInt(data[2]);
				unitaryPrice = Integer.parseInt(data[3]);

				model.addReadRawMaterial(prod, quantity, alert, unitaryPrice);
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "."
					+ Model.extension);
			try {
				new File("caisse_BDD").mkdir();
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readPurchasedProduct(Model model) {
		String fileName = TableModelPurchasedProd.fileName;
		try {
			InputStream f = new FileInputStream(Model.repository + "/"
					+ fileName + "." + Model.extension);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;

			int price, num;
			String prod, store;
			RawMaterial mat;

			line = d.readLine();
			while (line != null) {
				data = line.split("; ");

				prod = data[0];
				price = Integer.parseInt(data[1]);
				mat = model.getRawMateriel(data[2]);
				num = Integer.parseInt(data[3]);
				store = data[4];

				model.addReadPurchasedProduct(prod, price, mat, num, store);
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "."
					+ Model.extension);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readSellProduct(Model model) {
		String fileName = TableModelSoldProd.fileName;
		try {
			InputStream f = new FileInputStream(Model.repository + "/"
					+ fileName + "." + Model.extension);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			String[] rawMat;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				SoldProduct.prodType type;
				if (data[2].equals("FOOD")) {
					type = SoldProduct.prodType.FOOD;
				} else if (data[2].equals("DRINK")) {
					type = SoldProduct.prodType.DRINK;
				} else {
					type = SoldProduct.prodType.MISC;
				}
				model.addReadSoldProduct(data[0], Integer.parseInt(data[1]),
						type);
				if (data.length > 3) {
					rawMat = data[3].split(" \\| ");
					for (int i = 0; i < rawMat.length; i += 2) {
						model.addReadMaterialToSoldProduct(data[0],
								model.getRawMateriel(rawMat[i]),
								Integer.parseInt(rawMat[i + 1]));
					}
				}
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "."
					+ Model.extension);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readHistoric(Model model) {
		String fileName = TableModelHistoric.fileName + Model.getActualYear();
		try {
			InputStream f = new FileInputStream(Model.repository + "/"
					+ fileName + "." + Model.extension);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			String[] sellProd;
			int id, price, cash;
			Date date;
			Transaction tran;
			Color col;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				id = Integer.parseInt(data[0]);
				price = Integer.parseInt(data[1]);
				cash = Integer.parseInt(data[2]);
				date = Model.dateFormatFull.parse(data[3]);
				if (data.length > 5) {
					switch (data[5]) {
					case "RED" : 
						col = Model.RED;
						break;
					case "GREEN" : 
						col = Model.GREEN;
						break;
					case "BLUE" : 
						col = Model.BLUE;
						break;
					case "CYAN" : 
						col = Model.CYAN;
						break;
					case "YELLOW" :
						col = Model.YELLOW;
						break;
					default :
						col = Model.LIGHT_GRAY;
						break;
					}
				} else {
					if (price > 0) {
						col = Model.GREEN;
					} else if (price == 0) {
						if (data[4].contains("Dépot")) {
							col = Model.BLUE;
						} else if (data[4].contains("Ajout")) {
							col = Model.CYAN;
						} else if (data[4].contains("Retrait")) {
							col = Model.RED;
						} else {
							col = Model.LIGHT_GRAY;
						}
					} else {
						col = Model.YELLOW;
					}
				}
				tran = new Transaction(id, price, cash, date, col);
				model.addReadHistoric(tran);
				if (data.length > 2) {
					sellProd = data[4].split(" \\| ");
					for (int i = 0; i < sellProd.length; i += 2) {
						tran.addArchivedProd(sellProd[i],
								Integer.parseInt(sellProd[i + 1]));
					}
				}
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "."
					+ Model.extension);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readUser(int addYear) {
		String fileName = TableModelUser.fileName
				+ (Model.getActualYear() + addYear);
		try {
			InputStream f = new FileInputStream(Model.repository + "/"
					+ fileName + "." + Model.extension);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				Model.getInstance().addReadUser(Integer.parseInt(data[0]),
						data[1], data[2], Boolean.parseBoolean(data[4]),
						Model.dateFormatSimple.parse(data[3]), data[11],
						data[5], data[6], data[7], data[8], data[9],
						Boolean.parseBoolean(data[10]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "."
					+ Model.extension);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readUserAccounts(Model model) {
		String fileName = TableModelUser.fileNameAcc;
		try {
			InputStream f = new FileInputStream(Model.repository + "/"
					+ fileName + "." + Model.extension);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.readUserAccount(Integer.parseInt(data[0]),
						Integer.parseInt(data[1]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "."
					+ Model.extension);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

}
