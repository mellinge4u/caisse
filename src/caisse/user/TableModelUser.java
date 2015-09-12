package caisse.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class TableModelUser extends AbstractTableModel {

	private ArrayList<User> users;
	protected String[] colNames = { "ID", "Nom", "Prenom", "Solde" };
	protected Class<?>[] colClass = { Integer.class, String.class,
			String.class, Double.class };
	protected Boolean[] colEdit = { false, false, false, false };

	public TableModelUser() {
		this.users = new ArrayList<>();
	}

	public void addUser(String name, String firstname, int userNumber) {
		User user = new User(name, firstname, userNumber);
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

		return null;
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
		return users.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		switch (columnIndex) {
		case 0:
			return users.get(rowIndex).getUserNumber();
		case 1:
			return users.get(rowIndex).getName();
		case 2:
			return users.get(rowIndex).getFirstname();
		case 3:
			return ((double) users.get(rowIndex).getAcount() / 100);
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
