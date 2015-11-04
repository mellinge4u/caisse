package caisse.sell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;
import caisse.tools.IdSpinner;
import caisse.tools.MonetarySpinner;
import caisse.tools.CellRender;

public class ViewSell extends JPanel implements Observer {

	protected Model model;

	private JTable tableTrans;
	private TableModelCurrentTransaction transaction;
	private JScrollPane scrollPane;

	private JSpinner userId;
	private JLabel name;
	private JLabel firstname;
	private JLabel sold;
	private MonetarySpinner cashIn;
	private JLabel lCashOut;
	private JLabel cashOut;
	private JLabel lSoldDebit;
	private JLabel soldDebit;
	private JLabel soldFinal;
	private JLabel total;
	private PanelAddSoldProd select;

	public ViewSell(final Model model, final JFrame frame) {
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
		tableTrans.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 127) {
					removeArticle();
				}
			}
		});
		scrollPane = new JScrollPane(tableTrans);

		JButton removeProduct = new JButton("Retirer");
		removeProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeArticle();
			}
		});
		JButton validTrans = new JButton("Valider");
		validTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valid();
			}
		});
		JButton cancelTrans = new JButton("Annuler");
		cancelTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transaction.clear();
				reset();
			}
		});

		userId = new IdSpinner();
		userId.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.update();
			}
		});
		name = new JLabel();
		firstname = new JLabel();
		sold = new JLabel("0.00");
		cashIn = new MonetarySpinner(0.1);
		cashIn.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.update();
			}
		});
		cashOut = new JLabel("0.00");
		soldDebit = new JLabel("0.00");
		soldFinal = new JLabel("0.00");

		JPanel pCtrl = new JPanel();
		JPanel pTransaction = new JPanel(new BorderLayout());
		select = new PanelAddSoldProd(removeProduct);
		JPanel pTranPayment = new JPanel(new BorderLayout());
		JPanel pTranPaymentSub = new JPanel(new BorderLayout());
		JPanel pTranPaymentGrid = new JPanel();
		JPanel pProductSelection = new JPanel(new BorderLayout());

		JLabel lID = new JLabel("ID : ");
		JLabel lName = new JLabel("Nom : ");
		JLabel lFirstname = new JLabel("Prenom : ");
		JLabel lSold = new JLabel("Solde : ");
		JLabel lCashIn = new JLabel("Espece : ");
		lCashOut = new JLabel("Monaie rendu : ");
		lSoldDebit = new JLabel("Debit compte : ");
		JLabel lSoldFinal = new JLabel("Solde final : ");
		total = new JLabel("Total : ");
		total.setBorder(new LineBorder(Color.RED));
		total.setFont(new Font(total.getFont().getFontName(), Font.BOLD, 20));

		this.setLayout(new BorderLayout());
		this.add(pTransaction, BorderLayout.CENTER);
		this.add(pTranPayment, BorderLayout.EAST);
		this.add(pCtrl, BorderLayout.SOUTH);

		pTransaction.add(pProductSelection, BorderLayout.CENTER);
		pTransaction.add(scrollPane, BorderLayout.SOUTH);

		pProductSelection.add(select, BorderLayout.CENTER);
		pProductSelection.add(new JSeparator(SwingConstants.VERTICAL),
				BorderLayout.EAST);

		pCtrl.add(validTrans);
		pCtrl.add(cancelTrans);

		pTranPayment.add(pTranPaymentSub, BorderLayout.CENTER);
		pTranPayment.add(new JSeparator(SwingConstants.HORIZONTAL),
				BorderLayout.SOUTH);
		pTranPaymentSub.add(pTranPaymentGrid, BorderLayout.NORTH);
		pTranPaymentSub.add(total, BorderLayout.SOUTH);
		pTranPaymentGrid.setLayout(new GridLayout(10, 2));
		pTranPaymentGrid.add(lID);
		pTranPaymentGrid.add(userId);
		pTranPaymentGrid.add(lName);
		pTranPaymentGrid.add(name);
		pTranPaymentGrid.add(lFirstname);
		pTranPaymentGrid.add(firstname);
		pTranPaymentGrid.add(lSold);
		pTranPaymentGrid.add(sold);
		pTranPaymentGrid.add(new JLabel());
		pTranPaymentGrid.add(new JLabel());
		pTranPaymentGrid.add(lCashIn);
		pTranPaymentGrid.add(cashIn);
		pTranPaymentGrid.add(lCashOut);
		pTranPaymentGrid.add(cashOut);
		pTranPaymentGrid.add(lSoldDebit);
		pTranPaymentGrid.add(soldDebit);
		pTranPaymentGrid.add(new JLabel());
		pTranPaymentGrid.add(new JLabel());
		pTranPaymentGrid.add(lSoldFinal);
		pTranPaymentGrid.add(soldFinal);
	}

	private void valid() {
		int res = JOptionPane.YES_OPTION;
		int debit = Integer
				.max(transaction.getCost() - cashIn.getIntValue(), 0);
		if (res == JOptionPane.YES_OPTION
				&& !Model.getInstance().isIdUsed((int) userId.getValue())
				&& debit > 0) {
			res = JOptionPane.NO_OPTION;
			JOptionPane
					.showMessageDialog(
							null,
							"L'adhèrent n'est pas encore inscrit, le débit de sont compte est impossible.",
							"Adhèrent non inscrit", 0);
		}
		if (res == JOptionPane.YES_OPTION
				&& !Model.getInstance().isIdUsed((int) userId.getValue())) {
			res = JOptionPane
					.showConfirmDialog(
							null,
							"Le numero d'adhèrent n'est pas utilisé, voulez-vous continuer ?",
							"Adhèrent annonyme", JOptionPane.YES_NO_OPTION, 2);
		}
		if (res == JOptionPane.YES_OPTION && (int) userId.getValue() == 0) {
			res = JOptionPane.showConfirmDialog(null,
					"Aucun adhèrent séléctionné, voulez-vous continuer ?",
					"Adhèrent annonyme", JOptionPane.YES_NO_OPTION, 2);
		}
		if (res == JOptionPane.YES_OPTION && debit > 0) {
			res = JOptionPane
					.showConfirmDialog(
							null,
							"Le payment en espece est inssufisant, voulez-vous débiter le compte ?",
							"Espece insuffisant", JOptionPane.YES_NO_OPTION, 2);
		}
		if (res == JOptionPane.YES_OPTION) {
			int credit = Integer.min(transaction.getCost(),
					cashIn.getIntValue());
			model.debitUser((int) userId.getValue(), debit);
			model.creditUser(-1, credit);
			transaction.validTransaction((int) userId.getValue(),
					Integer.min(cashIn.getIntValue(), transaction.getCost()));
			userId.setValue(0);
			reset();
		}
	}

	public void reset() {
		userId.setValue(0);
		cashIn.setValue(0.00);
		model.update();
		// select.resetSelection();

	}

	public void removeArticle() {
		int lastRow = transaction.getRowCount() - 1;
		ArrayList<SoldProduct> items = transaction.getAllProduct();
		int[] val = tableTrans.getSelectedRows();
		for (int i = 0; i < val.length; i++) {
			if (val[i] != lastRow) {
				model.removeProductOnCurrentTransaction(items.get(val[i]));
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		transaction.fireTableChanged(null);
		int cost = transaction.getCost();
		int left = cost - cashIn.getIntValue();
		double dLeft = (double) left / 100;
		int id = (int) userId.getValue();
		int userSold = model.getUserSold(id);
		DecimalFormat df = Model.doubleFormatMoney;
		name.setText(model.getUserName(id));
		firstname.setText(model.getUserFirstname(id));
		sold.setText(df.format((double) userSold / 100) + " €");
		total.setText("Total : "
				+ df.format((double) transaction.getCost() / 100) + " €");
		if (left < 0) {
			cashOut.setText(df.format(-dLeft) + " €");
			soldDebit.setText("0.00 €");
			soldFinal.setText(df.format((double) userSold / 100) + " €");
			lCashOut.setForeground(Color.BLACK);
			cashOut.setForeground(Color.BLACK);
			lSoldDebit.setForeground(Color.LIGHT_GRAY);
			soldDebit.setForeground(Color.LIGHT_GRAY);
		} else if (left > 0) {
			cashOut.setText("0.00 €");
			soldDebit.setText(df.format(dLeft) + " €");
			soldFinal.setText(df.format((double) (userSold - left) / 100)
					+ " €");
			lCashOut.setForeground(Color.LIGHT_GRAY);
			cashOut.setForeground(Color.LIGHT_GRAY);
			lSoldDebit.setForeground(Color.BLACK);
			soldDebit.setForeground(Color.BLACK);
		} else {
			cashOut.setText("0.00 €");
			soldDebit.setText("0.00 €");
			soldFinal.setText(df.format((double) userSold / 100) + " €");
			lCashOut.setForeground(Color.LIGHT_GRAY);
			cashOut.setForeground(Color.LIGHT_GRAY);
			lSoldDebit.setForeground(Color.LIGHT_GRAY);
			soldDebit.setForeground(Color.LIGHT_GRAY);
		}
		for (int i = 0; i < transaction.getColumnCount(); i++) {
			tableTrans.getColumnModel().getColumn(i)
					.setCellRenderer(transaction.getColumnModel(i));
		}
		Dimension d = tableTrans.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, tableTrans
				.getRowHeight() * (transaction.getRowCount() + 2) - 9));
	}

}
