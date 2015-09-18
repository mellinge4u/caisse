package caisse.user;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

	protected int userId;
	protected String name;
	protected String firstname;
	protected boolean sexe;
	protected Date birthDate;
	protected String phoneNumber;
	protected String studies;
	protected String mailStreet;
	protected String mailPostalCode;
	protected String mailTown;
	protected String eMail;
	protected boolean newsLetter;
	protected int account;
	public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public User(int userId, String name, String firstname, boolean sexe,
			Date birthDate, String phoneNumber, String studies, String mailStreet,
			String mailPostalCode, String mailTown, String eMail,
			boolean newLetter) {
		this.userId = userId;
		this.name = name;
		this.firstname = firstname;
		this.sexe = sexe;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.studies = studies;
		this.mailStreet = mailStreet;
		this.mailPostalCode = mailPostalCode;
		this.mailTown = mailTown;
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

	public boolean isMan() {
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

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean isNewsLetter() {
		return newsLetter;
	}

	public void setNewsLetter(boolean newsLetter) {
		this.newsLetter = newsLetter;
	}

	public String getMailStreet() {
		return mailStreet;
	}

	public void setMailStreet(String mailStreet) {
		this.mailStreet = mailStreet;
	}

	public String getMailPostalCode() {
		return mailPostalCode;
	}

	public void setMailPostalCode(String mailPostalCode) {
		this.mailPostalCode = mailPostalCode;
	}

	public String getMailTown() {
		return mailTown;
	}

	public void setMailTown(String mailTown) {
		this.mailTown = mailTown;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId + "; ");
		sb.append(name + "; ");
		sb.append(firstname + "; ");
		sb.append(sexe + "; ");
		sb.append(df.format(birthDate) + "; ");
		sb.append(phoneNumber + "; ");
		sb.append(studies + "; ");
		sb.append(mailStreet + "; ");
		sb.append(mailPostalCode + "; ");
		sb.append(mailTown + "; ");
		sb.append(eMail + "; ");
		sb.append(newsLetter + "; ");
		return sb.toString();
	}

}
