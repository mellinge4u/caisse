package caisse.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sun.misc.JavaLangAccess;

import com.toedter.calendar.JDateChooser;

import caisse.Model;
import caisse.historic.HistoricSelector;
import caisse.historic.ViewTransactionDetails;
import caisse.listener.CloseListener;
import caisse.tools.CellRender;
import caisse.tools.IdSpinner;
import caisse.tools.MonetarySpinner;

public class ViewUserDetails extends JDialog {

	private Model model;
	private JTable table;
	private TableModelUserHistoric tableModel;
	private IdSpinner id;
	private JTextField name;
	private JTextField firstname;
	private JDateChooser birthDate;
	private JPanel pBirth;
	private JLabel lBirthDate;
	private JTextField tel;
	private JPanel pSexe;
	private JLabel lSexe;
	private JButton bSexe;
	private JTextField studies;
	private JTextField mailStreet;
	private JTextField mailPostalCode;
	private JTextField mailTown;
	private JTextField eMail;
	private JCheckBox newsLetter;
	private JLabel sold;
	private JButton bDeposit;
	private MonetarySpinner sDeposit;
	private JButton bEdit;
	private JButton bEditValid;
	private boolean depositOn;
	private boolean edit = false;
	private boolean newSexe;

	public ViewUserDetails(final Model model, final JFrame parent,
			final int userId) {
		super((JFrame) parent, "Adherent", true);
		this.setResizable(false);
		this.model = model;
		newSexe = Model.getInstance().getUserById(userId).isMan();
		
		table = new JTable();
		tableModel = new TableModelUserHistoric(userId);
		table.setModel(tableModel);
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new ViewTransactionDetails(model, parent, tableModel
							.getTransaction(table.getSelectedRow()));
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, table
				.getRowHeight() * (tableModel.getRowCount() + 12) - 9));
		JButton ok = new JButton("Ok");
		ok.addActionListener(new CloseListener(this));

		int col = 10;
		id = new IdSpinner();
		id.setValue(userId);
		id.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateId();
			}
		});

		name = new JTextField(col);
		firstname = new JTextField(col);
		pBirth = new JPanel(new GridLayout(1, 1));
		birthDate = new JDateChooser(new Date(), "dd/MM/yyyy");
		lBirthDate = new JLabel();
		pBirth.add(lBirthDate);
		pSexe = new JPanel(new GridLayout(1, 1));
		lSexe = new JLabel();
		bSexe = new JButton();
		pSexe.add(lSexe);
		bSexe.setPreferredSize(name.getPreferredSize());
		bSexe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newSexe = !newSexe;
				updateSexe();
			}
		});
		studies = new JTextField(col);
		col = 30;
		mailStreet = new JTextField(col);
		mailPostalCode = new JTextField(col);
		mailTown = new JTextField(col);
		eMail = new JTextField(col);
		newsLetter = new JCheckBox("newsLetter");
		sold = new JLabel();
		tel = new JTextField();
		depositOn = false;
		bDeposit = new JButton("Dépôt");
		bDeposit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				depositOn = !depositOn;
				sDeposit.setVisible(depositOn);
				if (depositOn) {
					bDeposit.setText("Valider");
				} else {
					bDeposit.setText("Dépôt");
					int dep = sDeposit.getIntValue();
					if (dep > 0) {
						model.deposit((int) id.getValue(),
								sDeposit.getIntValue());
						sold.setText(Model.doubleFormatMoney
								.format((double) model.getUserSold((int) id
										.getValue()) / 100)
								+ " €");
					}
					sDeposit.setValue(0.0);
				}
			}
		});
		sDeposit = new MonetarySpinner(5.0);
		sDeposit.setVisible(depositOn);
		bEdit = new JButton("Editer");
		bEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				edit = !edit;
				update();
			}
		});
		bEditValid = new JButton("Valider");
		bEditValid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editProfil();
				edit = false;
				update();
			}
		});

		HistoricSelector hSelect = new HistoricSelector(tableModel);
		JPanel center = new JPanel(new BorderLayout());
		JPanel ctrl = new JPanel(new BorderLayout());
		JPanel ctrlCenter = new JPanel();
		JPanel details = new JPanel(new BorderLayout());
		JPanel detailsLeft = new JPanel(new GridLayout(6, 2));
		JPanel detailsRightR = new JPanel(new GridLayout(6, 1, 0, 8));
		JPanel detailsRightL = new JPanel(new GridLayout(6, 1));
		JPanel detailsDown = new JPanel(new BorderLayout());
		JPanel detailsDDown = new JPanel();
		JPanel detailsEdit = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		center.add(details, BorderLayout.NORTH);
		center.add(scrollPane, BorderLayout.CENTER);
		center.add(detailsDown, BorderLayout.SOUTH);

		details.add(detailsLeft, BorderLayout.WEST);
		details.add(detailsRightR, BorderLayout.CENTER);
		details.add(detailsRightL, BorderLayout.EAST);
		details.add(detailsEdit, BorderLayout.SOUTH);

		detailsLeft.add(new JLabel("Id : "));
		detailsLeft.add(id);
		detailsLeft.add(new JLabel("Nom : "));
		detailsLeft.add(name);
		detailsLeft.add(new JLabel("Prenom : "));
		detailsLeft.add(firstname);
		detailsLeft.add(new JLabel("Date de naissance : "));
		detailsLeft.add(pBirth);
		detailsLeft.add(new JLabel("Sexe : "));
		detailsLeft.add(pSexe);
		detailsLeft.add(new JLabel("Filière : "));
		detailsLeft.add(studies);

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

		detailsDown.add(hSelect, BorderLayout.NORTH);
		detailsDown.add(detailsDDown, BorderLayout.SOUTH);

		detailsDDown.add(new JLabel("Solde : "));
		detailsDDown.add(sold);
		detailsDDown.add(bDeposit);
		detailsDDown.add(sDeposit);

		detailsEdit.add(bEditValid);
		detailsEdit.add(bEdit);

		ctrl.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		ctrl.add(ctrlCenter, BorderLayout.CENTER);

		ctrlCenter.add(ok);

		update();

		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2)
				- (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this
				.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}

	public void updateId() {
		edit = false;
		int newId = (int) id.getValue();
		tableModel.setId(newId);
		if (Model.getInstance().isIdUsed(newId)) {
			newSexe = Model.getInstance().getUserById(newId).isMan();
			bEdit.setEnabled(true);
		} else {
			newSexe = true;
			bEdit.setEnabled(false);
		}
		update();
	}

	public void editProfil() {
		User u = Model.getInstance().getUserById((int) id.getValue());
		u.setName(name.getText());
		u.setFirstname(firstname.getText());
		u.setBirthDate(birthDate.getDate());
		u.setSexe(newSexe);
		u.setStudies(studies.getText());
		u.setMailStreet(mailStreet.getText());
		u.setMailPostalCode(mailPostalCode.getText());
		u.setMailTown(mailTown.getText());
		u.setEMail(eMail.getText());
		u.setNewsLetter(newsLetter.isSelected());
		u.setPhoneNumber(tel.getText());
		Model.getInstance().writeAllUSers();
		Model.getInstance().update();
	}

	private void updateSexe() {
		if (newSexe) {
			lSexe.setText("Homme");
			bSexe.setText("Homme");
		} else {
			lSexe.setText("Femme");
			bSexe.setText("Femme");
		}
	}
	
	public void update() {
		int userId = (int) id.getValue();
		tableModel.setId(userId);
		User u = model.getUserById(userId);

		CellRender cellRender = new CellRender(true);
		for (int i = 0; i < table.getModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}

		name.setEditable(edit);
		firstname.setEditable(edit);
		pBirth.removeAll();
		pSexe.removeAll();
		if (edit) {
			pBirth.add(birthDate);
			pSexe.add(bSexe);
			bEdit.setText("Annuler");
		} else {
			pBirth.add(lBirthDate);
			pSexe.add(lSexe);
			bEdit.setText("Editer");
		}
		bEditValid.setVisible(edit);
		bSexe.setEnabled(edit);
		studies.setEditable(edit);
		mailStreet.setEditable(edit);
		mailPostalCode.setEditable(edit);
		mailTown.setEditable(edit);
		eMail.setEditable(edit);
		newsLetter.setEnabled(edit);
		tel.setEditable(edit);

		if (u == null) {
			name.setText("...");
			firstname.setText("...");
			Calendar cal = Calendar.getInstance();
			cal.set(1900, 0, 1);
			birthDate.setDate(cal.getTime());
			lBirthDate.setText("../../....");
			lSexe.setText("...");
			bSexe.setText("...");
			studies.setText("...");
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
			birthDate.setDate(cal.getTime());
			lBirthDate.setText(Model.dateFormatSimple.format(u.getBirthDate()));
			updateSexe();
			studies.setText(u.getStudies());
			mailStreet.setText(u.getMailStreet());
			mailPostalCode.setText(u.getMailPostalCode());
			mailTown.setText(u.getMailTown());
			eMail.setText(u.getEMail());
			if(u.isValidEmailAddress()) {
				eMail.setForeground(Color.DARK_GRAY);
			} else {
				eMail.setForeground(Color.RED);
			}
			if(u.isAdult()) {
				lBirthDate.setForeground(Color.DARK_GRAY);
			} else {
				lBirthDate.setForeground(Color.RED);
			}
			newsLetter.setSelected(u.isNewsLetter());
			tel.setText(u.getPhoneNumber());

			DecimalFormat df = new DecimalFormat("#0.00");
			sold.setText(df.format((double) u.getAccount() / 100) + " €");
		}
		depositOn = false;
		sDeposit.setVisible(depositOn);
		bDeposit.setText("Dépôt");
		repaint();
		pack();
	}

}
