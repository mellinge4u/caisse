package caisse.view.sellProcuct;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import caisse.Model;
import caisse.tools.CellRender;
import caisse.view.stock.ListRawMaterial;

public class SellProcuctView extends JPanel implements Observer {

	protected Model model;
	protected JButton newSoldProd;
	protected JTable tableProduit;
	protected ListSoldProd listeProduit;
	protected CellRender cellRender;
	protected DecimalFormat df = new DecimalFormat("#0.00");

	public SellProcuctView(final Model model, final JFrame parent) {
		this.model = model;
		model.addObserver(this);

		listeProduit = model.getSoldProdModel();
		tableProduit = new JTable(listeProduit);
		tableProduit.addContainerListener(new ContainerListener() {
			@Override
			public void componentRemoved(ContainerEvent arg0) {
			}

			@Override
			public void componentAdded(ContainerEvent arg0) {
				JTable table = (JTable) arg0.getComponent();
				if (table.getSelectedColumn() != 0) {
					JTextField text = (JTextField) arg0.getChild();
					text.setText(null);
				}
			}
		});
		cellRender = new CellRender();
		for (int i = 0; i < listeProduit.getColumnCount(); i++) {
			tableProduit.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		JScrollPane scrollPane = new JScrollPane(tableProduit);
		newSoldProd = new JButton("Ajouter un produit");
		newSoldProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NewSellProductView(model, parent);
			}
		});

		JPanel pCtrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pCtrl, BorderLayout.SOUTH);

		pCtrl.add(newSoldProd, BorderLayout.SOUTH);

	}

	@Override
	public void update(Observable o, Object arg) {
		listeProduit.fireTableChanged(null);
		cellRender = new CellRender();
		for (int i = 0; i < listeProduit.getColumnCount(); i++) {
			tableProduit.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
	}

}
