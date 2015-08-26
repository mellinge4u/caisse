package caisse.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import caisse.Model;
import caisse.product.RawMaterial;

public class NewPurchasedProdView extends JFrame {

	protected Model model;

	protected JTextField name;
	protected JSpinner price;
	protected JComboBox<RawMaterial> material;
	protected JSpinner quantity;
	protected JButton accept;
	protected JButton cancel;

	public NewPurchasedProdView(Model model) {
		super(" Caisse CENS ");
		this.model = model;
		this.setLayout(new GridLayout(5, 2));

		this.add(new JLabel("Nom de l'article : "));
		name = new JTextField();
		this.add(name);
		this.add(new JLabel("Prix : "));
		price = new JSpinner();
		this.add(price);
		this.add(new JLabel("Produit : "));
		material = new JComboBox<RawMaterial>(); // TODO Remplir la liste
		this.add(material);
		this.add(new JLabel("Quantite : "));
		quantity = new JSpinner();
		this.add(quantity);
		accept = new JButton("Valider");
		this.add(accept);
		cancel = new JButton("Annuler");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Fermer la fenetre
			}
		});
		this.add(cancel);
		
		pack();
		setVisible(true);
	}

}
