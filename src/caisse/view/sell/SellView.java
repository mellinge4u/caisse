package caisse.view.sell;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;

import caisse.Model;
import caisse.product.SoldProduct;
import caisse.tools.CurrentTransaction;
import caisse.view.sellProcuct.NewSellProductView;

public class SellView extends JPanel implements Observer {

	protected Model model;

	private JPanel panelLeft;
	// TODO ajouter les items, ex: private Item it ...
	// TODO ajouter pour chaques items les éléments correxpondants
	// TODO private JButton ajouter pour chaque item

	private JTable tableTrans;
	private CurrentTransaction transaction;
	private JButton addProduct;
	private JButton creatProduct;

	private JPanel panelRight;
	private JLabel membre;
	private JComboBox<String> jcb;
	private JLabel payment;
	private JLabel vide;
	private JLabel jLiquide;
	private JCheckBox cash;
	private JLabel jCompte;
	private JCheckBox compte;
	private JLabel jMixte;
	private JCheckBox mixte;
	private JLabel vide2;
	private JLabel jLiquide2;
	private JSpinner cashLiquide;
	private JLabel solde;
	private JLabel solde2;

	public SellView(final Model model) {
		this.model = model;
		model.addObserver(this);
		this.panelLeft = new JPanel();
		panelLeft.setLayout(new BorderLayout());

		transaction = model.getCurrentTransaction();

		// TODO supprimer les examples
		// Examples


		tableTrans = new JTable(transaction);
		JScrollPane scrollPane = new JScrollPane(tableTrans);
		panelLeft.add(scrollPane, BorderLayout.CENTER);
		JPanel controlLeft = new JPanel();
		panelLeft.add(controlLeft, BorderLayout.SOUTH);
		addProduct = new JButton("Ajouter un article");
		addProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSoldProdView(model);
			}
		});

		controlLeft.add(addProduct);
		creatProduct = new JButton("Cr�er un prod");
		creatProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewSellProductView(model);
			}
		});
		controlLeft.add(creatProduct);
		
		this.panelRight = new JPanel();

		this.membre = new JLabel("Membre :");
		this.jcb = new JComboBox<>();

		this.payment = new JLabel("Payment :");
		this.vide = new JLabel("");

		this.jLiquide = new JLabel("	Liquide :");
		this.cash = new JCheckBox();

		this.jCompte = new JLabel("	Compte :");
		this.compte = new JCheckBox();

		this.jMixte = new JLabel("	mixte :");
		this.mixte = new JCheckBox();

		this.jLiquide2 = new JLabel("		Liquide ajouté :");
		this.cashLiquide = new JSpinner();

		this.solde = new JLabel("Solde : ");
		this.solde2 = new JLabel("0,0");

		this.panelRight.setLayout(new GridLayout(7, 2));
		panelRight.add(membre);
		panelRight.add(jcb);
		panelRight.add(payment);
		panelRight.add(vide);
		panelRight.add(jLiquide);
		panelRight.add(cash);
		panelRight.add(jCompte);
		panelRight.add(compte);
		panelRight.add(jMixte);
		panelRight.add(mixte);
		panelRight.add(jLiquide2);
		panelRight.add(cashLiquide);
		panelRight.add(solde);
		panelRight.add(solde2);
		this.setLayout(new GridLayout(1, 2));
		this.add(panelLeft);
		this.add(panelRight);
	}

	@Override
	public void update(Observable o, Object arg) {
		transaction.fireTableChanged(null);
	}

}
