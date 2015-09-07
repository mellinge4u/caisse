package caisse.view.stock;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import caisse.Model;
import caisse.tools.ListRawMaterial;
import caisse.tools.StockColumnCellRender;

public class StockView extends JPanel implements Observer {

	protected Model model;
	protected JButton addMaterial;
	protected JTable tableMaterial;
	protected ListRawMaterial listMaterial;
	protected StockColumnCellRender stockCellRender;

	public StockView(final Model model) {
		this.model = model;
		model.addObserver(this);

		listMaterial = model.getRawMaterialTableModel();
		tableMaterial = new JTable(listMaterial);
		tableMaterial.addContainerListener(new ContainerListener() {
			@Override
			public void componentRemoved(ContainerEvent arg0) {
			}

			@Override
			public void componentAdded(ContainerEvent arg0) {
				JTextField text = (JTextField) arg0.getChild();
				text.setText(null);
			}
		});
		stockCellRender = new StockColumnCellRender();
		tableMaterial.getColumnModel().getColumn(1).setCellRenderer(stockCellRender);
		JScrollPane scrollPane = new JScrollPane(tableMaterial);
		addMaterial = new JButton("Ajouter un produit");
		addMaterial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) JOptionPane.showInputDialog(null,
						"Nom du produit : ", "Nouveau produit", -1);
				if (s != null) {
					model.addRawMaterial(s);
				}
			}
		});

		JPanel pCtrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pCtrl, BorderLayout.SOUTH);
		pCtrl.add(addMaterial);
	}

	@Override
	public void update(Observable o, Object arg) {
		listMaterial.fireTableChanged(null);
		tableMaterial.getColumnModel().getColumn(1).setCellRenderer(stockCellRender);
	}

}
