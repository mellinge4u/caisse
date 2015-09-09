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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import caisse.Model;
import caisse.tools.MonetarySpinner;
import caisse.tools.CellRender;

public class ViewRestockage extends JPanel implements Observer {

	protected Model model;

	private JTable tableProd;
	private TableModelPurchasedProd listProd;
	private JButton newProduct;
	private JButton accept;
	private JButton cancel;
	private JLabel lPrix;
	private MonetarySpinner sPrixReal;
	protected CellRender cellRender;

	public ViewRestockage(final Model model, final JFrame parent) {
		this.model = model;
		model.addObserver(this);
		final JPanel panel = this;

		this.listProd = model.getPurchasedProdModel();
		tableProd = new JTable(listProd);
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
		newProduct = new JButton("Nouveau produit");
		newProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewNewPurchasedProd(model, parent);
			}
		});
		accept = new JButton("Accepter");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getTotalPriceRestock() == (int) ((double) sPrixReal
						.getValue() * 100)) {
					model.restock();
				} else {
					JOptionPane.showMessageDialog(panel,
							(Object) "Les prix ne correspondent pas",
							"Erreur de prix", 2, null);
				}
			}
		});
		cancel = new JButton("Annuler l'operation"); // TODO Accents
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearRestock();
			}
		});
		sPrixReal = new MonetarySpinner();
		lPrix = new JLabel("0.00 EUR"); // TODO symbole EUR

		JLabel lPrixAnnonce = new JLabel("Prix annonce : "); // TODO Accents
		JLabel lPrixReal = new JLabel("Prix a l'achat (reel) : "); // TODO
																	// Accents

		JPanel controlPanel = new JPanel();
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);

		controlPanel.setLayout(new GridLayout(1, 2));
		controlPanel.add(panelLeft);
		controlPanel.add(panelRight);

		// panelLeft.setLayout(new GridLayout(1, 3));
		panelLeft.add(newProduct);
		panelLeft.add(accept);
		panelLeft.add(cancel);

		panelRight.setLayout(new GridLayout(2, 2));
		panelRight.add(lPrixAnnonce);
		panelRight.add(lPrix);
		panelRight.add(lPrixReal);
		panelRight.add(sPrixReal);
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
		lPrix.setText(price + add + " EUR"); // TODO symbole EUR
		for (int i = 0; i < listProd.getColumnCount(); i++) {
			tableProd.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
	}

}
