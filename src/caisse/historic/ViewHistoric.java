package caisse.historic;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import caisse.Model;
import caisse.tools.CellRender;

public class ViewHistoric extends JPanel implements Observer {

	protected Model model;
	protected JTable table;
	protected TableModelHistoric listHisto;
	protected CellRender cellRender;
	protected JLabel total;

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
		final JSpinner showingDay = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		showingDay.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				listHisto.setWatchingDays((int) showingDay.getValue());
				listHisto.updateDisplayList();
				listHisto.fireTableDataChanged();
				total.setText("Transaction : " + Model.doubleFormatMoney.format((double) listHisto.getTotalTransaction() / 100) + " €");
			}
		});
		JPanel ctrl = new JPanel(new BorderLayout());
		JPanel ctrlUp = new JPanel();
		JPanel ctrlDown = new JPanel();
		total = new JLabel("Transaction : " + Model.doubleFormatMoney.format((double) listHisto.getTotalTransaction() / 100) + " €");
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		ctrl.add(ctrlUp, BorderLayout.NORTH);
		ctrl.add(ctrlDown, BorderLayout.SOUTH);
		

		ctrlDown.add(new JLabel("Afficher l'historique sur "));
		ctrlDown.add(showingDay);
		ctrlDown.add(new JLabel(" jour(s)"));
		ctrlUp.add(total);
	}

	@Override
	public void update(Observable o, Object arg) {
		listHisto.fireTableDataChanged();
		for (int i = 0; i < listHisto.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
		total.setText("Transaction : " + Model.doubleFormatMoney.format((double) listHisto.getTotalTransaction() / 100) + " €");
	}
}
