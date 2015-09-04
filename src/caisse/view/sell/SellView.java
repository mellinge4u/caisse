package caisse.view.sell;

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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

import caisse.Model;
import caisse.WriteFile;
import caisse.product.SoldProduct;
import caisse.tools.CurrentTransaction;
import caisse.tools.MonetarySpinner;
import caisse.view.sellProcuct.NewSellProductView;

public class SellView extends JPanel implements Observer {

	protected Model model;

	private JTable tableTrans;
	private CurrentTransaction transaction;
	private JButton addProduct;
	private JButton validTrans;
	private JButton cancelTrans;
	private JTextField member;
//	private JComboBox<String> member;
	private JRadioButton cash;
	private JRadioButton account;
	private JRadioButton both;
	private MonetarySpinner cashAmount;
	private JLabel lSoldeAmount;

	private JButton write;
	
	public SellView(final Model model, final JFrame frame) {
		this.model = model;
		model.addObserver(this);

		transaction = model.getCurrentTransaction();
		tableTrans = new JTable(transaction);
		tableTrans.addContainerListener(new ContainerListener() {
			@Override
			public void componentRemoved(ContainerEvent arg0) {
			}

			@Override
			public void componentAdded(ContainerEvent arg0) {
				JTextField text = (JTextField) arg0.getChild();
				text.setText(null);
			}
		});
		JScrollPane scrollPane = new JScrollPane(tableTrans);
		addProduct = new JButton("Ajouter un article");
		addProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSoldProdView(model, frame);
			}
		});
		validTrans = new JButton("Valider");
		validTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transaction.validTransaction(member.getText());
				model.update();
			}
		});
		cancelTrans = new JButton("Annuler");
		cancelTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transaction.clear();
				model.update();
			}
		});

		this.member = new JTextField();
//		this.member = new JComboBox<>();
		this.cash = new JRadioButton();
		this.account = new JRadioButton();
		this.both = new JRadioButton();
		this.cashAmount = new MonetarySpinner();

		this.write = new JButton("Write");
		write.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.writeStock();
				model.writePurchasedProduct();
				model.writeSoldProduct();
			}
		});
		
		JPanel pRight = new JPanel();
		JPanel pCtrl = new JPanel();
		JPanel pInter = new JPanel(new BorderLayout());
		JLabel lMembre = new JLabel("Membre :");
		JLabel lPayment = new JLabel("Payment :");
		JLabel lEmpty = new JLabel("");
		JLabel lCash = new JLabel("Liquide :");
		JLabel lAccount = new JLabel("Compte :");
		JLabel lBoth = new JLabel("Mixte :");
		JLabel lCashAmount = new JLabel("Liquide ajoute :"); // TODO Accents
		JLabel lSoldeText = new JLabel("Solde : ");
		lSoldeAmount = new JLabel("0.00 EUR");

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pInter, BorderLayout.EAST);
		this.add(pCtrl, BorderLayout.SOUTH);

		pCtrl.add(addProduct);
		pCtrl.add(validTrans);
		pCtrl.add(cancelTrans);
		pCtrl.add(write);

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
		int cent = transaction.getCost();
		double price = (double) cent / 100;
		String add = "";
		if (cent % 10 == 0) {
			add = "0";
		}
		lSoldeAmount.setText(price + add + " EUR"); // TODO symbole EUR
	}

}
