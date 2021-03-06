package caisse.listener;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;

public class AddProductOnTransactionListener implements ActionListener {

	protected Model model;
	protected Window window;
	protected JList<SoldProduct> list;

	public AddProductOnTransactionListener(Model model, Window window,
			JList<SoldProduct> list) {
		this.model = model;
		this.window = window;
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		model.addProductOnCurrentTransaction(list.getSelectedValue());
		window.dispose();
	}

}
