package caisse.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import caisse.Model;
import caisse.listener.CloseListener;
import caisse.tools.IdSpinner;

public class ViewNewUser extends JDialog {

	protected Model model;
	protected IdSpinner id;
	protected JTextField name;
	protected JTextField firstname;
	protected JRadioButton man;
	protected JRadioButton woman;
	protected JSpinner day;
	protected JSpinner month;
	protected JSpinner year;
	protected JTextField filiere;
	protected JTextField mailStreet;
	protected JTextField mailPostalCode;
	protected JTextField mailTown;
	protected JTextField eMail;
	protected JCheckBox news;
	protected JButton accept;
	protected JButton cancel;

	public ViewNewUser(Model model, JFrame parent) {
		super((JFrame) parent, "Nouvel adherent", true);
		this.model = model;
		this.setResizable(false);

		id = new IdSpinner();
		name = new JTextField();
		firstname = new JTextField();
		ButtonGroup group = new ButtonGroup();
		man = new JRadioButton("Homme");
		man.setSelected(true);
		group.add(man);
		woman = new JRadioButton("Femme");
		group.add(woman);
		day = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
		month = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
		Calendar cal = Calendar.getInstance();
		year = new JSpinner(new SpinnerNumberModel(1990, 1900, cal.get(Calendar.YEAR), 1));
		filiere = new JTextField();
		mailStreet = new JTextField();
		mailStreet.setColumns(25);
		mailPostalCode = new JTextField();
		mailTown = new JTextField();
		eMail = new JTextField();
		news = new JCheckBox();
		accept = new JButton("Valider");
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		cancel = new JButton("Anuler");
		cancel.addActionListener(new CloseListener(this));

		JPanel center = new JPanel(new GridLayout(11, 2));
		JPanel ctrl = new JPanel();
		JPanel datePanel = new JPanel(new GridLayout(1, 3));
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
		center.add(new JLabel("Sexe : "));
		center.add(sexePanel);
		center.add(new JLabel("Date de naissance : "));
		center.add(datePanel);
		center.add(new JLabel("Filiere : "));
		center.add(filiere);
		center.add(new JLabel("Adresse : ")); // TODO accent
		center.add(mailStreet);
		center.add(new JLabel("    (Code postal)"));
		center.add(mailPostalCode);
		center.add(new JLabel("    (Commune)"));
		center.add(mailTown);
		center.add(new JLabel("Adresse e-mail : "));
		center.add(eMail);
		center.add(new JLabel("Newsletter : "));
		center.add(news);

		sexePanel.add(man);
		sexePanel.add(woman);
		
		datePanel.add(day);
		datePanel.add(month);
		datePanel.add(year);
		
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
