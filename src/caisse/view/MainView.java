package caisse.view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import caisse.Model;
import caisse.historic.ViewHistoric;
import caisse.restock.ViewRestockage;
import caisse.sell.ViewSell;
import caisse.sellProcuct.ViewSellProcuct;
import caisse.stock.ViewStock;
import caisse.user.ViewUser;

public class MainView extends JFrame {

	protected Model model;
	JTabbedPane jtb1;

	public MainView(Model model) {
		super(" Caisse CENS ");
		this.model = model;
		jtb1 = new JTabbedPane();
		jtb1.add("Vente", new ViewSell(model, this));
		jtb1.add("Article en Vente", new ViewSellProcuct(model, this));
		jtb1.add("Restockage", new ViewRestockage(model, this));
		jtb1.add("Stock", new ViewStock(model));
		jtb1.add("Utilisateur", new ViewUser(model, this));
		jtb1.add("Historique", new ViewHistoric(model));
		this.add(jtb1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainView(new Model());
	}

}
