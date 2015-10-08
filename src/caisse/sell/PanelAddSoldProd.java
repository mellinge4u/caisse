package caisse.sell;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;

public class PanelAddSoldProd extends JPanel implements Observer{

	private JButton accept;
	private JButton cancel;
	private JTable food;
	private JTable drink;
	private JTable misc;
	private TableModelSelectProduct tableModelFood;
	private TableModelSelectProduct tableModelDrink;
	private TableModelSelectProduct tableModelMisc;

	public PanelAddSoldProd(JButton remove) {
		Model.getInstance().addObserver(this);
		accept = new JButton("Ajouter");
		cancel = new JButton("Désélectionner");

		tableModelFood = new TableModelSelectProduct(SoldProduct.prodType.FOOD);
		food = new JTable(tableModelFood);
		JScrollPane scrollFood = new JScrollPane(food);
		tableModelDrink = new TableModelSelectProduct(SoldProduct.prodType.DRINK);
		drink = new JTable(tableModelDrink);
		JScrollPane scrollDrink = new JScrollPane(drink);
		tableModelMisc = new TableModelSelectProduct(SoldProduct.prodType.MISC);
		misc = new JTable(tableModelMisc);
		JScrollPane scrollMisc = new JScrollPane(misc);

		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSelection();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetSelection();
			}
		});

		this.setLayout(new BorderLayout());
		JPanel ctrl = new JPanel();
		JPanel tabs = new JPanel(new GridLayout(1, 3));
		JPanel tabsName = new JPanel(new GridLayout(1, 3));
		JPanel nameFood = new JPanel(new BorderLayout());
		JPanel nameDrink = new JPanel(new BorderLayout());
		JPanel nameMisc = new JPanel(new BorderLayout());
		this.add(tabsName, BorderLayout.NORTH);
		this.add(tabs, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		tabsName.add(nameFood);
		tabsName.add(nameDrink);
		tabsName.add(nameMisc);

		JLabel lNameFood = new JLabel("Nourriture");
		lNameFood.setHorizontalAlignment(SwingConstants.CENTER);
		nameFood.add(lNameFood, BorderLayout.CENTER);
		nameFood.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);

		JLabel lNameDrink = new JLabel("Boissons");
		lNameDrink.setHorizontalAlignment(SwingConstants.CENTER);
		nameDrink.add(lNameDrink, BorderLayout.CENTER);
		nameDrink.add(new JSeparator(SwingConstants.VERTICAL),
				BorderLayout.EAST);

		JLabel lNameMisc = new JLabel("Divers");
		lNameMisc.setHorizontalAlignment(SwingConstants.CENTER);
		nameMisc.add(lNameMisc, BorderLayout.CENTER);

		tabs.add(scrollFood);
		tabs.add(scrollDrink);
		tabs.add(scrollMisc);

		ctrl.add(accept);
		ctrl.add(remove);
		ctrl.add(cancel);
	}

	private void resetSelection() {
		food.clearSelection();
		drink.clearSelection();
		misc.clearSelection();
	}

	private void setSelection() {
		Model model = Model.getInstance();
		int[] valFood = food.getSelectedRows();
		int[] valDrink = drink.getSelectedRows();
		int[] valMisc = misc.getSelectedRows();
		for (int i = 0; i < valFood.length; i++) {
			model.addProductOnCurrentTransaction(tableModelFood.getProduct(valFood[i]));
		}
		for (int i = 0; i < valDrink.length; i++) {
			model.addProductOnCurrentTransaction(tableModelDrink.getProduct(valDrink[i]));
		}
		for (int i = 0; i < valMisc.length; i++) {
			model.addProductOnCurrentTransaction(tableModelMisc.getProduct(valMisc[i]));
		}
		resetSelection();
	}

	@Override
	public void update(Observable o, Object arg) {
		tableModelFood.updateArrayList();
		tableModelDrink.updateArrayList();
		tableModelMisc.updateArrayList();
		tableModelFood.fireTableStructureChanged();
		tableModelDrink.fireTableStructureChanged();
		tableModelMisc.fireTableStructureChanged();
	}

}