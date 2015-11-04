package caisse.historic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import caisse.Model;

public class ViewHistoric extends JPanel implements Observer {

	protected Model model;
	protected JTable table;
	protected TableModelHistoric listHisto;

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
					new ViewTransactionDetails(model, parent, listHisto
							.getDisplayTransaction(table.getSelectedRow()));
				}
			}
		});

		JTextField green = new JTextField("Vente");
		JTextField blue = new JTextField("Dépots");
		JTextField yellow = new JTextField("Courses");
		JTextField red = new JTextField("Retrait Stock");
		JTextField cyan = new JTextField("Ajout Stock");
		green.setEditable(false);
		green.setBackground(Model.GREEN);
		green.setHorizontalAlignment(SwingConstants.CENTER);
		blue.setEditable(false);
		blue.setBackground(Model.BLUE);
		blue.setHorizontalAlignment(SwingConstants.CENTER);
		yellow.setEditable(false);
		yellow.setBackground(Model.YELLOW);
		yellow.setHorizontalAlignment(SwingConstants.CENTER);
		red.setEditable(false);
		red.setBackground(Model.RED);
		red.setHorizontalAlignment(SwingConstants.CENTER);
		cyan.setEditable(false);
		cyan.setBackground(Model.CYAN);
		cyan.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(table);
		JPanel ctrl = new JPanel(new BorderLayout());
		JPanel legend = new JPanel(new GridLayout(1, 5));
		JPanel mainView = new JPanel(new BorderLayout());

		this.add(mainView, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		legend.add(green);
		legend.add(blue);
		legend.add(yellow);
		legend.add(red);
		legend.add(cyan);
		
		mainView.add(scrollPane, BorderLayout.CENTER);
		mainView.add(legend, BorderLayout.SOUTH);
		ctrl.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		ctrl.add(new HistoricSelector(listHisto), BorderLayout.CENTER);

	}

	@Override
	public void update(Observable o, Object arg) {
		listHisto.fireTableDataChanged();
		for (int i = 0; i < listHisto.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(listHisto.getColumnModel(i));
		}
		resizeColumnWidth(table);
	}

	private void resizeColumnWidth(JTable table) {
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
