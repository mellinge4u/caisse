package caisse.view;

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
import caisse.product.ListRawMaterial;

public class StockView extends JPanel implements Observer {

	protected Model model;
	protected JButton addMaterial;
	protected JTable table;
	protected ListRawMaterial listeMaterial;
	
	public StockView(Model model) {
		this.model = model;
		model.addObserver(this);
		this.setLayout(new BorderLayout());
		listeMaterial = model.getRawMaterialTableModel();
		
		// TODO a supprimer
		model.addRawMaterial("Objet");
		model.getRawMateriel("Objet").addStock(5);;
		model.getRawMateriel("Objet").restock(3, 3);
		model.getRawMateriel("Objet").endRestock();
		
		table = new JTable(listeMaterial);
		JScrollPane scrollPane = new JScrollPane(table);
	    this.add(scrollPane);
		// this.add(table, BorderLayout.CENTER);
		
		addMaterial = new JButton("Ajouter un produit");
		addMaterial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String)JOptionPane.showInputDialog(null, "Nom du produit : ", "Nouveau produit", -1);
				// TODO v�rifier si non null
				model.addRawMaterial(s);
			}
		});
		this.add(addMaterial, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		listeMaterial.fireTableChanged(null);
	}

}
