package caisse.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class TableModelUser extends AbstractTableModel {

	private ArrayList<User> users;
	protected String[] colNames = { "ID", "Nom", "Prenom", "Solde" };
	protected Class<?>[] colClass = { Integer.class, String.class, String.class, Double.class };
	protected Boolean[] colEdit = { false, false, false, false };

	public TableModelUser() {
		this.users = new ArrayList<>();
	}

	public void addUser(int userId, String name, String firstname,
			boolean sexe, Date birthDate, String phoneNumber, String studies, String mailStreet,
			String mailPostalCode, String mailTown, String eMail,
			boolean newLetter) {
		User user = new User(userId, name, firstname, sexe, birthDate, phoneNumber, studies,
				mailStreet, mailPostalCode, mailTown, eMail, newLetter);
		int id = user.getUserNumber();
		int i = 0;
		boolean added = false;
		for (User u : users) {
			if (id < u.getUserNumber()) {
				users.add(i, user);
				added = true;
				break;
			} else {
				i++;
			}
		}
		if (!added) {
			users.add(i, user);
		}
	}

	public User getUserById(int id) {
		User u = null;
		for (User us : users) {
			if (us.getUserNumber() == id) {
				u = us;
				break;
			}
		}

		if (u == null)
			return NonExistingUserException();

		return u;

	}

	public User getUserByName(String name) {
		User u = null;
		for (User us : users) {
			if (us.getName() == name) {
				u = us;
				break;
			}
		}

		if (u == null)
			return NonExistingUserException();

		return u;

	}

	public void deleteUser(User us) {
		users.remove(us);
	}

	private User NonExistingUserException() {
		// TODO Cette fonction ne sert a rien
		return null;
	}

	public int getNewId() {
		int id = 1;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int add = cal.get(Calendar.YEAR) - 2000;
		if (month < 8) {
			add--;
		}
		add *= 10000;
		id += add;

		int oldId;
		for (User u : users) {
			oldId = u.getUserId();
			if (oldId > add) {
				if (oldId == id) {
					id++;
				} else {
					break;
				}
			}
		}
		return id;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return colClass[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		return users.size() - 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		rowIndex += 2;
		switch (columnIndex) {
		case 0:
			return users.get(rowIndex).getUserNumber();
		case 1:
			return users.get(rowIndex).getName();
		case 2:
			return users.get(rowIndex).getFirstname();
		case 3:
			return ((double) users.get(rowIndex).getAccount() / 100);
		default:
			break;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return colEdit[columnIndex];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		switch (columnIndex) {
		case 1:
			// users.get(rowIndex).setStock((int) aValue);
			break;
		default:
			break;
		}
	}

	public void debitUser(int id, int debit) {
		getUserById(id).debitAccount(debit);
	}

}
