package caisse.sell;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.sellProcuct.SoldProduct;

public class ViewAddSoldProd extends JDialog {

	protected Model model;

	protected JList<SoldProduct> list;
	protected JButton accept;
	protected JButton cancel;

	public ViewAddSoldProd(final Model model, JFrame parent) {
		super((JFrame) parent, "Nouvel article", true);
		this.model = model;
		final Window win = this;
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		final SoldProduct[] items = model.getAvailableSoldProdArray();
		list = new JList<SoldProduct>(items);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] val = list.getSelectedIndices();
				for (int i = 0; i < val.length; i++) {
					model.addProductOnTransaction(items[val[i]]);
				}
				win.dispose();
			}
		});
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));

		JPanel control = new JPanel();

		this.add(list, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);
		control.setLayout(new GridLayout(1, 2));
		control.add(accept);
		control.add(cancel);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
