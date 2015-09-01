package caisse.view.sellProcuct;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.product.RawMaterial;
import caisse.tools.MaterialList;
import caisse.tools.MonetarySpinner;

public class NewSellProductView extends JDialog {

	protected Model model;
	protected JList<RawMaterial> list;
	protected final MaterialList matList;
	protected JTable table;
	protected JTextField name;
	protected JButton select;
	protected JButton remove;
	protected JButton accept;
	protected JButton cancel;
	protected MonetarySpinner price;

	public NewSellProductView(final Model model, JFrame parent) {
		super((JFrame) parent, "Nouvel article", true);
		this.model = model;
		final JDialog dialog = this;
		this.setResizable(false);

		list = new JList<RawMaterial>(model.getAllMaterialsArray());
		matList = new MaterialList();
		table = new JTable(matList);
		name = new JTextField();
		select = new JButton("Selectionner");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matList.addMaterial(list.getSelectedValue(), 1);
				update();
			}
		});
		remove = new JButton("Enlever");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matList.removeMaterial(list.getSelectedValue());
				update();
			}
		});
		accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addSoldProduct(name.getText(),
						(int) ((double) price.getValue() * 100));
				for (RawMaterial mat : matList.getAllMaterial()) {
					model.addMaterialToSoldProduct(name.getText(), mat,
							matList.getNumber(mat));
				}
				dialog.dispose();
			}
		});
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));
		price = new MonetarySpinner();

		JPanel panel = new JPanel();
		JPanel pList = new JPanel();
		JPanel pInter = new JPanel(new BorderLayout());
		JPanel pSubCtrl = new JPanel();
		JPanel pControl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		this.add(pControl, BorderLayout.SOUTH);

		panel.setLayout(new GridLayout(1, 2));
		panel.add(pList);
		panel.add(table);

		pList.setLayout(new BorderLayout());
		pList.add(list, BorderLayout.CENTER);
		pList.add(pInter, BorderLayout.EAST);

		pInter.add(pSubCtrl, BorderLayout.NORTH);
		pSubCtrl.setLayout(new BoxLayout(pSubCtrl, BoxLayout.Y_AXIS));
		pSubCtrl.add(new JLabel("Nom : "));
		pSubCtrl.add(name);
		pSubCtrl.add(select);
		pSubCtrl.add(remove);
		pSubCtrl.add(new JLabel("Prix : "));
		pSubCtrl.add(price);

		pControl.add(accept);
		pControl.add(cancel);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

	void update() {
		matList.fireTableDataChanged();
	}
}
