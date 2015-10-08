package caisse.stock;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import caisse.Model;
import caisse.tools.CellRender;

public class ViewStock extends JPanel implements Observer {

	protected Model model;
	protected JTable tableMaterial;
	protected TableModelRawMaterial listMaterial;
	protected CellRender cellRender;

	public ViewStock(final Model model) {
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
		tableMaterial.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		cellRender = new CellRender();
		for (int i = 0; i < listMaterial.getColumnCount(); i++) {
			tableMaterial.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
		JScrollPane scrollPane = new JScrollPane(tableMaterial);
		JButton addMaterial = new JButton("Ajouter un produit");
		addMaterial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) JOptionPane.showInputDialog(null,
						"Nom du produit : ", "Nouveau produit", -1, null, null, null);
				if (s != null) {
					model.addRawMaterial(s);
				}
			}
		});

		JButton detailsMaterial = new JButton("Details d'un produit");
		detailsMaterial.setEnabled(false);
		JButton deleteMaterial = new JButton("Supptimer un produit");
		deleteMaterial.setEnabled(false);
		JButton shoppingList = new JButton("Lister de courses");
		shoppingList.setEnabled(false);
		
		JPanel pCtrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pCtrl, BorderLayout.SOUTH);
		pCtrl.add(addMaterial);
		pCtrl.add(detailsMaterial);
		pCtrl.add(deleteMaterial);
		pCtrl.add(shoppingList);
	}

	@Override
	public void update(Observable o, Object arg) {
		listMaterial.fireTableChanged(null);
		// tableMaterial.getColumnModel().getColumn(1).setCellRenderer(stockCellRender);
		for (int i = 0; i < listMaterial.getColumnCount(); i++) {
			tableMaterial.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
	}

}
