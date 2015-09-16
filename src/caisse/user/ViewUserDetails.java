package caisse.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import caisse.Model;
import caisse.listener.CloseListener;

public class ViewUserDetails extends JDialog {

	public ViewUserDetails(Model model, JFrame parent, int userId) {
		super((JFrame) parent, "Adherent", true);
		this.setResizable(false);
		User u = model.getUserById(userId);
		
		this.setLayout(new BorderLayout());
		JPanel center = new JPanel(new BorderLayout());
		JPanel ctrl = new JPanel();
		JPanel details = new JPanel(new GridLayout(8, 4));
		
		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		JTable table = new JTable();	// TODO Ajouter un model
		JScrollPane scrollPane = new JScrollPane(table);
		center.add(details, BorderLayout.NORTH);
		center.add(scrollPane, BorderLayout.CENTER);
		
		details.add(new JLabel("Id : "));
		details.add(new JLabel("" + userId));
		 details.add(new JLabel("Adresse : "));
		 details.add(new JLabel(u.getMail()));
		details.add(new JLabel("Nom : "));
		details.add(new JLabel(u.getName()));
		 details.add(new JLabel(""));
		 details.add(new JLabel("..."));
		details.add(new JLabel("Prenom : "));
		details.add(new JLabel(u.getFirstname()));
		 details.add(new JLabel(""));
		 details.add(new JLabel("..."));
		details.add(new JLabel("Date de naissance : "));
		details.add(new JLabel(User.df.format(u.getBirthDate()))); // TODO ajouter majorité 
		 details.add(new JLabel("Adresse e-Mail : "));
		 details.add(new JLabel(u.geteMail()));
		details.add(new JLabel("Sexe : "));
		details.add(new JLabel("Homme/Femme")); // TODO
		 details.add(new JLabel("NewsLetter : "));
		 details.add(new JLabel("" + u.isNewsLetter()));
		details.add(new JLabel("Filière : "));
		details.add(new JLabel(u.getStudies()));
		 details.add(new JLabel(""));
		 details.add(new JLabel(""));
		details.add(new JLabel(""));
		details.add(new JLabel(""));
		 details.add(new JLabel(""));
		 details.add(new JLabel(""));
		details.add(new JLabel("Solde : "));
		DecimalFormat df = new DecimalFormat("#0.00");
		details.add(new JLabel(df.format((double) u.getAccount() / 100) + " €"));
					
		JButton ok = new JButton("Ok");
		ok.addActionListener(new CloseListener(this));
		ctrl.add(ok);
		
		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
