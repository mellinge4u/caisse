package caisse;

import caisse.member.User;
import caisse.view.MainView;

public class Launcher {

	public static void main(String[] args) {
		Model model = new Model();

		ReadFile.readStock(model);
		// ReadFile.readStock2(model);
		ReadFile.readPurchasedProduct(model);
		ReadFile.readSellProduct(model);
		
		model.writeStock();
		
		// Users
		User us = new User("Mellinger","Erwan",43);
		model.addUser(us);

		
		new MainView(model);
	}

}
