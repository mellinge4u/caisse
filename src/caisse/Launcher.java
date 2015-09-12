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
		model.addUser(new User("CENS", "", -1));
		model.addUser(new User("...", "...", 0));
		model.addUser(new User("Merkling", "Raphael", 150001));
		model.getUserById(1).setAcount(3000);
		model.addUser(new User("Daval", "Amael", 150002));
		model.addUser(new User("Matuang", "Pierre", 150003));
		model.addUser(new User("Marmier", "Herve", 150007));
		model.addUser(new User("Morgano", "Maxime", 150042));
		model.addUser(new User("Mellinger", "Erwan", 150046));

		new MainView(model);
		model.update();
	}

}
