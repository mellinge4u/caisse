package caisse.restock;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import caisse.Model;
import caisse.tools.MonetarySpinner;
import caisse.tools.CellRender;

public class ViewRestockage extends JPanel implements Observer {

	protected Model model;

	private JTable tableProd;
	private TableModelPurchasedProd listProd;
	private JLabel lPrix;
	private JCheckBox cash;
	private MonetarySpinner sPrixReal;
	protected CellRender cellRender;

	public ViewRestockage(final Model model, final JFrame parent) {
		this.model = model;
		model.addObserver(this);
		final JPanel panel = this;

		this.listProd = model.getPurchasedProdModel();
		tableProd = new JTable(listProd);
		tableProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableProd.addContainerListener(new ContainerListener() {
			@Override
			public void componentRemoved(ContainerEvent arg0) {
			}

			@Override
			public void componentAdded(ContainerEvent arg0) {
				JTable table = (JTable) arg0.getComponent();
				if (table.getSelectedColumn() != 0) {
					JTextField text = (JTextField) arg0.getChild();
					text.setText(null);
				}
			}
		});
		cellRender = new CellRender();
		for (int i = 0; i < listProd.getColumnCount(); i++) {
			tableProd.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		JScrollPane scrollPane = new JScrollPane(tableProd);
		JButton newProduct = new JButton("Ajouter un article");
		newProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewNewPurchasedProd(model, parent);
			}
		});
		JButton accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getTotalPriceRestock() == (int) ((double) sPrixReal.getValue() * 100)) {
					model.restock(cash.isSelected());
					reset();
				} else {
					JOptionPane.showMessageDialog(panel, (Object) "Les prix ne correspondent pas", "Erreur de prix", 2,
							null);
				}
			}
		});
		JButton cancel = new JButton("Annuler"); // TODO Accents
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearRestock();
				reset();
			}
		});
		JButton deleteArticle = new JButton("Supprimer un article");
		deleteArticle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableProd.getSelectedRow();
				if (row != -1) {
					String nameProd = listProd.getProd(row).getName();
					model.deletePurchasedProduct(nameProd);
					model.update();
				}
			}
		});
		JButton viewArticle = new JButton("Visualiser un article");
		viewArticle.setEnabled(false);
		sPrixReal = new MonetarySpinner(0.01);
		lPrix = new JLabel("0.00 €"); // TODO symbole EUR
		cash = new JCheckBox("Paiement liquide");

		JLabel lPrixAnnonce = new JLabel("Prix annonce : "); // TODO Accents
		JLabel lPrixReal = new JLabel("Prix a l'achat (reel) : ");
		// TODO Accents

		JPanel center = new JPanel(new BorderLayout());
		JPanel subctrl = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel panelDown = new JPanel();
		JPanel panelUp = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);

		center.add(scrollPane, BorderLayout.CENTER);
		center.add(subctrl, BorderLayout.SOUTH);

		subctrl.add(newProduct);
		subctrl.add(deleteArticle);
		subctrl.add(viewArticle);

		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		controlPanel.add(panelUp, BorderLayout.CENTER);
		controlPanel.add(panelDown, BorderLayout.SOUTH);

		panelDown.add(accept);
		panelDown.add(cancel);

		panelUp.setLayout(new GridLayout(3, 2));
		panelUp.add(lPrixAnnonce);
		panelUp.add(lPrix);
		panelUp.add(new JLabel());
		panelUp.add(cash);
		panelUp.add(lPrixReal);
		panelUp.add(sPrixReal);
	}

	public void reset() {
		cash.setSelected(false);
		sPrixReal.setValue(0.0);
	}

	@Override
	public void update(Observable o, Object arg) {
		listProd.fireTableChanged(null);
		int cent = model.getTotalPriceRestock();
		double price = (double) cent / 100;
		String add = "";
		if (cent % 10 == 0) {
			add = "0";
		}
		lPrix.setText(price + add + " €"); // TODO symbole EUR
		for (int i = 0; i < listProd.getColumnCount(); i++) {
			tableProd.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
	}

}
