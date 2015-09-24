package caisse.historic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import caisse.Model;
import caisse.tools.CellRender;

public class ViewHistoric extends JPanel implements Observer {

	protected Model model;
	protected JTable table;
	protected TableModelHistoric listHisto;
	protected CellRender cellRender;
	protected JLabel total;
	protected JLabel caisse;

	public ViewHistoric(final Model model, final JFrame parent) {
		this.model = model;
		model.addObserver(this);
		this.setLayout(new BorderLayout());

		listHisto = model.getHistoricModel();
		table = new JTable(listHisto);
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new ViewTransactionDetails(model, parent, listHisto.getTransaction(table.getSelectedRow()));
				}
			}
		});
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		cellRender = new CellRender();
		for (int i = 0; i < listHisto.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		final JSpinner showingDay = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		JComponent editor = showingDay.getEditor();
		JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
		tf.setColumns(4);
		showingDay.setValue(1);
		showingDay.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				listHisto.setWatchingDays((int) showingDay.getValue());
				listHisto.updateDisplayList();
				listHisto.fireTableDataChanged();
				total.setText("Transaction : "
						+ Model.doubleFormatMoney.format((double) listHisto.getTotalTransaction() / 100) + " €");
			}
		});
		JPanel ctrl = new JPanel(new BorderLayout());
		JPanel ctrlUp = new JPanel();
		JPanel ctrlCenter = new JPanel();
		JPanel ctrlDown = new JPanel();
		total = new JLabel("Transaction : "
				+ Model.doubleFormatMoney.format((double) listHisto.getTotalTransaction() / 100) + " €");
		caisse = new JLabel("Caisse : " + Model.doubleFormatMoney.format((double) model.getUserSold(-1) / 100) + " €");

		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		ctrl.add(ctrlUp, BorderLayout.NORTH);
		ctrl.add(ctrlCenter, BorderLayout.CENTER);
		ctrl.add(ctrlDown, BorderLayout.SOUTH);

		ctrlUp.add(total);
		ctrlCenter.add(caisse);
		ctrlDown.add(new JLabel("Afficher l'historique sur "));
		ctrlDown.add(showingDay);
		ctrlDown.add(new JLabel(" jour(s)"));
	}

	@Override
	public void update(Observable o, Object arg) {
		listHisto.fireTableDataChanged();
		for (int i = 0; i < listHisto.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		total.setText("Transaction : " + Model.doubleFormatMoney.format((double) listHisto.getTotalTransaction() / 100)
				+ " €");
		caisse.setText("Caisse : " + Model.doubleFormatMoney.format((double) model.getUserSold(-1) / 100) + " €");
		resizeColumnWidth(table);
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Min width
			int widthMax = 175; // Max width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
				width = Math.min(width, widthMax);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

}
