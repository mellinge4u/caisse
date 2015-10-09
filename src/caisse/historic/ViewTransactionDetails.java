package caisse.historic;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.tools.CellRender;
import caisse.user.User;

public class ViewTransactionDetails extends JDialog {

	protected Model model;

	public ViewTransactionDetails(final Model model, Window parent,
			Transaction tran) {
		super((JFrame) parent, "Transaction", true);
		this.setResizable(false);
		this.model = model;

		CellRender cellRender = new CellRender();
		User u = model.getUserById(tran.getClientId()); 
		JLabel lUser = new JLabel(u.getName() + " "
				+ u.getFirstname() + " (" + u.getUserId() + ")");
		JLabel lDate = new JLabel(Model.dateFormatFull.format(tran.getDate()));
		JLabel lPrice = new JLabel("Prix : " + Model.doubleFormatMoney.format((double) tran
				.getPrice() / 100) + " €");
		JLabel lCashAdd = new JLabel("Especes : " + Model.doubleFormatMoney.format((double) tran
				.getCashAdd() / 100) + " €");
		JTable table = new JTable(tran);
		JScrollPane scrollPane = new JScrollPane(table);
		JButton bOk = new JButton("Ok");
		bOk.addActionListener(new CloseListener(this));
		
		JPanel details = new JPanel(new GridLayout(2,  2));
		JPanel ctrl = new JPanel();
		
		for (int i = 0; i < tran.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
		this.setLayout(new BorderLayout());
		this.add(details, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		details.add(lUser);
		details.add(lDate);
		details.add(lPrice);
		details.add(lCashAdd);
		
		ctrl.add(bOk);
		
		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
