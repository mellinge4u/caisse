package caisse;

import caisse.user.User;
import caisse.view.MainView;
import file.ReadFile;

public class Launcher {

	public static void main(String[] args) {
		Model model = new Model();

		ReadFile.readStock(model);
		ReadFile.readPurchasedProduct(model);
		ReadFile.readSellProduct(model);
		ReadFile.readHistoric(model);

		/*
		 * model.writeStock(); model.writePurchasedProduct();
		 * model.writeSoldProduct();
		 */

		// Users
		User us = new User("Mellinger", "Erwan", 43);
		model.addUser(us);

		model.update();
		new MainView(model);
	}

}
