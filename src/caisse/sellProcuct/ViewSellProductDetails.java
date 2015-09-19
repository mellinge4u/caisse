package caisse.sellProcuct;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.stock.TableModelListRawMaterial;

public class ViewSellProductDetails extends JDialog {

	protected Model model;

	public ViewSellProductDetails(Model model, JFrame parent, SoldProduct prod) {
		super((JFrame) parent, "Adherent", true);
		this.setResizable(false);
		this.model = model;

		DecimalFormat df = Model.doubleFormatMoney;
		JLabel name = new JLabel(prod.getName());
		JLabel quantity = new JLabel("Quantité : " + prod.getQuantity());
		JLabel price = new JLabel("Prix : " + df.format((double) prod.getSalePrice() / 100) + " €");
		JLabel cost = new JLabel("Coût : " + df.format((double) prod.getCost() / 100) + " €");
		JLabel profit = new JLabel("Bénéfice : " + df.format((double) prod.getProfit() / 100) + " €");
		TableModelListRawMaterial rawMat = prod.getTableModel();
		JTable table = new JTable(rawMat);
		JScrollPane scrollPane = new JScrollPane(table);
		JButton ok = new JButton("ok");
		ok.addActionListener(new CloseListener(this));

		JPanel detail = new JPanel(new GridLayout(1, 5));
		JPanel ctrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(detail, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		detail.add(name);
		detail.add(quantity);
		detail.add(price);
		detail.add(cost);
		detail.add(profit);

		ctrl.add(ok);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- (this.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
