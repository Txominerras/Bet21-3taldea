package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Transaction implements Serializable {

	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int transactionNumber;
	private int type;
	private double amount;
	@XmlIDREF
	private NormalUser user;

	public Transaction(int transactionNumber, int type, double amount, NormalUser u) {
		this.transactionNumber = transactionNumber;
		this.type = type;
		this.amount = amount;
		this.user = u;
	}

	public Transaction(int type, double amount, NormalUser u) {
		this.type = type;
		this.amount = amount;
		this.user = u;
	}

	public int getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(NormalUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		switch (type) {
		case 1: // dirua sartu da
			return ResourceBundle.getBundle("Etiquetas").getString("InsertedMoney") + ": " + amount;
		case 2: // dirua apostatu da
			return ResourceBundle.getBundle("Etiquetas").getString("BetedMoney") + ": " + amount;

		case 3: // apostua irabazita lortu den dirua:
			return ResourceBundle.getBundle("Etiquetas").getString("EarnedMoney") + ": " + amount;

		case 4: // Sistemak dirua itzultzen duen dirua:
			return ResourceBundle.getBundle("Etiquetas").getString("ReturnedMoney") + ": " + amount;

		case 5: // follow
			return ResourceBundle.getBundle("Etiquetas").getString("followingMoney") + ": " + amount;

		case 6:
			return ResourceBundle.getBundle("Etiquetas").getString("unfollowingMoney") + ": " + -amount;

		default:
			return null;
		}
	}

}
