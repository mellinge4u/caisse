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
import caisse.tools.MonetarySpinner;
import caisse.view.sellProcuct.NewSellProductView;

public class SellView extends JPanel implements Observer {

	protected Model model;

	private JTable tableTrans;
	private CurrentTransaction transaction;
	private JButton addProduct;
	private JButton creatProduct;
	private JComboBox<String> member;
	private JCheckBox cash;
	private JCheckBox account;
	private JCheckBox both;
	private MonetarySpinner cashAmount;

	public SellView(final Model model) {
		this.model = model;
		model.addObserver(this);

		transaction = model.getCurrentTransaction();
		tableTrans = new JTable(transaction);
		JScrollPane scrollPane = new JScrollPane(tableTrans);
		addProduct = new JButton("Ajouter un article");
		addProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSoldProdView(model);
			}
		});
		creatProduct = new JButton("Créer un prod");
		creatProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewSellProductView(model);
			}
		});
		this.member = new JComboBox<>();
		this.cash = new JCheckBox();
		this.account = new JCheckBox();
		this.both = new JCheckBox();
		this.cashAmount = new MonetarySpinner();

		JPanel pRight = new JPanel();
		JPanel pCtrl = new JPanel();
		JPanel pInter = new JPanel(new BorderLayout());
		JLabel lMembre = new JLabel("Membre :");
		JLabel lPayment = new JLabel("Payment :");
		JLabel lEmpty = new JLabel("");
		JLabel lCash = new JLabel("Liquide :");
		JLabel lAccount = new JLabel("Compte :");
		JLabel lBoth = new JLabel("Mixte :");
		JLabel lCashAmount = new JLabel("Liquide ajoute :");	// TODO accent
		JLabel lSoldeText = new JLabel("Solde : ");
		JLabel lSoldeAmount = new JLabel("0,0");

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pInter, BorderLayout.EAST);
		this.add(pCtrl, BorderLayout.SOUTH);
	
		pCtrl.add(addProduct);
		pCtrl.add(creatProduct);

		pInter.add(pRight, BorderLayout.NORTH);
		pRight.setLayout(new GridLayout(7, 2));
		pRight.add(lMembre);
		pRight.add(member);
		pRight.add(lPayment);
		pRight.add(lEmpty);
		pRight.add(lCash);
		pRight.add(cash);
		pRight.add(lAccount);
		pRight.add(account);
		pRight.add(lBoth);
		pRight.add(both);
		pRight.add(lCashAmount);
		pRight.add(cashAmount);
		pRight.add(lSoldeText);
		pRight.add(lSoldeAmount);
	}

	@Override
	public void update(Observable o, Object arg) {
		transaction.fireTableChanged(null);
	}

}
