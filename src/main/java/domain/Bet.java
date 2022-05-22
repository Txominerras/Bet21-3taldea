package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable{	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private int betNumber;
	private double betedMoney;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private User user;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	//private Cuote cuote
	private ArrayList<Quote> cuote;
	private Boolean WonBet;
	private boolean multiBet;
	
	public Bet(int betNumber, int betedMoney, User user, ArrayList<Quote> cuote) {
		this.betNumber=betNumber;
		this.betedMoney=betedMoney;
		this.user=user;
		this.cuote=cuote;
		if(this.cuote.size()>1)
			this.multiBet = true;
		else this.multiBet=false;
	}
	public Bet(double betedMoney, User user, ArrayList<Quote> cuote) {
		this.betedMoney=betedMoney;
		this.user=user;
		this.cuote=cuote;
		if(this.cuote.size()>1)
			this.multiBet = true;
		else this.multiBet=false;
	}
	public int getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(int betNumber) {
		this.betNumber = betNumber;
	}

	public double getBetedMoney() {
		return betedMoney;
	}

	public void setBetedMoney(int betedMoney) {
		this.betedMoney = betedMoney;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Quote> getCuote() {
		return cuote;
	}

	public void setCuote(ArrayList<Quote> cuote) {
		this.cuote = cuote;
	}
	public Boolean getWonBet() {
		return WonBet;
	}
	public void setWonBet(Boolean wonBet) {
		WonBet = wonBet;
	}
	public void setMultiBet(boolean b) {
		this.multiBet=b;
	}
	public boolean getMultiBet() {
		return this.multiBet;
	}
	public float WonMoney() {
		if(this.multiBet=false) {
			Quote c = cuote.get(0);
			float mult = (float) c.getPrize();
			return (mult*(float)betedMoney);
		}
		else {
			float mult=1;
			 for(Quote c: this.cuote) {
				 mult = mult * (float)c.getPrize();
			 }
			 return mult*(float)betedMoney;
		}
	}
	/*
	 * Bet arrunta bada bere listako kuota itzultzen du. Kuota hori bakarra izango da, listan lehenengo posizioan dagoena.
	 * 
	 */
	public Quote getBetCuote() {
		if(!this.multiBet) return this.getCuote().get(0);
		else return null;
	}
	@Override
    public String toString() {
		String s ="";
		for(Quote c: this.cuote)
			s = s + c.getOption() + " / "+ c.getPrize() + " // ";
		if(!multiBet)
			return "MultiBet: " + ResourceBundle.getBundle("Etiquetas").getString("No") + ", " + ResourceBundle.getBundle("Etiquetas").getString("Cuote") + ": " + s + ". " + ResourceBundle.getBundle("Etiquetas").getString("BetedMoney") + ": " + betedMoney;
		else
			return "MultiBet: " + ResourceBundle.getBundle("Etiquetas").getString("Yes") + ", " + ResourceBundle.getBundle("Etiquetas").getString("Cuote") + ": " + s + ". " + ResourceBundle.getBundle("Etiquetas").getString("BetedMoney") + ": " + betedMoney;
	}
}
