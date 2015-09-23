package caisse.historic;

import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

import caisse.Model;

public class ViewTransactionDetails extends JDialog {

	protected Model model;

	public ViewTransactionDetails(final Model model, JFrame parent,
			Transaction tran) {
		super((JFrame) parent, "Transaction", true);
		this.setResizable(false);
		this.model = model;

		JLabel lUser = new JLabel(model.getUserName(tran.getClientId()) + " "
				+ model.getUserFirstname(tran.getClientId()));
		JLabel lDate = new JLabel(Model.dateFormatFull.format(tran.getDate()));
		JLabel lPrice = new JLabel(Model.doubleFormatMoney.format((double) tran
				.getPrice() / 100) + " €");
		JLabel lCashAdd = new JLabel(Model.doubleFormatMoney.format((double) tran
				.getCashAdd() / 100) + " €");
		JTable table = new JTable(); 
		
		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
