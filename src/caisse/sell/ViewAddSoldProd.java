package caisse.sell;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.sellProcuct.SoldProduct;
import caisse.sellProcuct.SoldProduct.prodType;

public class ViewAddSoldProd extends JDialog {

	protected Model model;

	protected JList<SoldProduct> listFood;
	protected JList<SoldProduct> listDrink;
	protected JList<SoldProduct> listMisc;
	protected JButton accept;
	protected JButton cancel;

	public ViewAddSoldProd(final Model model, JFrame parent) {
		super((JFrame) parent, "Nouvel article", true);
		this.model = model;
		final Window win = this;
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		final SoldProduct[] itemsFood = model.getAvailableSoldProdArray(prodType.FOOD);
		final SoldProduct[] itemsDrink = model.getAvailableSoldProdArray(prodType.DRINK);
		final SoldProduct[] itemsMisc = model.getAvailableSoldProdArray(prodType.MISC);
		listFood = new JList<SoldProduct>(model.getAvailableSoldProdArray(SoldProduct.prodType.FOOD));
		listFood.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listFood.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listDrink = new JList<SoldProduct>(model.getAvailableSoldProdArray(SoldProduct.prodType.DRINK));
		listDrink.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listDrink.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listMisc = new JList<SoldProduct>(model.getAvailableSoldProdArray(SoldProduct.prodType.MISC));
		listMisc.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listMisc.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] valFood = listFood.getSelectedIndices();
				for (int i = 0; i < valFood.length; i++) {
					model.addProductOnCurrentTransaction(itemsFood[valFood[i]]);
				}
				int[] valDrink = listDrink.getSelectedIndices();
				for (int i = 0; i < valDrink.length; i++) {
					model.addProductOnCurrentTransaction(itemsDrink[valDrink[i]]);
				}
				int[] valMisc = listMisc.getSelectedIndices();
				for (int i = 0; i < valMisc.length; i++) {
					model.addProductOnCurrentTransaction(itemsMisc[valMisc[i]]);
				}
				win.dispose();
			}
		});
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));

		JPanel center = new JPanel();
		JPanel control = new JPanel();
		JPanel pFood = new JPanel(new BorderLayout());
		JPanel pDrink = new JPanel(new BorderLayout());
		JPanel pMisc = new JPanel(new BorderLayout());

		JLabel lFood = new JLabel("Nourriture");
		lFood.setHorizontalAlignment(JLabel.CENTER);
		JLabel lDrink = new JLabel("Boisson");
		lDrink.setHorizontalAlignment(JLabel.CENTER);
		JLabel lMisc = new JLabel("Divers");
		lMisc.setHorizontalAlignment(JLabel.CENTER);
		
		pFood.add(lFood, BorderLayout.NORTH);
		pDrink.add(lDrink, BorderLayout.NORTH);
		pMisc.add(lMisc, BorderLayout.NORTH);
		pFood.add(listFood, BorderLayout.CENTER);
		pDrink.add(listDrink, BorderLayout.CENTER);
		pMisc.add(listMisc, BorderLayout.CENTER);
		
		center.add(pFood);
		center.add(new JSeparator(SwingConstants.VERTICAL));
		center.add(pDrink);
		center.add(new JSeparator(SwingConstants.VERTICAL));
		center.add(pMisc);
		
		this.add(center, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);
		control.add(accept);
		control.add(cancel);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
