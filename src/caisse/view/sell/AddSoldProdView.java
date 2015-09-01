package caisse.view.sell;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import caisse.Model;
import caisse.listener.AddProductOnTransactionListener;
import caisse.listener.CloseListener;
import caisse.product.SoldProduct;

public class AddSoldProdView extends JDialog {

	protected Model model;

	protected JList<SoldProduct> list;
	protected JButton accept;
	protected JButton cancel;

	
	public AddSoldProdView(Model model, JFrame parent) {
		super((JFrame) parent, "Nouvel article", true);
		this.model = model;
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		list = new JList<SoldProduct>(model.getAllSoldProdArray());
		this.add(list, BorderLayout.CENTER);

		JPanel control = new JPanel();
		control.setLayout(new GridLayout(1, 2));
		accept = new JButton("Valider");
		accept.addActionListener(new AddProductOnTransactionListener(model, this, list));
		control.add(accept);
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));
		control.add(cancel);
		this.add(control, BorderLayout.SOUTH);
		
		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
