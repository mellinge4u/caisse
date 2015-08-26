package caisse.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import caisse.Model;

public class StockView extends JPanel implements Observer {

	protected Model model;
	protected JButton addMaterial;
	
	public StockView(Model model) {
		this.model = model;
		model.addObserver(this);
		addMaterial = new JButton("Ajouter un produit");
		addMaterial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String)JOptionPane.showInputDialog(null, "Nom du produit : ", "Nouveau produit", -1);
				model.addRawMaterial(s);
			}
		});
		this.add(addMaterial);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
