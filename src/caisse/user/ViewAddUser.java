package caisse.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import caisse.Model;
import caisse.listener.CloseListener;

public class ViewAddUser extends JFrame {

	protected Model model;
	protected User user;
	protected JTextField id;
	protected JTextField name;
	protected JTextField fName;
	protected JButton accept;
	protected JButton cancel;

	public ViewAddUser(Model mod) {
		super("Nouvelle utilisateur");
		model = mod;
		this.setLayout(new BorderLayout());

		id = new JTextField("ID etu");
		name = new JTextField("Nom");
		fName = new JTextField("Prenom");

		this.add(id, BorderLayout.WEST);
		this.add(name, BorderLayout.CENTER);
		this.add(fName, BorderLayout.EAST);

		JPanel control = new JPanel();
		control.setLayout(new GridLayout(1, 2));
		accept = new JButton("Valider");
		control.add(accept);
		cancel = new JButton("Annuler");
		final JFrame f = this;
		accept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.addUser(name.getText(), fName.getText(), Integer.parseInt(id.getText()));
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
