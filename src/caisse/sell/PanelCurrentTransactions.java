package caisse.sell;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;

public class PanelCurrentTransactions extends JPanel implements Observer {

	private JTable table;
	private TableModelTransactionUndone tableModel;
	
	public PanelCurrentTransactions() {
		Model.getInstance().addObserver(this);
		tableModel = Model.getInstance().getTransactionUndone();
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		this.setLayout(new BorderLayout());
		
		this.add(scrollPane, BorderLayout.CENTER);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		tableModel.fireTableDataChanged();
	}

}
