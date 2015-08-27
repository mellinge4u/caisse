package caisse.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import caisse.Model;
import caisse.product.RawMaterial;

public class AddPurchProdListener implements ActionListener {

	protected Model model;
	protected JFrame frame;
	protected JTextField name;
	protected JSpinner price;
	protected JComboBox<RawMaterial> material;
	protected JSpinner quantity;

	public AddPurchProdListener(Model model, JFrame frame, JTextField name,
			JSpinner price, JComboBox<RawMaterial> material, JSpinner quantity) {
		this.model = model;
		this.frame = frame;
		this.name = name;
		this.price = price;
		this.material = material;
		this.quantity = quantity;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.addPurchasedProduct(name.getText(),
				(int) ((double) price.getValue() * 100),
				(RawMaterial) material.getSelectedItem(),
				(int) quantity.getValue());
		frame.dispose();
	}

}