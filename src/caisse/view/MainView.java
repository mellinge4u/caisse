package caisse.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import caisse.Model;

public class MainView extends JFrame{

	protected Model model;
	JTabbedPane jtb1 ;
	
	public MainView(Model model){
		super(" Caisse CENS ");
		this.model = model;
		jtb1 = new JTabbedPane();
		jtb1.add("Vente",new SellView(model));
		jtb1.add("Restockage",new RestockageView(model));
		jtb1.add("Stock",new StockView(model));
		jtb1.add("Utilisateur",new UserView(model));
		jtb1.add("Historique",new HistoricView(model));
		this.add(jtb1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack() ;
        setVisible(true);
	}
	
	public static void main(String[] args){
		new MainView(new Model());
	}
	
}
