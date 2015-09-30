package caisse.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.tools.IdSpinner;
import caisse.tools.NumberSpinner;

public class ViewNewUser extends JDialog {

	protected Model model;
	protected IdSpinner id;
	protected JTextField name;
	protected JTextField firstname;
	protected JRadioButton man;
	protected JRadioButton woman;
	protected NumberSpinner day;
	protected NumberSpinner month;
	protected NumberSpinner year;
	protected JTextField filiere;
	protected JTextField mailStreet;
	protected JTextField mailPostalCode;
	protected JTextField mailTown;
	protected JTextField eMail;
	protected JTextField phoneNum;
	protected JCheckBox news;
	protected JButton accept;
	protected JButton cancel;

	public ViewNewUser(final Model model, JFrame parent) {
		super((JFrame) parent, "Nouvel adherent", true);
		final Window window = this;
		this.model = model;
		this.setResizable(false);

		id = new IdSpinner();
		id.setValue(model.getNewId());
		name = new JTextField();
		firstname = new JTextField();
		ButtonGroup group = new ButtonGroup();
		man = new JRadioButton("Homme");
		man.setSelected(true);
		group.add(man);
		woman = new JRadioButton("Femme");
		group.add(woman);
		Calendar calBirthStart = Calendar.getInstance();
		calBirthStart.set(Calendar.DAY_OF_MONTH, 1);
		calBirthStart.set(Calendar.MONTH, 0);
		calBirthStart.add(Calendar.YEAR, -20);
		final JDateChooser birthDate = new JDateChooser(calBirthStart.getTime(), "dd/MM/yyyy");
		filiere = new JTextField();
		mailStreet = new JTextField();
		mailStreet.setColumns(25);
		mailPostalCode = new JTextField();
		mailTown = new JTextField();
		eMail = new JTextField();
		news = new JCheckBox("Newsletter");
		phoneNum = new JTextField();
		accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.addUser((int) id.getValue(), name.getText(),
						firstname.getText(), man.isSelected(), birthDate.getDate(),
						phoneNum.getText(), filiere.getText(),
						mailStreet.getText(), mailPostalCode.getText(),
						mailTown.getText(), eMail.getText(), news.isSelected());
				window.dispose();
			}
		});
		cancel = new JButton("Anuler");
		cancel.addActionListener(new CloseListener(this));

		
		
		JPanel center = new JPanel(new GridLayout(12, 2));
		JPanel ctrl = new JPanel();
		JPanel sexePanel = new JPanel(new GridLayout(1, 2));

		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		center.add(new JLabel("Id : "));
		center.add(id);
		center.add(new JLabel("Nom : "));
		center.add(name);
		center.add(new JLabel("Prenom : ")); // TODO accent
		center.add(firstname);
		center.add(new JLabel("Date de naissance : "));
		center.add(birthDate);
		center.add(new JLabel("Sexe : "));
		center.add(sexePanel);
		center.add(new JLabel("Adresse : ")); // TODO accent
		center.add(mailStreet);
		center.add(new JLabel("    (Code postal)"));
		center.add(mailPostalCode);
		center.add(new JLabel("    (Commune)"));
		center.add(mailTown);
		center.add(new JLabel("Telephone : ")); // TODO accent
		center.add(phoneNum);
		center.add(new JLabel("Adresse e-mail : "));
		center.add(eMail);
		center.add(new JLabel("Newsletter : "));
		center.add(news);
		center.add(new JLabel("Filiere : "));
		center.add(filiere);

		sexePanel.add(man);
		sexePanel.add(woman);

		ctrl.add(accept);
		ctrl.add(cancel);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

}
