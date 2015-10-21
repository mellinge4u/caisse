package caisse.user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import caisse.Model;
import caisse.historic.ViewTransactionDetails;
import caisse.tools.CellRender;
import caisse.tools.CellRenderColorPrice;

public class ViewUser extends JPanel implements Observer {

	protected Model model;
	protected JTable usersTable;
	protected TableModelUser tableModel;
	protected JButton addUser;
	protected JButton detailUser;
	protected CellRender cellRender;
	protected CellRenderColorPrice cellRenderColor;

	public ViewUser(final Model model, final JFrame parent) {
		this.model = model;

		tableModel = model.getUsers();
		this.usersTable = new JTable(tableModel);
		usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		usersTable.addMouseListener(new MouseListener() {
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
					viewDetails(parent);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(usersTable);
		addUser = new JButton("Ajouter un nouvel adherent"); // TODO accents
		addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewNewUser(model, parent);
			}
		});
		detailUser = new JButton("Details d'un adherent"); // TODO accents
		detailUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewDetails(parent);
			}
		});
		cellRender = new CellRender(false);
		cellRenderColor = new CellRenderColorPrice(false);
		JButton mailList = new JButton("Visualiser la mail-list");
		mailList.setEnabled(false);

		JPanel ctrl = new JPanel();

		this.setLayout(new BorderLayout());

		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		ctrl.add(addUser);
		ctrl.add(detailUser);
		ctrl.add(mailList);

		model.addObserver(this);
	}

	public void viewDetails(JFrame parent) {
		int row = usersTable.getSelectedRow();
		int id = (int) tableModel.getValueAt(row, 0);
		new ViewUserDetails(model, parent, id);
	}

	@Override
	public void update(Observable o, Object arg) {
		model.getUsers().fireTableChanged(null);
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			if (i == 3) {
				usersTable.getColumnModel().getColumn(i)
				.setCellRenderer(cellRenderColor);
			} else {
				usersTable.getColumnModel().getColumn(i)
						.setCellRenderer(cellRender);
			}
		}
	}

}
