package caisse.sell;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;
import caisse.sellProcuct.SoldProduct.prodType;

public class PanelAddSoldProd extends JPanel {

	protected Model model;

	protected JList<SoldProduct> listFood;
	protected JList<SoldProduct> listDrink;
	protected JList<SoldProduct> listMisc;
	protected SoldProduct[] itemsFood;
	protected SoldProduct[] itemsDrink;
	protected SoldProduct[] itemsMisc;
	protected JButton accept;
	protected JButton cancel;

	public PanelAddSoldProd(final Model model) {
		this.model = model;
		this.setLayout(new BorderLayout());

		itemsFood = model.getAvailableSoldProdArray(prodType.FOOD);
		itemsDrink = model.getAvailableSoldProdArray(prodType.DRINK);
		itemsMisc = model.getAvailableSoldProdArray(prodType.MISC);
		listFood = new JList<SoldProduct>(
				model.getAvailableSoldProdArray(SoldProduct.prodType.FOOD));
		listFood.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listFood.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listDrink = new JList<SoldProduct>(
				model.getAvailableSoldProdArray(SoldProduct.prodType.DRINK));
		listDrink
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listDrink.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listMisc = new JList<SoldProduct>(
				model.getAvailableSoldProdArray(SoldProduct.prodType.MISC));
		listMisc.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listMisc.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setProduct();
			}
		});
		cancel = new JButton("Annuler");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetSelection();
			}
		});

		
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

	}

	public void resetSelection() {
		listFood.clearSelection();
		listDrink.clearSelection();
		listMisc.clearSelection();
	}
	
	public void setProduct() {
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
		resetSelection();
	}
}