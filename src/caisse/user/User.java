package caisse.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import caisse.Model;

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

	public User(int userId, String name, String firstname, boolean sexe,
			Date birthDate, String phoneNumber, String studies,
			String mailStreet, String mailPostalCode, String mailTown,
			String eMail, boolean newLetter) {
		this.userId = userId;
		this.name = name;
		if (this.name.equals("")) {
			this.name = "Sans nom";
		}
		this.firstname = firstname;
		if (this.firstname.equals("")) {
			this.firstname = "Sans prenom";
		}
		this.sexe = sexe;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		if (this.phoneNumber.equals("")) {
			this.phoneNumber = "00 00 00 00 00";
		}
		this.studies = studies;
		if (this.studies.equals("")) {
			this.studies = "n/a";
		}
		this.mailStreet = mailStreet;
		if (this.mailStreet.equals("")) {
			this.mailStreet = "Sdf";
		}
		this.mailPostalCode = mailPostalCode;
		if (this.mailPostalCode.equals("")) {
			this.mailPostalCode = "0";
		}
		this.mailTown = mailTown;
		if (this.mailTown.equals("")) {
			this.mailTown = "Nulpart";
		}
		this.eMail = eMail;
		if (this.eMail.equals("")) {
			this.eMail = "nom@hebergeur.ext";
		}
		this.newsLetter = newLetter;
		this.account = 0;
		if (name.equals("")) {
			name = "Sans nom";
		}
	}

	public User(int id) {
		this.userId = id;
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

	public boolean isValidEmailAddress() {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(eMail);
		return m.matches();
	}

	public boolean isAdult() {
		Calendar nowM18 = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthDate);
		nowM18.add(Calendar.YEAR, -18);
		return nowM18.after(birth);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
		sb.append(';');
		sb.append(name);
		sb.append(';');
		sb.append(firstname);
		sb.append(';');
		sb.append(Model.dateFormatSimple.format(birthDate));
		sb.append(';');
		sb.append(sexe);
		sb.append(';');
		sb.append(studies);
		sb.append(';');
		sb.append(mailStreet);
		sb.append(';');
		sb.append(mailPostalCode);
		sb.append(';');
		sb.append(mailTown);
		sb.append(';');
		sb.append(eMail);
		sb.append(';');
		sb.append(newsLetter);
		sb.append(';');
		sb.append(phoneNumber);
		sb.append(';');
		return sb.toString();
	}

}
