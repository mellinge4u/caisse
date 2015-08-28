package caisse.member;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import caisse.product.RawMaterial;

public class Users extends AbstractTableModel{

	private ArrayList<User> users;
	protected String[] colNames = { "ID", "Nom", "Prenom", "Solde" };
	protected Class<?>[] colClass = { Integer.class, String.class,
			String.class,Integer.class };
	protected Boolean[] colEdit = { false, false, false,false };
	
	
	public Users(){
		this.users = new ArrayList<>();
	}
	
	public void addUser(User us){
		users.add(us);
	}
	
	public User getUserById(int i){
		User u = null;
		for(User us : users){
			if(us.getUserNumber() == i){ 
				u = us;
				break;
			}
		}
		
		if(u == null) return NonExistingUserException();
			
		return u;
		
	}
	
	public User getUserByName(String name){
		User u = null;
		for(User us : users){
			if(us.getName() == name){ 
				u = us;
				break;
			}
		}
		
		if(u == null) return NonExistingUserException();
			
		return u;
		
	}

	public void deleteUser(User us){
		users.remove(us);
	}
	
	private User NonExistingUserException() {
		
		return null;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
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
	
}
