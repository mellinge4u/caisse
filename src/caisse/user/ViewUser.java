package caisse.user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;
import caisse.tools.CellRender;

public class ViewUser extends JPanel implements Observer {

	protected Model model;
	protected JTable usersTable;
	protected TableModelUser tableModel;
	protected JButton addUser;
	protected CellRender cellRender;

	public ViewUser(final Model model, final JFrame parent) {
		this.model = model;
		
		tableModel = model.getUsers();
		this.usersTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(usersTable);
		addUser = new JButton("Ajouter un nouvel adherent"); // TODO accents
		addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewNewUser(model, parent);
			}
		});
		cellRender = new CellRender();
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			usersTable.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}

		JPanel ctrl = new JPanel();
		
		this.setLayout(new BorderLayout());

		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		ctrl.add(addUser);

		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		model.getUsers().fireTableChanged(null);
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			usersTable.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
	}

}
