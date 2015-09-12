package caisse.user;

public class User {

	protected String name;
	protected String firstname;
	protected int userNumber;
	protected int account;

	// TODO Ajouter l'historique

	public User(String name, String firstname, int userNumber) {
		this.name = name;
		this.firstname = firstname;
		this.userNumber = userNumber;
		this.account = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumberSSS(int userNumber) {
		this.userNumber = userNumber;
	}

	public int getAcount() {
		return account;
	}

	public void setAcount(int account) {
		this.account = account;
	}

	public void creditAccount(int credit) {
		account += credit;
	}

	public void debitAccount(int debit) {
		account -= debit;
	}

}
