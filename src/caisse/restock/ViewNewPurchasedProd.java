package caisse.restock;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	public ViewNewPurchasedProd(Model model, JFrame parent) {
		super(parent, "Nouveau produit", true);
		this.model = model;
		this.setLayout(new BorderLayout());
		this.setLayout(new GridLayout(5, 2));

		this.add(new JLabel("Nom de l'article : "));
		name = new JTextField();
		this.add(name);
		this.add(new JLabel("Prix : "));
		price = new MonetarySpinner();
		this.add(price);
		this.add(new JLabel("Produit : "));
		material = new JComboBox<RawMaterial>(model.getAllMaterialsArray());
		this.add(material);
		this.add(new JLabel("Quantite : "));
		quantity = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		this.add(quantity);
		accept = new JButton("Valider");
		accept.addActionListener(new AddPurchProdListener(model, this, name,
				price, material, quantity));
		this.add(accept);
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));
		this.add(cancel);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
