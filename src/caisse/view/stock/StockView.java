package caisse.view.stock;

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

public class StockView extends JPanel implements Observer {

	protected Model model;
	protected JButton addMaterial;
	protected JTable tableMaterial;
	protected ListRawMaterial listeMaterial;

	public StockView(final Model model) {
		this.model = model;
		model.addObserver(this);
		
		listeMaterial = model.getRawMaterialTableModel();
		tableMaterial = new JTable(listeMaterial);
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
		listeMaterial.fireTableChanged(null);
	}

}
