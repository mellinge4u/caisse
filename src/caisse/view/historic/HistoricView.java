package caisse.view.historic;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;
import caisse.tools.ListHistoric;

public class HistoricView extends JPanel implements Observer {

	protected Model model;
	protected JTable table;
	protected ListHistoric listHisto;

	public HistoricView(Model model) {
		this.model = model;
		model.addObserver(this);
		this.setLayout(new BorderLayout());

		listHisto = model.getHistoricModel();
		table = new JTable(listHisto);
		JScrollPane scrollPane = new JScrollPane(table);
		
		this.add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void update(Observable o, Object arg) {
		listHisto.fireTableDataChanged();
	}
}
