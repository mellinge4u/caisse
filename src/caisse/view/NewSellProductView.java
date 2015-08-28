package caisse.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;

import caisse.Model;
import caisse.product.RawMaterial;
import caisse.tools.MonetarySpinner;

public class NewSellProductView extends JFrame {

	protected Model model;
	protected JList<RawMaterial> list;
	protected JTable table;
	protected JButton select;
	protected JButton remove;
	protected JButton accept;
	protected JButton cancel;
	protected MonetarySpinner price;
	
	public NewSellProductView(Model model) {
		super("Nouveau prod");
		this.model = model;
		list = new JList<RawMaterial>();
		table = new JTable();
		select = new JButton();
		remove = new JButton();
		accept = new JButton();
		cancel = new JButton();
		price = new MonetarySpinner();
		
		JPanel panel = new JPanel();
		JPanel pList = new JPanel();
		JPanel pSubCtrl = new JPanel();
//		JPanel pTable = new JPanel();
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
	
		pControl.add(cancel);
		pControl.add(accept);
		
		pack() ;
        setVisible(true);
	}

}
