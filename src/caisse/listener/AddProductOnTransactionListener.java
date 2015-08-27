package caisse.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;

import caisse.Model;
import caisse.product.SoldProduct;

public class AddProductOnTransactionListener implements ActionListener {

	protected Model model;
	protected JFrame frame;
	protected JList<SoldProduct> list;
	
	public AddProductOnTransactionListener(Model model, JFrame frame, JList<SoldProduct> list) {
		this.model = model;
		this.frame = frame;
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		model.addProductOnTransaction(list.getSelectedValue());
		frame.dispose();
	}

}
