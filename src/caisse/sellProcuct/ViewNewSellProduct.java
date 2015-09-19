package caisse.sellProcuct;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.stock.RawMaterial;
import caisse.stock.TableModelListRawMaterial;
import caisse.tools.MonetarySpinner;

public class ViewNewSellProduct extends JDialog {

	protected Model model;
	protected JList<RawMaterial> list;
	protected final TableModelListRawMaterial matList;
	protected JTable table;
	protected JTextField name;
	protected JButton select;
	protected JButton remove;
	protected JButton accept;
	protected JButton cancel;
	protected MonetarySpinner price;

	public ViewNewSellProduct(final Model model, JFrame parent) {
		super((JFrame) parent, "Nouvel article", true);
		this.model = model;
		final JDialog dialog = this;
		this.setResizable(false);
		final RawMaterial[] items = model.getAllMaterialsArray();

		list = new JList<RawMaterial>(model.getAllMaterialsArray());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		matList = new TableModelListRawMaterial();
		table = new JTable(matList);
		table.addContainerListener(new ContainerListener() {
			@Override
			public void componentRemoved(ContainerEvent arg0) {
			}

			@Override
			public void componentAdded(ContainerEvent arg0) {
				JTextField text = (JTextField) arg0.getChild();
				text.setText(null);
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		name = new JTextField();
		select = new JButton("Selectionner");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int[] val = list.getSelectedIndices();
				for (int i = 0; i < val.length; i++) {
					matList.addMaterial(items[val[i]], 1);
				}
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
				model.writeSoldProduct();
				dialog.dispose();
			}
		});
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));
		price = new MonetarySpinner(0.1);

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
		panel.add(scrollPane);

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
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

	void update() {
		matList.fireTableDataChanged();
	}
}
