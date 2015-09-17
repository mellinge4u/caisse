package caisse.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.tools.IdSpinner;

public class ViewUserDetails extends JDialog {

	protected Model model;
	protected JTable table;
	protected IdSpinner id;
	protected JTextField name;
	protected JTextField firstname;
	protected JSpinner birthDay;
	protected JSpinner birthMonth;
	protected JSpinner birthYear;
	protected JLabel phone; // TODO phone number a faire !!!
	protected JLabel sexe;
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
		this.model = model;
		User u = model.getUserById(userId);

		this.setLayout(new BorderLayout());
		JPanel center = new JPanel(new BorderLayout());
		JPanel ctrl = new JPanel();
		JPanel details = new JPanel();

		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		table = new JTable(new TableModelUserHistoric(model, userId));
		JScrollPane scrollPane = new JScrollPane(table);
		center.add(details, BorderLayout.NORTH);
		center.add(scrollPane, BorderLayout.CENTER);

		int col = 10;
		id = new IdSpinner();
		id.setValue(userId);
		id.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				update((int) id.getValue());
			}
		});
		name = new JTextField(u.getName(), col);
		name.setEditable(edit);
		firstname = new JTextField(u.getFirstname(), col);
		firstname.setEditable(edit);
		Calendar cal = Calendar.getInstance();
		cal.setTime(u.getBirthDate());
		birthDay = new JSpinner(); // TODO gérer les date de naiss
		birthDay.setValue(cal.get(Calendar.DAY_OF_MONTH));
		birthDay.setEnabled(edit);
		birthMonth = new JSpinner(); // TODO gérer les date de naiss
		birthMonth.setValue(cal.get(Calendar.MONTH));
		birthMonth.setEnabled(edit);
		birthYear = new JSpinner(); // TODO gérer les date de naiss
		birthYear.setValue(cal.get(Calendar.YEAR));
		birthYear.setEnabled(edit);
		JPanel birthDate = new JPanel(new GridLayout(1, 3));
		birthDate.add(birthDay);
		birthDate.add(birthMonth);
		birthDate.add(birthYear);
		sexe = new JLabel();
		if (u.isMan()) {
			sexe.setText("Homme");
		} else {
			sexe.setText("Femme");
		}
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
		newsLetter.setEnabled(edit);

		JPanel detailsLeft = new JPanel(new GridLayout(7, 2));
		JPanel detailsRightR = new JPanel(new GridLayout(5, 1, 0, 8));
		JPanel detailsRightL = new JPanel(new GridLayout(5, 1));
		details.add(detailsLeft, BorderLayout.WEST);
		details.add(detailsRightR, BorderLayout.CENTER);
		details.add(detailsRightL, BorderLayout.EAST);

		detailsLeft.add(new JLabel("Id : "));
		detailsLeft.add(id);
		detailsLeft.add(new JLabel("Nom : "));
		detailsLeft.add(name);
		detailsLeft.add(new JLabel("Prenom : "));
		detailsLeft.add(firstname);
		detailsLeft.add(new JLabel("Date de naissance : "));
		detailsLeft.add(birthDate);
		detailsLeft.add(new JLabel("Sexe : "));
		detailsLeft.add(sexe);
		detailsLeft.add(new JLabel("Filière : "));
		detailsLeft.add(studdies);
		detailsLeft.add(new JLabel("Solde : "));
		DecimalFormat df = new DecimalFormat("#0.00");
		detailsLeft.add(new JLabel(df.format((double) u.getAccount() / 100)
				+ " €"), BorderLayout.EAST);

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

	public void update(int userId) {
		table.setModel(new TableModelUserHistoric(model, userId));
		;
		User u = model.getUserById(userId);
		if (u == null) {
			name.setText("...");
			firstname.setText("...");
			birthDay.setValue(1);
			birthMonth.setValue(1);
			birthYear.setValue(1900);
			// phone; // TODO phone number a faire !!!
			sexe.setText("...");
			studdies.setText("...");
			mailStreet.setText("...");
			mailPostalCode.setText(".....");
			mailTown.setText("...");
			eMail.setText("...");
			newsLetter.setSelected(false);
		} else {
			name.setText(u.getName());
			firstname.setText(u.getFirstname());
			Calendar cal = Calendar.getInstance();
			cal.setTime(u.getBirthDate());
			birthDay.setValue(cal.get(Calendar.DAY_OF_MONTH));
			birthMonth.setValue(cal.get(Calendar.MONTH));
			birthYear.setValue(cal.get(Calendar.YEAR));
			// phone; // TODO phone number a faire !!!
			if (u.isMan()) {
				sexe.setText("Homme");
			} else {
				sexe.setText("Femme");
			}
			studdies.setText(u.getStudies());
			mailStreet.setText(u.getMailStreet());
			mailPostalCode.setText(u.getMailPostalCode());
			mailTown.setText(u.getMailTown());
			eMail.setText(u.getEMail());
			newsLetter.setSelected(u.isNewsLetter());
		}
	}

}
