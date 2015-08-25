package caisse.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class NewRawMaterialView extends JFrame {
// TODO cette classe ne sert a rien, mais la ligne de sont constructeur permet d'affiche la bonne boite de dialogue
	
	public NewRawMaterialView() {
		String s = (String)JOptionPane.showInputDialog(this, "Nom du produit : ", "Nouveau produit", -1);
		System.out.println(s);
	}

}
