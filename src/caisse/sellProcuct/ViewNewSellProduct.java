package caisse.sellProcuct;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import caisse.sellProcuct.SoldProduct.prodType;
import caisse.stock.RawMaterial;
import caisse.stock.TableModelListRawMaterial;
import caisse.tools.MonetarySpinner;

public class ViewNewSellProduct extends JDialog {

	private final TableModelListRawMaterial tableModel;

	public ViewNewSellProduct(JFrame parent) {
		super((JFrame) parent, "Nouvel article", true);
		final JDialog dialog = this;
		this.setResizable(false);
		final Model model = Model.getInstance();

		final JTextField name = new JTextField();
		final MonetarySpinner price = new MonetarySpinner(0.1);
		final JComboBox<SoldProduct.prodType> type = new JComboBox<SoldProduct.prodType>();
		JButton add = new JButton("=>");
		JButton rem = new JButton("<=");
		JButton accept = new JButton("Valider");
		JButton cancel = new JButton("Annuler");
		final JList<RawMaterial> list = new JList<RawMaterial>(
				(RawMaterial[]) model.getAllMarerials().toArray());
		JScrollPane listScrollPane = new JScrollPane(list);
		tableModel = new TableModelListRawMaterial();
		final JTable table = new JTable(tableModel);
		JScrollPane tableScrollPane = new JScrollPane(table);

		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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

		final RawMaterial[] items = (RawMaterial[]) model.getAllMarerials().toArray();
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] val = list.getSelectedIndices();
				for (int i = 0; i < val.length; i++) {
					tableModel.addMaterial(items[val[i]], 1);
				}
				update();
			}
		});
		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.removeSelectedRows(table.getSelectedRows());
				update();
			}
		});
		accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addSoldProduct(name.getText(),
						(int) ((double) price.getValue() * 100),
						(prodType) type.getSelectedItem());
				for (RawMaterial mat : tableModel.getAllMaterial()) {
					model.addMaterialToSoldProduct(name.getText(), mat,
							tableModel.getNumber(mat));
				}
				model.writeSoldProduct();
				dialog.dispose();
			}
		});
		cancel.addActionListener(new CloseListener(this));
		name.setColumns(10);
		type.addItem(SoldProduct.prodType.DRINK);
		type.addItem(SoldProduct.prodType.FOOD);
		type.addItem(SoldProduct.prodType.MISC);
		type.setSelectedItem(SoldProduct.prodType.MISC);

		this.setLayout(new BorderLayout());
		JPanel param = new JPanel();
		JPanel tableSet = new JPanel(new BorderLayout());
		JPanel set = new JPanel();
		set.setLayout(new BoxLayout(set, BoxLayout.Y_AXIS));
		JPanel ctrl = new JPanel();

		this.add(param, BorderLayout.NORTH);
		this.add(listScrollPane, BorderLayout.WEST);
		this.add(tableSet, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		param.add(new JLabel("Nom : "));
		param.add(name);
		param.add(new JLabel("Prix : "));
		param.add(price);
		param.add(new JLabel("Type : "));
		param.add(type);

		tableSet.add(set, BorderLayout.WEST);
		tableSet.add(tableScrollPane, BorderLayout.CENTER);

		set.add(add);
		set.add(rem);

		ctrl.add(accept);
		ctrl.add(cancel);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

	void update() {
		tableModel.fireTableDataChanged();
	}
}
