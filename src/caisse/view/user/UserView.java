package caisse.view.user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import caisse.Model;
import caisse.member.Users;

public class UserView extends JPanel implements Observer{

	protected Model model;
	protected JTable usersTable;
	protected JButton addUser;
	
	public UserView(final Model model) {
		this.model = model;
		
		this.setLayout(new BorderLayout());
		this.usersTable = new JTable(model.getUsers());
		addUser = new JButton("ajouter un nouvel utilisateur");
		addUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddUserView(model);
			}
		});
		JScrollPane scrollPane = new JScrollPane(usersTable);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.add(addUser,BorderLayout.SOUTH);
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		model.getUsers().fireTableChanged(null);
	}

}
