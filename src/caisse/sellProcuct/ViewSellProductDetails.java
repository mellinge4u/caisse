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
import javax.swing.JSeparator;
import javax.swing.JTable;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.stock.TableModelListRawMaterial;

public class ViewSellProductDetails extends JDialog {

	protected Model model;

	public ViewSellProductDetails(Model model, JFrame parent, SoldProduct prod) {
		super((JFrame) parent, "Article en vente : " + prod.getName(), true);
		this.setResizable(false);
		this.model = model;

		DecimalFormat df = Model.doubleFormatMoney;
		JLabel name = new JLabel(prod.getName());
		name.setHorizontalAlignment(JLabel.CENTER);
		JLabel quantity = new JLabel("Quantit� : " + prod.getQuantity());
		quantity.setHorizontalAlignment(JLabel.CENTER);
		JLabel price = new JLabel("Prix : " + df.format((double) prod.getSalePrice() / 100) + " �");
		price.setHorizontalAlignment(JLabel.CENTER);
		JLabel cost = new JLabel("Co�t : " + df.format((double) prod.getCost() / 100) + " �");
		cost.setHorizontalAlignment(JLabel.CENTER);
		JLabel profit = new JLabel("B�n�fice : " + df.format((double) prod.getProfit() / 100) + " �");
		profit.setHorizontalAlignment(JLabel.CENTER);
		TableModelListRawMaterial rawMat = prod.getTableModel();
		JTable table = new JTable(rawMat);
		JScrollPane scrollPane = new JScrollPane(table);
		JButton ok = new JButton("ok");
		ok.addActionListener(new CloseListener(this));

		JPanel detail = new JPanel(new BorderLayout());
		JPanel detailUp = new JPanel(new GridLayout(1, 2));
		JPanel detailDown = new JPanel(new GridLayout(1, 3));
		JPanel ctrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(detail, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		detail.add(detailUp, BorderLayout.NORTH);
		detail.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.CENTER);
		detail.add(detailDown, BorderLayout.SOUTH);
		detailUp.add(name);
		detailUp.add(quantity);
		detailDown.add(price);
		detailDown.add(cost);
		detailDown.add(profit);

		ctrl.add(ok);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- (this.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}