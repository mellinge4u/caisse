package caisse.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import caisse.Model;
import caisse.member.Users;

public class UserView extends JPanel implements Observer{

	protected Model model;
	protected JTable usersTable;
	protected JButton addUser;
	protected Users users;
	
	public UserView(final Model model) {
		this.model = model;
		users = new Users();
		this.usersTable = new JTable(users);
		addUser = new JButton("ajouter un nouvel utilisateur");
		addUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddUserView(model);
			}
		});
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
