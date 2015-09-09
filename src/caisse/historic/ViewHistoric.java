package caisse.historic;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;
import caisse.tools.CellRender;

public class ViewHistoric extends JPanel implements Observer {

	protected Model model;
	protected JTable table;
	protected TableModelHistoric listHisto;
	protected CellRender cellRender;

	public ViewHistoric(Model model) {
		this.model = model;
		model.addObserver(this);
		this.setLayout(new BorderLayout());

		listHisto = model.getHistoricModel();
		table = new JTable(listHisto);
		cellRender = new CellRender();
		for (int i = 0; i < listHisto.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		
		this.add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void update(Observable o, Object arg) {
		listHisto.fireTableDataChanged();
		for (int i = 0; i < listHisto.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
	}
}
