package caisse.user;

import java.util.Date;

public class User {

	protected int userId;
	protected String name;
	protected String firstname;
	protected boolean sexe;
	protected Date birthDate;
	protected String studies;
	protected String mail;
	protected String eMail;
	protected boolean newsLetter;
	protected int account;

	public User(int userId, String name, String firstname, boolean sexe,
			Date birthDate, String studies, String mail, String eMail,
			boolean newLetter) {
		this.userId = userId;
		this.name = name;
		this.firstname = firstname;
		this.sexe = sexe;
		this.birthDate = birthDate;
		this.studies = studies;
		this.mail = mail;
		this.eMail = eMail;
		this.newsLetter = newLetter;
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
		return userId;
	}

	public void setUserNumberSSS(int userNumber) {
		this.userId = userNumber;
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
