package caisse.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import caisse.Model;
import caisse.listener.AddPurchProdListener;
import caisse.listener.CloseListener;
import caisse.product.RawMaterial;
import caisse.tools.MonetarySpinnerModel;

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
		price = new JSpinner(new MonetarySpinnerModel());
		this.add(price);
		this.add(new JLabel("Produit : "));
		material = new JComboBox<RawMaterial>(model.getAllMaterialsArray());
		this.add(material);
		this.add(new JLabel("Quantite : "));
		quantity = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		this.add(quantity);
		accept = new JButton("Valider");
		accept.addActionListener(new AddPurchProdListener(model, this, name, price, material, quantity));
		this.add(accept);
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));
		this.add(cancel);
		
		pack();
		setVisible(true);
	}

}
