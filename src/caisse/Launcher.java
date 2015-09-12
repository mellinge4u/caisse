package caisse;

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
		model.addUser("...", "...", 0);
		model.addUser("Matuang", "Pierre", 150003);
		model.addUser("Marmier", "Herve", 150007);
		model.addUser("Morgano", "Maxime", 150042);
		model.addUser("CENS", "", -1);
		model.addUser("Merkling", "Raphael", 150001);
		model.addUser("Daval", "Amael", 150002);
		model.addUser("Mellinger", "Erwan", 150046);
		model.getUserById(150001).setAcount(3000);
		for (int i = 150010; i < 150040; i++) {
			model.addUser("Homme", "Rand", i);
		}

		new MainView(model);
		model.update();
	}

}
