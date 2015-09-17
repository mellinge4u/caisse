package caisse.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import caisse.Model;
import caisse.listener.CloseListener;

public class ViewUserDetails extends JDialog {

	protected JTextField name;
	protected JTextField firstname;
	protected JTextField birthDate;
	protected JTextField studdies;
	protected JTextField mailStreet;
	protected JTextField mailPostalCode;
	protected JTextField mailTown;
	protected JTextField eMail;
	protected JCheckBox newsLetter;
	protected boolean edit = false;
	
	public ViewUserDetails(Model model, JFrame parent, int userId) {
		super((JFrame) parent, "Adherent", true);
		this.setResizable(false);
		User u = model.getUserById(userId);
		
		this.setLayout(new BorderLayout());
		JPanel center = new JPanel(new BorderLayout());
		JPanel ctrl = new JPanel();
		// JPanel details = new JPanel(new BorderLayout());
		JPanel details = new JPanel();
		
		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		JTable table = new JTable();	// TODO Ajouter un model
		JScrollPane scrollPane = new JScrollPane(table);
		center.add(details, BorderLayout.NORTH);
		center.add(scrollPane, BorderLayout.CENTER);
		
		int col = 10;
		name = new JTextField(u.getName(), col);
		name.setEditable(edit);
		firstname = new JTextField(u.getFirstname(), col);
		firstname.setEditable(edit);
		birthDate = new JTextField(col); // TODO gérer les date de naiss
		birthDate.setEditable(edit);
		studdies = new JTextField(u.getStudies(), col);
		studdies.setEditable(edit);
		col = 30;
		mailStreet = new JTextField(u.getMailStreet(), col);
		mailStreet.setEditable(edit);
		mailPostalCode = new JTextField(u.getMailPostalCode(), col);
		mailPostalCode.setEditable(edit);
		mailTown = new JTextField(u.getMailTown(), col);
		mailTown.setEditable(edit);
		eMail = new JTextField(u.getEMail(), col);
		eMail.setEditable(edit);
		newsLetter = new JCheckBox("newsLetter");
		newsLetter.setSelected(u.isNewsLetter());
		newsLetter.setEnabled(edit);;
		
		
		JPanel detailsLeft = new JPanel(new GridLayout(7,  2));
		JPanel detailsRightR= new JPanel(new GridLayout(5,  1, 0, 8));
		JPanel detailsRightL = new JPanel(new GridLayout(5,  1));
		details.add(detailsLeft, BorderLayout.WEST);
		details.add(detailsRightR, BorderLayout.CENTER);
		details.add(detailsRightL, BorderLayout.EAST);
		
		JLabel lid = new JLabel("Id : ");
//		detailsLeft.add(new JLabel("Id : "));
		detailsLeft.add(lid);
		detailsLeft.add(new JLabel("" + userId));
		detailsLeft.add(new JLabel("Nom : "));
		detailsLeft.add(name);
		detailsLeft.add(new JLabel("Prenom : "));
		detailsLeft.add(firstname);
		detailsLeft.add(new JLabel("Date de naissance : "));
		detailsLeft.add(new JLabel(User.df.format(u.getBirthDate()))); // TODO ajouter majorité 
		detailsLeft.add(new JLabel("Sexe : "));
		if (u.isMan()) {
			detailsLeft.add(new JLabel("Homme"));
		} else {
			detailsLeft.add(new JLabel("Femme"));
		}
		detailsLeft.add(new JLabel("Filière : "));
		detailsLeft.add(studdies);
		detailsLeft.add(new JLabel("Solde : "));
		DecimalFormat df = new DecimalFormat("#0.00");
		detailsLeft.add(new JLabel(df.format((double) u.getAccount() / 100) + " €"), BorderLayout.EAST);

		
		detailsRightR.add(new JLabel("Adresse : "));
		detailsRightL.add(mailStreet);
		detailsRightR.add(new JLabel("    (Code postal)"));
		detailsRightL.add(mailPostalCode);
		detailsRightR.add(new JLabel("    (Commune)"));
		detailsRightL.add(mailTown);
		detailsRightR.add(new JLabel("Adresse e-Mail : "));
		detailsRightL.add(eMail);
		detailsRightR.add(new JLabel("NewsLetter : "));
		detailsRightL.add(newsLetter);
					
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
