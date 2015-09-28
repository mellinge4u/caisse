package caisse.sell;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

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

	public PanelAddSoldProd(final Model model, JButton remove) {
		this.model = model;
		this.setLayout(new BorderLayout());

		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setProduct();
				}
			}
		};
		
		itemsFood = model.getAvailableSoldProdArray(prodType.FOOD);
		itemsDrink = model.getAvailableSoldProdArray(prodType.DRINK);
		itemsMisc = model.getAvailableSoldProdArray(prodType.MISC);
		listFood = new JList<SoldProduct>(
				model.getAvailableSoldProdArray(SoldProduct.prodType.FOOD));
		listFood.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listFood.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listFood.addMouseListener(mouseListener);
		listDrink = new JList<SoldProduct>(
				model.getAvailableSoldProdArray(SoldProduct.prodType.DRINK));
		listDrink
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listDrink.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listDrink.addMouseListener(mouseListener);
		listMisc = new JList<SoldProduct>(
				model.getAvailableSoldProdArray(SoldProduct.prodType.MISC));
		listMisc.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listMisc.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listMisc.addMouseListener(mouseListener);

		accept = new JButton("Ajouter");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setProduct();
			}
		});
		cancel = new JButton("Deselectionner");
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
		pFood.add(listFood, BorderLayout.CENTER);
		pDrink.add(lDrink, BorderLayout.NORTH);
		pDrink.add(listDrink, BorderLayout.CENTER);
		pMisc.add(lMisc, BorderLayout.NORTH);
		pMisc.add(listMisc, BorderLayout.CENTER);

		center.add(pFood, BorderLayout.WEST);
		center.add(pDrink, BorderLayout.CENTER);
		center.add(pMisc, BorderLayout.EAST);

		this.add(center, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);
		control.add(accept);
		control.add(remove);
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