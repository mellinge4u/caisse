package caisse.view.Restock;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Currency;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import caisse.Model;
import caisse.tools.MonetarySpinner;
import caisse.tools.ListPurchasedProd;
import caisse.tools.MonetarySpinnerModel;

public class RestockageView extends JPanel implements Observer {

	protected Model model;

	private JTable tableProd;
	private ListPurchasedProd listProd;

	private JButton newProduct;
	private JButton accept;
	private JButton cancel;
	private JLabel lPrixAnnonce;
	private JLabel lPrix;
	private JLabel lPrixReal;
	private MonetarySpinner sPrixReal;

	public RestockageView(final Model model) {
		this.model = model;
		model.addObserver(this);
		final JPanel panel = this;
		this.setLayout(new BorderLayout());
		this.listProd = model.getPurchasedProdModel();

		tableProd = new JTable(listProd);
		JScrollPane scrollPane = new JScrollPane(tableProd);
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		this.add(controlPanel, BorderLayout.SOUTH);
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		newProduct = new JButton("Nouveau produit");
		newProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new NewPurchasedProdView(model);
			}
		});
		accept = new JButton("Accepter");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getTotalPriceRestock() == (int) ((double) sPrixReal.getValue() * 100)) {
					model.restock();
				} else {
					JOptionPane.showMessageDialog(panel, (Object) "Les prix ne correspondent pas", "Erreur de prix", 2, null);
				}
			}
		});
		cancel = new JButton("Annuler l'op�ration");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearRestock();
			}
		});
		lPrixAnnonce = new JLabel("Prix annonc� : ");
		lPrix = new JLabel("0.00 �");
		lPrixReal = new JLabel("Prix � l'achat (r�el) : ");
		sPrixReal = new MonetarySpinner();
		controlPanel.setLayout(new GridLayout(1, 2));
		controlPanel.add(panelLeft);
		controlPanel.add(panelRight);
		panelLeft.setLayout(new GridLayout(1, 3));
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
		lPrix.setText(price + add + " �");
	}

}
