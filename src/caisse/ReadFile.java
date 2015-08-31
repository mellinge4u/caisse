package caisse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFile {

	public static String readFile(String filename) {

		StringBuilder donnee = new StringBuilder();

		try {
			InputStream f = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(f);
			BufferedReader d = new BufferedReader(isr);
			String line;
			line = d.readLine();
			while (line != null) {
				donnee.append(line + '\n');
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
		return donnee.toString();
	}

	public static void readStock(Model model) {
		model.addRawMaterial("Pizza Jambon");
		model.addRawMaterial("Pizza Chevre");
		model.addRawMaterial("Pizza Chorizo");

		model.addRawMaterial("Tranche de pain", 40);
		model.addRawMaterial("1/4 Baguette", 60);
		model.addRawMaterial("Tranche frommage", 60);
		model.addRawMaterial("Saussice", 30);
		model.addRawMaterial("1/4 Jambon", 48);
		model.addRawMaterial("Ketchup", 1);
		model.addRawMaterial("Mayonnaise", 1);

		model.addRawMaterial("Chips", 18);

		model.addRawMaterial("Coca Cola", 54);
		model.addRawMaterial("Oasis", 24);
		model.addRawMaterial("Schweeps", 24);
		model.addRawMaterial("Orangina", 16);

		model.addRawMaterial("Snickers", 25);
		model.addRawMaterial("Twix", 25);
		model.addRawMaterial("Mars", 5);
		model.addRawMaterial("Lion", 5);
		model.addRawMaterial("Kitkat", 15);
	}

	public static void readPurchasedProduct(Model model) {
		model.addPurchasedProduct("Pizza Jambon", 175,
				model.getRawMateriel("Pizza Jambon"), 1);
		model.addPurchasedProduct("Pizza Chevre", 185,
				model.getRawMateriel("Pizza Chevre"), 1);
		model.addPurchasedProduct("Pizza Chorizo", 185,
				model.getRawMateriel("Pizza Chorizo"), 1);

		model.addPurchasedProduct("Pain de mie", 50,
				model.getRawMateriel("Tranche de pain"), 20);
		model.addPurchasedProduct("2 1/2 Baguette prec.", 50,
				model.getRawMateriel("1/4 Baguette"), 4);
		model.addPurchasedProduct("Frommage Croc M", 10,
				model.getRawMateriel("Tranche frommage"), 10);
		model.addPurchasedProduct("Scaussices", 100,
				model.getRawMateriel("Saussice"), 10);
		model.addPurchasedProduct("Jambon", 200,
				model.getRawMateriel("1/4 Jambon"), 24);
		model.addPurchasedProduct("Ketchup", 70,
				model.getRawMateriel("Ketchup"), 1);
		model.addPurchasedProduct("product", 180,
				model.getRawMateriel("Mayonnaise"), 1);

		model.addPurchasedProduct("Chips x6", 120,
				model.getRawMateriel("Chips"), 6);

		model.addPurchasedProduct("Coca Cola x6", 230,
				model.getRawMateriel("Coca Cola"), 6);
		model.addPurchasedProduct("Oasis", 270, model.getRawMateriel("Oasis"),
				8);
		model.addPurchasedProduct("Scheeps", 290,
				model.getRawMateriel("Schweeps"), 8);
		model.addPurchasedProduct("Orangina", 299,
				model.getRawMateriel("Orangina"), 8);

		model.addPurchasedProduct("Snickers x5", 200,
				model.getRawMateriel("Snickers"), 5);
		model.addPurchasedProduct("Twix x5", 200, model.getRawMateriel("Twix"),
				5);
		model.addPurchasedProduct("Mars x5", 200, model.getRawMateriel("Mars"),
				5);
		model.addPurchasedProduct("Lion x5", 200, model.getRawMateriel("Lion"),
				5);
		model.addPurchasedProduct("Kitkat x5", 200,
				model.getRawMateriel("Kitkat"), 5);
	}

	public static void readSellProduct(Model model) {
		model.addSoldProduct("Pizza Jambon", 250,
				model.getRawMateriel("Pizza Jambon"));
		model.addSoldProduct("Pizza Chevre", 250,
				model.getRawMateriel("Pizza Chevre"));
		model.addSoldProduct("Pizza Chorizo", 250,
				model.getRawMateriel("Pizza Chorizo"));

		model.addSoldProduct("Croc Monsieur", 50);
		model.addMaterialToSoldProduct("Croc Monsieur",
				model.getRawMateriel("Tranche de pain"), 2);
		model.addMaterialToSoldProduct("Croc Monsieur",
				model.getRawMateriel("1/4 Jambon"), 2);
		model.addMaterialToSoldProduct("Croc Monsieur",
				model.getRawMateriel("Tranche frommage"), 1);
		model.addSoldProduct("Panini", 50);
		model.addMaterialToSoldProduct("Panini",
				model.getRawMateriel("1/4 Baguette"), 1);
		model.addMaterialToSoldProduct("Panini",
				model.getRawMateriel("1/4 Jambon"), 2);
		model.addMaterialToSoldProduct("Panini",
				model.getRawMateriel("Tranche frommage"), 1);
		model.addSoldProduct("Hot-Dog", 50);
		model.addMaterialToSoldProduct("Hot-Dog",
				model.getRawMateriel("1/4 Baguette"), 1);
		model.addMaterialToSoldProduct("Hot-Dog",
				model.getRawMateriel("Saussice"), 1);

		model.addSoldProduct("Chips", 50, model.getRawMateriel("Chips"));

		model.addSoldProduct("Coca Cola", 50, model.getRawMateriel("Coca Cola"));
		model.addSoldProduct("Oasis", 50, model.getRawMateriel("Oasis"));
		model.addSoldProduct("Schweeps", 50, model.getRawMateriel("Schweeps"));
		model.addSoldProduct("Orangina", 50, model.getRawMateriel("Orangina"));

		model.addSoldProduct("Snickers", 50, model.getRawMateriel("Snickers"));
		model.addSoldProduct("Twix", 50, model.getRawMateriel("Twix"));
		model.addSoldProduct("Mars", 50, model.getRawMateriel("Mars"));
		model.addSoldProduct("Lion", 50, model.getRawMateriel("Lion"));
		model.addSoldProduct("Kitkat", 50, model.getRawMateriel("Kitkat"));

	}

}
