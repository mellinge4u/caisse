package caisse;

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

		// Users
		model.addUser(150001, "Merkling", "Raphael", true, new Date(), "M1 info", "41 rue Sainte Catherine", "54000", "Nancy", "raphael.merkling@laposte.net", true);
		model.addUser(0, "...", "...", true, new Date(), "", "", "", "", "", false);
		model.addUser(150003, "Matuang", "Pierre", true, new Date(), "", "", "", "", "", false);
		model.addUser(150007, "Marmier", "Herve", true, new Date(), "", "", "", "", "", false);
		model.addUser(150042, "Morgano", "Maxime", true, new Date(), "", "", "", "", "", false);
		model.addUser(-1, "CENS", "", true, new Date(), "", "", "", "", "", false);
		model.addUser(150002, "Daval", "Amael", true, new Date(), "", "", "", "", "", false);
		model.addUser(150046, "Mellinger", "Erwan", true, new Date(), "", "", "", "", "", false);
		model.getUserById(150001).setAccount(3000);
		for (int i = 150010; i < 150040; i++) {
			model.addUser(i, "Homme", "Rand", true, new Date(), "", "", "", "", "", false);
		}

		new MainView(model);
		model.update();
	}

}
