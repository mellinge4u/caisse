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
import caisse.tools.CellRender;
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
	protected JPanel panelBirth;
	protected JLabel lBirthDate;
	protected JTextField tel;
	protected JLabel sexe;
	protected JTextField studdies;
	protected JTextField mailStreet;
	protected JTextField mailPostalCode;
	protected JTextField mailTown;
	protected JTextField eMail;
	protected JCheckBox newsLetter;
	protected JLabel sold;
	protected boolean edit = false;

	public ViewUserDetails(Model model, JFrame parent, int userId) {
		super((JFrame) parent, "Adherent", true);
		this.setResizable(false);
		this.model = model;
		User u = model.getUserById(userId);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		JButton ok = new JButton("Ok");
		ok.addActionListener(new CloseListener(this));

		int col = 10;
		id = new IdSpinner();
		id.setValue(userId);
		id.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				update((int) id.getValue());
			}
		});

		name = new JTextField(col);
		firstname = new JTextField(col);
		panelBirth = new JPanel(new GridLayout(1, 3));
		birthDay = new JSpinner();
		birthMonth = new JSpinner();
		birthYear = new JSpinner();
		lBirthDate = new JLabel();
		if (edit) {
			panelBirth.add(birthDay);
			panelBirth.add(birthMonth);
			panelBirth.add(birthYear);
		} else {
			panelBirth.add(lBirthDate);
		}
		sexe = new JLabel();
		studdies = new JTextField(col);
		col = 30;
		mailStreet = new JTextField(col);
		mailPostalCode = new JTextField(col);
		mailTown = new JTextField(col);
		eMail = new JTextField(col);
		newsLetter = new JCheckBox("newsLetter");
		sold = new JLabel();
		tel = new JTextField();
		
		JPanel center = new JPanel(new BorderLayout());
		JPanel ctrl = new JPanel();
		JPanel details = new JPanel();
		JPanel detailsLeft = new JPanel(new GridLayout(6, 2));
		JPanel detailsRightR = new JPanel(new GridLayout(6, 1, 0, 8));
		JPanel detailsRightL = new JPanel(new GridLayout(6, 1));

		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		center.add(details, BorderLayout.NORTH);
		center.add(scrollPane, BorderLayout.CENTER);

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
		detailsLeft.add(panelBirth);
		detailsLeft.add(new JLabel("Sexe : "));
		detailsLeft.add(sexe);
		detailsLeft.add(new JLabel("Filière : "));
		detailsLeft.add(studdies);
//		detailsLeft.add(new JLabel("Solde : "));
//		detailsLeft.add(sold);

		detailsRightR.add(new JLabel("Adresse : "));
		detailsRightL.add(mailStreet);
		detailsRightR.add(new JLabel("    (Code postal)"));
		detailsRightL.add(mailPostalCode);
		detailsRightR.add(new JLabel("    (Commune)"));
		detailsRightL.add(mailTown);
		detailsRightR.add(new JLabel("Adresse e-Mail : "));
		detailsRightL.add(eMail);
		detailsRightR.add(new JLabel());
		detailsRightL.add(newsLetter);
		detailsRightR.add(new JLabel("Numero de Tel : "));
		detailsRightL.add(tel);

		ctrl.add(ok);

		update(userId);

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

	public void update(int userId) {
		User u = model.getUserById(userId);
		table.setModel(new TableModelUserHistoric(model, userId));
		CellRender cellRender = new CellRender();
		for (int i = 0; i < table.getModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}

		name.setEditable(edit);
		firstname.setEditable(edit);
		panelBirth.removeAll();
		if (edit) {
			panelBirth.add(birthDay);
			panelBirth.add(birthMonth);
			panelBirth.add(birthYear);
		} else {
			panelBirth.add(lBirthDate);
		}
		studdies.setEditable(edit);
		mailStreet.setEditable(edit);
		mailPostalCode.setEditable(edit);
		mailTown.setEditable(edit);
		eMail.setEditable(edit);
		newsLetter.setEnabled(edit);
		tel.setEditable(edit);
		
		if (u == null) {
			name.setText("...");
			firstname.setText("...");
			birthDay.setValue(1);
			birthMonth.setValue(1);
			birthYear.setValue(1900);
			lBirthDate.setText("../../....");
			sexe.setText("...");
			studdies.setText("...");
			mailStreet.setText("...");
			mailPostalCode.setText(".....");
			mailTown.setText("...");
			eMail.setText("...");
			newsLetter.setSelected(false);
			tel.setText(".. .. .. .. ..");
			sold.setText("0.00 €");
		} else {
			name.setText(u.getName());
			firstname.setText(u.getFirstname());
			Calendar cal = Calendar.getInstance();
			cal.setTime(u.getBirthDate());
			birthDay.setValue(cal.get(Calendar.DAY_OF_MONTH));
			birthMonth.setValue(cal.get(Calendar.MONTH));
			birthYear.setValue(cal.get(Calendar.YEAR));
			lBirthDate.setText(Model.dateFormatSimple.format(u.getBirthDate()));
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
			tel.setText(u.getPhoneNumber());

			DecimalFormat df = new DecimalFormat("#0.00");
			sold.setText(df.format((double) u.getAccount() / 100) + " €");
		}
	}

}
