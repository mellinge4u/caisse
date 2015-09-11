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
		User usm1 = new User("CENS", "", -1);
		User us0 = new User("...", "...", 0);
		User us1 = new User("Merkling", "Raphael", 1);
		User us46 = new User("Mellinger", "Erwan", 46);
		model.addUser(usm1);
		model.addUser(us0);
		model.addUser(us1);
		model.addUser(us46);

		model.update();
		new MainView(model);
	}

}
