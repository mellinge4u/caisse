package caisse;

import caisse.member.User;
import caisse.view.MainView;

public class Launcher {

	public static void main(String[] args) {
		Model model = new Model();

		// TODO Examples
		// Stock
		model.addRawMaterial("Objet");
		model.getRawMateriel("Objet").addStock(5);
		model.getRawMateriel("Objet").restock(3, 300);
		model.getRawMateriel("Objet").endRestock();

		// Restock
		model.addPurchasedProduct("Truc", 300, model.getRawMateriel("Objet"), 4);

		// Vente
		model.addSoldProduct("Bidules", 250);
		model.addMaterialToSoldProduct("Bidules", model.getRawMateriel("Objet"), 2);
		model.addSoldProduct("Autre", 250);
		model.addProductOnTransaction(model.getSoldProduct("Bidules"));

		// Users
		User us = new User("Mellinger","Erwan",43);
		model.addUser(us);

		
		new MainView(model);
	}

}
