package file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class ReadFile {

	private static String path = "caisse_BDD/";

	public static void readStock(Model model) {
		String fileName = TableModelRawMaterial.fileName;
		try {
			InputStream f = new FileInputStream(path + fileName + ".txt");
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.addReadRawMaterial(data[0], Integer.parseInt(data[1]),
						Integer.parseInt(data[2]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readPurchasedProduct(Model model) {
		String fileName = TableModelPurchasedProd.fileName;
		try {
			InputStream f = new FileInputStream(path + fileName + ".txt");
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				model.addReadPurchasedProduct(data[0],
						Integer.parseInt(data[1]),
						model.getRawMateriel(data[2]),
						Integer.parseInt(data[3]));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readSellProduct(Model model) {
		String fileName = TableModelSoldProd.fileName;
		try {
			InputStream f = new FileInputStream(path + fileName + ".txt");
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
						model.addReadMaterialToSoldProduct(data[0],
								model.getRawMateriel(rawMat[i]),
								Integer.parseInt(rawMat[i + 1]));
					}
				}
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

	public static void readHistoric(Model model) {
		String fileName = TableModelHistoric.fileName;
		try {
			InputStream f = new FileInputStream(path + fileName + ".txt");
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			String[] data;
			SimpleDateFormat sdf = Transaction.df;
			Date date;
			line = d.readLine();
			while (line != null) {
				data = line.split("; ");
				date = sdf.parse(data[2]);
				model.addReadHistoric(new Transaction(data[0], Integer
						.parseInt(data[1]), date));
				line = d.readLine();
			}
			d.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERREUR : Lecture du fichier");
			System.exit(1);
		}
	}

}
