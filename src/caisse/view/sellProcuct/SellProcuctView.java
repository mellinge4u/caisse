package caisse.view.sellProcuct;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;
import caisse.tools.ListRawMaterial;
import caisse.tools.ListSoldProd;

public class SellProcuctView extends JPanel implements Observer {

	protected Model model;
	protected JButton newSoldProd;
	protected JTable tableProduit;
	protected ListSoldProd listeProduit;

	public SellProcuctView(final Model model) {
		this.model = model;
		model.addObserver(this);
		
		listeProduit = model.getSoldProdModel();
		tableProduit = new JTable(listeProduit);
		JScrollPane scrollPane = new JScrollPane(tableProduit);
		newSoldProd = new JButton("Ajouter un produit");
		newSoldProd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NewSellProductView(model);
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
	}

}
