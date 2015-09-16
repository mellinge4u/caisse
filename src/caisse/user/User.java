package caisse.user;

import java.text.SimpleDateFormat;
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
	public static SimpleDateFormat df = new SimpleDateFormat(
			"dd/MM/yyyy");

	
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

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public void creditAccount(int credit) {
		account += credit;
	}

	public void debitAccount(int debit) {
		account -= debit;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isSexe() {
		return sexe;
	}

	public void setSexe(boolean sexe) {
		this.sexe = sexe;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getStudies() {
		return studies;
	}

	public void setStudies(String studies) {
		this.studies = studies;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean isNewsLetter() {
		return newsLetter;
	}

	public void setNewsLetter(boolean newsLetter) {
		this.newsLetter = newsLetter;
	}

}
