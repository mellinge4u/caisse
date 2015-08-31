package caisse.view.sellProcuct;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.product.RawMaterial;
import caisse.tools.MaterialList;
import caisse.tools.MonetarySpinner;

public class NewSellProductView extends JFrame {

	protected Model model;
	protected JList<RawMaterial> list;
	protected final MaterialList matList;
	protected JTable table;
	protected JButton select;
	protected JButton remove;
	protected JButton accept;
	protected JButton cancel;
	protected MonetarySpinner price;
	
	public NewSellProductView(final Model model) {
		super("Nouveau prod");
		this.model = model;
		list = new JList<RawMaterial>(model.getAllMaterialsArray());
		matList = new MaterialList();
		table = new JTable(matList);
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
				// TODO Finir le listener
				model.addSoldProduct("None", (int) price.getValue());
				
			}
		});
		cancel = new JButton("Annuler");
		cancel.addActionListener(new CloseListener(this));
		price = new MonetarySpinner();
		
		JPanel panel = new JPanel();
		JPanel pList = new JPanel();
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
		pList.add(pSubCtrl, BorderLayout.EAST);
		
		pSubCtrl.setLayout(new GridLayout(4, 1));
		pSubCtrl.add(select);
		pSubCtrl.add(remove);
		pSubCtrl.add(new JLabel("Prix : "));
		pSubCtrl.add(price);
	
		pControl.add(accept);
		pControl.add(cancel);
		
		pack() ;
        setVisible(true);
	}

	void update() {
		matList.fireTableDataChanged();
	}
}
