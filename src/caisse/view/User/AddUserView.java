package caisse.view.User;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import caisse.Model;
import caisse.listener.AddProductOnTransactionListener;
import caisse.listener.CloseListener;
import caisse.member.User;

public class AddUserView extends JFrame {

	protected Model model;
	protected User user;
	protected JTextField id;
	protected JTextField name;
	protected JTextField fName;
	protected JButton accept;
	protected JButton cancel;

	public AddUserView(Model mod) {
		super("Nouvelle utilisateur");
		model = mod;
		this.setLayout(new BorderLayout());

		id = new JTextField("ID etu");
		name = new JTextField("Nom");
		fName = new JTextField("Prenom");
		
		this.add(id,BorderLayout.WEST);
		this.add(name,BorderLayout.CENTER);
		this.add(fName,BorderLayout.EAST);
		
		user = new User("", "", 0);
		JPanel control = new JPanel();
		control.setLayout(new GridLayout(1, 2));
		accept = new JButton("Valider");
		control.add(accept);
		cancel = new JButton("Annuler");
		final JFrame f = this;
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				user.setUserNumber(Integer.parseInt(id.getText()));
				user.setName(name.getText());
				user.setFirstname(fName.getText());
				model.addUser(user);
				f.dispose();
			}
		});
		control.add(cancel);
		cancel.addActionListener(new CloseListener(this));
		this.add(control, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

}
