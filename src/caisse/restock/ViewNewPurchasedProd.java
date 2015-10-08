package caisse.restock;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import caisse.Model;
import caisse.listener.AddPurchProdListener;
import caisse.listener.CloseListener;
import caisse.stock.RawMaterial;
import caisse.tools.MonetarySpinner;

public class ViewNewPurchasedProd extends JDialog {

	protected Model model;

	protected JTextField name;
	protected MonetarySpinner price;
	protected JComboBox<RawMaterial> material;
	protected JSpinner quantity;
	protected JButton accept;
	protected JButton cancel;
	protected JTextField store;

	public ViewNewPurchasedProd(Model model, JFrame parent) {
		super(parent, "Nouveau produit", true);
		this.model = model;
		
		name = new JTextField();
		price = new MonetarySpinner(0.01);
		material = new JComboBox<RawMaterial>(model
				.getAllMarerialsArray());
		quantity = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		store = new JTextField();
		accept = new JButton("Valider");
		accept.addActionListener(new AddPurchProdListener(model, this, name,
				price, material, quantity, store));
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));

		this.setLayout(new BorderLayout());
		JPanel center = new JPanel(new GridLayout(5, 2));
		JPanel ctrl = new JPanel();
		
		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		center.add(new JLabel("Nom de l'article : "));
		center.add(name);
		center.add(new JLabel("Prix : "));
		center.add(price);
		center.add(new JLabel("Produit : "));
		center.add(material);
		center.add(new JLabel("Quantite : "));
		center.add(quantity);
		center.add(new JLabel("Magasin : "));
		center.add(store);
		ctrl.add(accept);
		ctrl.add(cancel);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
