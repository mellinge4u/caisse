package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainView extends JFrame{

	JTabbedPane jtb1 ;

	
	public MainView(){
		super(" Caisse CENS ");
		jtb1 = new JTabbedPane();
		jtb1.add("Vente",null);
		jtb1.add("Restockage",null);
		jtb1.add("Stock",null);
		jtb1.add("Utilisateur",null);
		jtb1.add("Historique",null);
		this.add(jtb1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack() ;
        setVisible(true);
	}
	
	public static void main(String[] args){
		new MainView();
	}
	
}
