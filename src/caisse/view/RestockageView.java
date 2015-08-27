package caisse.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Currency;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import caisse.Model;
import caisse.tools.ListPurchasedProd;

public class RestockageView extends JPanel implements Observer {

	protected Model model;

	private JTable tableProd;
	private ListPurchasedProd listProd;

	private JButton newProduct;
	private JButton accept;
	private JButton cancel;
	private JLabel prixAnnonce;
	private JLabel prixAnnonce2;
	private JLabel prixReel;
	private JTextArea prixReel2;

	public RestockageView(final Model model) {
		this.model = model;
		model.addObserver(this);
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
				model.restock();
			}
		});
		cancel = new JButton("Annuler l'opération");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearRestock();
			}
		});
		prixAnnonce = new JLabel("Prix annoncé : ");
		prixAnnonce2 = new JLabel("0.00 �");
		prixReel = new JLabel("Prix à l'achat (réel) : ");
		prixReel2 = new JTextArea();
		controlPanel.setLayout(new GridLayout(1, 2));
		controlPanel.add(panelLeft);
		controlPanel.add(panelRight);
		panelLeft.setLayout(new GridLayout(1, 3));
		panelLeft.add(newProduct);
		panelLeft.add(accept);
		panelLeft.add(cancel);

		panelRight.setLayout(new GridLayout(2, 2));
		panelRight.add(prixAnnonce);
		panelRight.add(prixAnnonce2);
		panelRight.add(prixReel);
		panelRight.add(prixReel2);
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
		prixAnnonce2.setText(price + add + " �");
	}

}
