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
		model.addRawMaterial("Pizza Jambon", 0, 175);
		model.addRawMaterial("Pizza Chevre", 0, 175);
		model.addRawMaterial("Pizza Chorizo", 0, 175);

		model.addRawMaterial("1/4 Baguette", 60, 10);
		model.addRawMaterial("Tranche pain de mie", 40, 4);
		model.addRawMaterial("Saucisse", 20, 10);
		model.addRawMaterial("1/4 Jambon", 72, 7);
		model.addRawMaterial("Tranche fromage", 60, 9);
		model.addRawMaterial("Ketchup", 1, 69);
		model.addRawMaterial("Mayonnaise", 1, 209);

		model.addRawMaterial("Paquet Chips", 18, 13);

		model.addRawMaterial("Schweppes Agrumes", 24, 46);
		model.addRawMaterial("Oasis Tropical", 24, 44);
		model.addRawMaterial("Orangina Jaune", 16, 49);
		model.addRawMaterial("Coca Cola", 54, 46);

		model.addRawMaterial("Snickers", 20, 31);
		model.addRawMaterial("KitKat Chunky", 15, 7);
		model.addRawMaterial("Lion", 5, 39);
		model.addRawMaterial("Mars", 5, 33);
		model.addRawMaterial("Twix", 20, 31);
	}

	public static void readPurchasedProduct(Model model) {
		model.addPurchasedProduct("Pain de mie sandwich", 95,
				model.getRawMateriel("Tranche pain de mie"), 20);
		model.addPurchasedProduct("2 1/2 baguettes préc", 42,
				model.getRawMateriel("1/4 Baguette"), 4);
		model.addPurchasedProduct("Saucisses Strasbourg", 105,
				model.getRawMateriel("Saucisse"), 10);
		model.addPurchasedProduct("Jambon de Paris", 179,
				model.getRawMateriel("1/4 Jambon"), 24);
		model.addPurchasedProduct("From. fondu Croque M", 90,
				model.getRawMateriel("Tranche fromage"), 10);
		model.addPurchasedProduct("Ketchup", 69,
				model.getRawMateriel("Ketchup"), 1);
		model.addPurchasedProduct("Bénédicta Mayonnaise", 198,
				model.getRawMateriel("Mayonnaise"), 1);

		model.addPurchasedProduct("Chips salées 6x30g", 82,
				model.getRawMateriel("Paquet Chips"), 6);

		model.addPurchasedProduct("Snickers x5", 155,
				model.getRawMateriel("Snickers"), 5);
		model.addPurchasedProduct("Nestlé KitKat Chunky", 199,
				model.getRawMateriel("KitKat Chunky"), 5);
		model.addPurchasedProduct("Nestlé Lion", 166,
				model.getRawMateriel("Lion"), 5);
		model.addPurchasedProduct("Mars x5", 138, model.getRawMateriel("Mars"),
				5);
		model.addPurchasedProduct("Twix x5", 155, model.getRawMateriel("Twix"),
				5);

		model.addPurchasedProduct("Schweppes Agrumes", 369,
				model.getRawMateriel("Schweppes Agrumes"), 8);
		model.addPurchasedProduct("Oasis Tropical", 359,
				model.getRawMateriel("Oasis Tropical"), 8);
		model.addPurchasedProduct("Orangina jaune", 399,
				model.getRawMateriel("Orangina Jaune"), 8);
		model.addPurchasedProduct("Coca Cola boîte 6x0", 279,
				model.getRawMateriel("Coca Cola"), 6);

		// model.add
	}

	public static void readSellProduct(Model model) {
		model.addSoldProduct("Pizza Jambon", 250,
				model.getRawMateriel("Pizza Jambon"));
		model.addSoldProduct("Pizza Chevre", 250,
				model.getRawMateriel("Pizza Chevre"));
		model.addSoldProduct("Pizza Chorizo", 250,
				model.getRawMateriel("Pizza Chorizo"));

		model.addSoldProduct("Croc Monsieur", 70);
		model.addMaterialToSoldProduct("Croc Monsieur",
				model.getRawMateriel("Tranche pain de mie"), 2);
		model.addMaterialToSoldProduct("Croc Monsieur",
				model.getRawMateriel("Tranche fromage"), 1);
		model.addMaterialToSoldProduct("Croc Monsieur",
				model.getRawMateriel("1/4 Jambon"), 2);
		model.addSoldProduct("Panini", 70);
		model.addMaterialToSoldProduct("Panini",
				model.getRawMateriel("1/4 Baguette"), 1);
		model.addMaterialToSoldProduct("Panini",
				model.getRawMateriel("Tranche fromage"), 1);
		model.addMaterialToSoldProduct("Panini",
				model.getRawMateriel("1/4 Jambon"), 2);
		model.addSoldProduct("Hot-Dog", 70);
		model.addMaterialToSoldProduct("Hot-Dog",
				model.getRawMateriel("1/4 Baguette"), 1);
		model.addMaterialToSoldProduct("Hot-Dog",
				model.getRawMateriel("Saucisse"), 1);

		model.addSoldProduct("Paquet Chips", 50);
		model.addMaterialToSoldProduct("Paquet Chips",
				model.getRawMateriel("Paquet Chips"), 1);

		model.addSoldProduct("Schweppes Agrumes", 70);
		model.addMaterialToSoldProduct("Schweppes Agrumes",
				model.getRawMateriel("Schweppes Agrumes"), 1);
		model.addSoldProduct("Oasis Tropical", 70);
		model.addMaterialToSoldProduct("Oasis Tropical",
				model.getRawMateriel("Oasis Tropical"), 1);
		model.addSoldProduct("Orangina Jaune", 70);
		model.addMaterialToSoldProduct("Orangina Jaune",
				model.getRawMateriel("Orangina Jaune"), 1);
		model.addSoldProduct("Coca Cola", 70);
		model.addMaterialToSoldProduct("Coca Cola",
				model.getRawMateriel("Coca Cola"), 1);

		model.addSoldProduct("Snickers", 70);
		model.addMaterialToSoldProduct("Snickers",
				model.getRawMateriel("Snickers"), 1);
		model.addSoldProduct("KitKat Chunky", 70);
		model.addMaterialToSoldProduct("KitKat Chunky",
				model.getRawMateriel("KitKat Chunky"), 1);
		model.addSoldProduct("Lion", 70);
		model.addMaterialToSoldProduct("Lion", model.getRawMateriel("Lion"), 1);
		model.addSoldProduct("Mars", 70);
		model.addMaterialToSoldProduct("Mars", model.getRawMateriel("Mars"), 1);
		model.addSoldProduct("Twix", 70);
		model.addMaterialToSoldProduct("Twix", model.getRawMateriel("Twix"), 1);

	}

}
