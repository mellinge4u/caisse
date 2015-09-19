package caisse.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import caisse.Model;
import caisse.historic.TableModelHistoric;
import caisse.historic.Transaction;
import caisse.restock.TableModelPurchasedProd;
import caisse.sellProcuct.TableModelSoldProd;
import caisse.stock.TableModelRawMaterial;
import caisse.user.TableModelUser;

public class ReadFile {

	public static void readAll(Model model) {
		ReadFile.readStock(model);
		ReadFile.readPurchasedProduct(model);
		ReadFile.readSellProduct(model);
		ReadFile.readHistoric(model);
		ReadFile.readUser(model);
		ReadFile.readUserAccounts(model);
	}
	
	public static void readStock(Model model) {
		String fileName = TableModelRawMaterial.fileName;
		try {
			InputStream f = new FileInputStream(Model.repository + "/" + fileName + "." + Model.extention);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.addReadRawMaterial(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
						Integer.parseInt(data[3]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "." + Model.extention);
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

	public static void readPurchasedProduct(Model model) {
		String fileName = TableModelPurchasedProd.fileName;
		try {
			InputStream f = new FileInputStream(Model.repository + "/" + fileName + "." + Model.extention);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.addReadPurchasedProduct(data[0], Integer.parseInt(data[1]), model.getRawMateriel(data[2]),
						Integer.parseInt(data[3]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "." + Model.extention);
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
			InputStream f = new FileInputStream(Model.repository + "/" + fileName + "." + Model.extention);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			String[] rawMat;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.addReadSoldProduct(data[0], Integer.parseInt(data[1]));
				if (data.length > 2) {
					rawMat = data[2].split(" \\| ");
					for (int i = 0; i < rawMat.length; i += 2) {
						model.addReadMaterialToSoldProduct(data[0], model.getRawMateriel(rawMat[i]),
								Integer.parseInt(rawMat[i + 1]));
					}
				}
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "." + Model.extention);
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
		String fileName = TableModelHistoric.fileName;
		try {
			InputStream f = new FileInputStream(Model.repository + "/" + fileName + "." + Model.extention);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			String[] sellProd;
			Date date;
			Transaction tran;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				date = Model.dateFormatFull.parse(data[3]);
				tran = new Transaction(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]),
						date);
				model.addReadHistoric(tran);
				if (data.length > 2) {
					sellProd = data[4].split(" \\| ");
					for (int i = 0; i < sellProd.length; i += 2) {
						tran.addArchivedProd(sellProd[i], Integer.parseInt(sellProd[i + 1]));
					}
				}
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "." + Model.extention);
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

	public static void readUser(Model model) {
		String fileName = TableModelUser.fileName;
		try {
			InputStream f = new FileInputStream(Model.repository + "/" + fileName + "." + Model.extention);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.addReadUser(Integer.parseInt(data[0]), data[1], data[2], Boolean.parseBoolean(data[4]),
						Model.dateFormatSimple.parse(data[3]), data[11], data[5], data[6], data[7], data[8], data[9],
						Boolean.parseBoolean(data[10]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "." + Model.extention);
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
			InputStream f = new FileInputStream(Model.repository + "/" + fileName + "." + Model.extention);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.readUserAccount(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			File file = new File(Model.repository + "/" + fileName + "." + Model.extention);
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
