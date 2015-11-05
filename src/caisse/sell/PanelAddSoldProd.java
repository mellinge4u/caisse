package caisse.sell;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import caisse.Model;
import caisse.sellProcuct.SoldProduct;

public class PanelAddSoldProd extends JPanel implements Observer {

	private JButton accept;
	private JButton cancel;
	private ArrayList<JTable> tables;
	private ArrayList<TableModelSelectProduct> tableModels;
	private boolean ctrlPressed;

	public PanelAddSoldProd(JButton remove) {
		Model.getInstance().addObserver(this);
		ctrlPressed = false;

		tables = new ArrayList<JTable>();
		tableModels = new ArrayList<TableModelSelectProduct>();
		accept = new JButton("Ajouter");
		cancel = new JButton("Désélectionner");

		KeyListener kl = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == 17) {
					ctrlPressed = false;
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 17) {
					ctrlPressed = true;
				}
			}
		};

		class MousL implements MouseListener {

			private JTable table;

			public MousL(JTable table) {
				this.table = table;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (!ctrlPressed) {
					resetSelectionWithout(table);
				}
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
					setSelection();
				}
			}
		}
		;

		String[] colNames = {"Nourriture", "Boissons", "Divers"};
		ArrayList<JScrollPane> scrolls = new ArrayList<JScrollPane>();
		tableModels.add(new TableModelSelectProduct(SoldProduct.prodType.FOOD));
		tableModels
				.add(new TableModelSelectProduct(SoldProduct.prodType.DRINK));
		tableModels.add(new TableModelSelectProduct(SoldProduct.prodType.MISC));
		for (TableModelSelectProduct tm : tableModels) {
			JTable t = new JTable(tm);
			tables.add(t);
			scrolls.add(new JScrollPane(t));
			t.addMouseListener(new MousL(t));
			t.addKeyListener(kl);
		}

		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSelection();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetSelection();
			}
		});

		this.setLayout(new BorderLayout());
		JPanel ctrl = new JPanel();
		JPanel tabs = new JPanel(new GridLayout(1, tables.size()));
		JPanel tabsName = new JPanel(new GridLayout(1, tables.size()));
		this.add(tabsName, BorderLayout.NORTH);
		this.add(tabs, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		int i = 0;
		for (JScrollPane sp : scrolls) {
			JPanel name = new JPanel(new BorderLayout());
			tabsName.add(name);
			JLabel lName= new JLabel(colNames[i]);
			lName.setHorizontalAlignment(SwingConstants.CENTER);
			name.add(lName, BorderLayout.CENTER);
			name.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
			tabs.add(sp);
			i++;
		}

		ctrl.add(accept);
		ctrl.add(remove);
		ctrl.add(cancel);

		for (JTable t : tables) {
			resizeColumnWidth(t);
		}
	}

	private void resetSelection() {
		for (JTable t : tables) {
			t.clearSelection();
		}
	}

	private void resetSelectionWithout(JTable table) {
		for (JTable t : tables) {
			if (!t.equals(table)) {
				t.clearSelection();
			}
		}
	}

	private void setSelection() {
		Model model = Model.getInstance();
		for (JTable t : tables) {
			int[] val = t.getSelectedRows();
			for (int i = 0; i < val.length; i++) {
				model.addProductOnCurrentTransaction(((TableModelSelectProduct) t
						.getModel()).getProduct(val[i]));
			}
		}
		resetSelection();
	}

	@Override
	public void update(Observable o, Object arg) {
		for (JTable t : tables) {
			TableModelSelectProduct tm = (TableModelSelectProduct) t.getModel();
			tm.updateArrayList();
			for (int i = 0; i < tm.getColumnCount(); i++) {
				t.getColumnModel().getColumn(i).setCellRenderer(tm.getColumnModel(i));
			}
			resizeColumnWidth(t);
		}
	}

	private void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 30; // Min width
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