package caisse;

import java.text.ParseException;
import java.util.Date;

import caisse.file.ReadFile;
import caisse.user.User;
import caisse.view.MainView;

public class Launcher {

	public static void main(String[] args) {
		Model model = new Model();

		ReadFile.readStock(model);
		ReadFile.readPurchasedProduct(model);
		ReadFile.readSellProduct(model);
		ReadFile.readHistoric(model);
		ReadFile.readUser(model);
		
		//TODO Supprimer
		// Users
		/*	
		try {
			model.addUser(150001, "Merkling", "Raphael", true, Model.dateFormatSimple.parse("27/12/1991"), "03 83 44 59 91", "M1 info", "41 rue Sainte Catherine", "54000", "Nancy", "raphael.merkling@laposte.net", true);
		model.getUserById(150001).setAccount(3000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		*/
		model.addReadUser(0, "...", "...", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(150003, "Matuang", "Pierre", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(150007, "Marmier", "Herve", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(150042, "Morgano", "Maxime", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(-1, "CENS", "", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(150002, "Daval", "Amael", true, new Date(), "", "", "", "", "", "", false);
		model.addReadUser(150046, "Mellinger", "Erwan", true, new Date(), "", "", "", "", "", "", false);
		for (int i = 150010; i < 150040; i++) {
			model.addReadUser(i, "Homme", "Rand", true, new Date(), "", "", "", "", "", "", false);
		}

		new MainView(model);
		model.update();
	}

}
