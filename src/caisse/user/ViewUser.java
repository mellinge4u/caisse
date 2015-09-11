package caisse.user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
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

	public ViewUser(final Model model) {
		this.model = model;

		this.setLayout(new BorderLayout());
		tableModel = model.getUsers();
		this.usersTable = new JTable(tableModel);
		addUser = new JButton("ajouter un nouvel utilisateur");
		addUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewAddUser(model);
			}
		});
		cellRender = new CellRender();
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			usersTable.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
		JScrollPane scrollPane = new JScrollPane(usersTable);
		this.add(scrollPane, BorderLayout.CENTER);

		this.add(addUser, BorderLayout.SOUTH);
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
