package caisse.sellProcuct;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import caisse.Model;
import caisse.tools.CellRender;

public class ViewSellProcuct extends JPanel implements Observer {

	protected Model model;
	protected JTable tableProduit;
	protected TableModelSoldProd listeProduit;
	protected CellRender cellRender;

	public ViewSellProcuct(final Model model, final JFrame parent) {
		this.model = model;
		model.addObserver(this);

		listeProduit = model.getSoldProdModel();
		tableProduit = new JTable(listeProduit);
		tableProduit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

		JButton newSoldProd = new JButton("Ajouter un article");
		newSoldProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ViewNewSellProduct(model, parent);
			}
		});
		JButton deleteSoldProd = new JButton("Supprimer un article");
		deleteSoldProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = tableProduit.getSelectedRow();
				if (row != -1) {
					String nameProd = listeProduit.getSoldProduct(row).getName();
					model.deleteSoldProduct(nameProd);
					model.update();
				}
			}
		});
		JButton viewSoldProd = new JButton("Visualiser un article");
		viewSoldProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tableProduit.getSelectedRow() != -1) {
					new ViewSellProductDetails(parent, listeProduit.getSoldProduct(tableProduit.getSelectedRow()));
				}
			}
		});
		;

		JPanel pCtrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pCtrl, BorderLayout.SOUTH);

		pCtrl.add(newSoldProd);
		pCtrl.add(deleteSoldProd);
		pCtrl.add(viewSoldProd);

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
