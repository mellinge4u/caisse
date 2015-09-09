package caisse.listener;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import caisse.Model;
import caisse.stock.RawMaterial;

public class AddPurchProdListener implements ActionListener {

	protected Model model;
	protected Window window;
	protected JTextField name;
	protected JSpinner price;
	protected JComboBox<RawMaterial> material;
	protected JSpinner quantity;

	public AddPurchProdListener(Model model, Window window, JTextField name,
			JSpinner price, JComboBox<RawMaterial> material, JSpinner quantity) {
		this.model = model;
		this.window = window;
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
		window.dispose();
	}

}
