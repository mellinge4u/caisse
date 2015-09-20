package caisse.sell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.org.apache.xpath.internal.operations.Mod;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;
import caisse.tools.IdSpinner;
import caisse.tools.MonetarySpinner;
import caisse.tools.CellRender;

public class ViewSell extends JPanel implements Observer {

	protected Model model;

	private JTable tableTrans;
	private TableModelCurrentTransaction transaction;
	protected CellRender cellRender;

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
		cellRender = new CellRender();
		for (int i = 0; i < transaction.getColumnCount(); i++) {
			tableTrans.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		JScrollPane scrollPane = new JScrollPane(tableTrans);
		JButton addProduct = new JButton("Ajouter un article");
		addProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewAddSoldProd(model, frame);
			}
		});
		JButton removeProduct = new JButton("Retirer un article");
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
				int debit = Integer.max(transaction.getCost() - cashIn.getIntValue(), 0);
				int credit = Integer.min(transaction.getCost(), cashIn.getIntValue());
				model.debitUser((int) userId.getValue(), debit);
				model.creditUser(-1, credit);
				transaction.validTransaction((int) userId.getValue(),
						Integer.min(cashIn.getIntValue(), transaction.getCost()));
				userId.setValue(0);
				reset();
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

		JPanel pRight = new JPanel();
		JPanel pCtrl = new JPanel();
		JPanel pInter = new JPanel(new BorderLayout());

		JLabel lID = new JLabel("ID : ");
		JLabel lName = new JLabel("Nom : ");
		JLabel lFirstname = new JLabel("Prenom : ");
		JLabel lSold = new JLabel("Sold : ");
		JLabel lCashIn = new JLabel("Espece : ");
		lCashOut = new JLabel("Monaie rendu : ");
		lSoldDebit = new JLabel("Debit compte : ");
		JLabel lSoldFinal = new JLabel("Sold final : ");

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pInter, BorderLayout.EAST);
		this.add(pCtrl, BorderLayout.SOUTH);

		pCtrl.add(addProduct);
		pCtrl.add(removeProduct);
		pCtrl.add(validTrans);
		pCtrl.add(cancelTrans);

		pInter.add(pRight, BorderLayout.NORTH);
		pInter.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
		pRight.setLayout(new GridLayout(10, 2));
		pRight.add(lID);
		pRight.add(userId);
		pRight.add(lName);
		pRight.add(name);
		pRight.add(lFirstname);
		pRight.add(firstname);
		pRight.add(lSold);
		pRight.add(sold);
		pRight.add(new JLabel());
		pRight.add(new JLabel());
		pRight.add(lCashIn);
		pRight.add(cashIn);
		pRight.add(lCashOut);
		pRight.add(cashOut);
		pRight.add(lSoldDebit);
		pRight.add(soldDebit);
		pRight.add(new JLabel());
		pRight.add(new JLabel());
		pRight.add(lSoldFinal);
		pRight.add(soldFinal);
	}

	public void reset() {
		userId.setValue(0);
		cashIn.setValue(0.00);
		model.update();
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
			soldFinal.setText(df.format((double) (userSold - left) / 100) + " €");
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
			tableTrans.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
	}

}
