package caisse.member;

import java.util.ArrayList;

public class Users {

	private ArrayList<User> users;
	
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
	
}
