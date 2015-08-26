package caisse.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainView extends JFrame{

	JTabbedPane jtb1 ;

	
	public MainView(){
		super(" Caisse CENS ");
		jtb1 = new JTabbedPane();
		jtb1.add("Vente",new SellView());
		jtb1.add("Restockage",new RestockageView());
		jtb1.add("Stock",new StockView());
		jtb1.add("Utilisateur",new UserView());
		jtb1.add("Historique",new HistoricView());
		this.add(jtb1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack() ;
        setVisible(true);
	}
	
	public static void main(String[] args){
		new MainView();
	}
	
}
