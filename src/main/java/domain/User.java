package domain;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ NormalUser.class, Admin.class })
@Entity
public abstract class User implements Serializable {
	@Id
	@XmlID
	private String userName;
	private String password;
	private String email;
	private int phoneNumber;
	private int age;
	private String realName;
	private String realSurname;

	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private ArrayList<Chat> txatak;

	public User(String userName, String password, String email, int phoneNumber, int age, String realName,
			String realSurname) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.realName = realName;
		this.realSurname = realSurname;
		txatak = new ArrayList<Chat>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Chat> getTxatak() {
		return txatak;
	}

	public void setTxatak(ArrayList<Chat> txatak) {
		this.txatak = txatak;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealSurname() {
		return realSurname;
	}

	public void setRealSurname(String realSurname) {
		this.realSurname = realSurname;
	}

	public boolean correctPassword(String pass) {
		return this.password.equals(pass);
	}

	public boolean availableEmail(String email) {
		if (this.getEmail().equals(email) || email.equals("")) {
			return false;
		}
		return true;
	}

	public boolean availablePhoneNumber(int number) {
		if (this.getPhoneNumber() == number)
			return false;
		return true;
	}

	public void addChat(Chat txata) {
		txatak.add(txata);
	}

	public Group createGroup(ArrayList<User> users, String gName) {
		Group group = new Group(gName, users, this.getUserName());
		// this.txatak.add(group);
		for (User u : users) {
			u.addChat(group);
		}
		return group;
	}

	public void removeGroup(Group group) {
		this.txatak.remove(group);
	}
}
