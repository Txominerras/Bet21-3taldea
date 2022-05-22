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
public class Quote implements Serializable {
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer cuoteNumber;
	private float prize;
	private String option;
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Question question;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Bet> betList;
	
	
	public Quote(String option, float prize, Question question){
		this.option = option;
		this.prize = prize;
		this.betList = new ArrayList<Bet>();
		this.question=question;
	}
	public void setOption(String option) {
		this.option=option;
	}
	public String getOption() {
		return this.option;
	}
	public void setPrize(float prize) {
		this.prize=prize;
	}
	
	public boolean hasBets() {
		return !this.getBetList().isEmpty();
	}
	public float getPrize() {
		return this.prize;
	}
	/*@Override
	public String toString() {
		return "Número de cuota" + cuoteNumber +", Ganancia: " +  prize +", opción:" + option;
	}*/
	public Integer getCuoteNumber() {
		return cuoteNumber;
	}
	public void setCuoteNumber(Integer cuoteNumber) {
		this.cuoteNumber = cuoteNumber;
	}
	public ArrayList<Bet> getBetList() {
		return betList;
	}
	public void setBetList(ArrayList<Bet> betList) {
		this.betList = betList;
	}
	public void addBet(Bet bet) {
		this.betList.add(bet);
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public void deleteBet(int betNumber) {
        for(Bet cbet: betList) {
            if(betNumber == cbet.getBetNumber()) {
                Bet bet = cbet;
                betList.remove(bet);
                break;

            }
        }
    }
	@Override
    public String toString() {
        return  ResourceBundle.getBundle("Etiquetas").getString("Question") + ": " + question.getQuestion() + ",  " + ResourceBundle.getBundle("Etiquetas").getString("Cuote") + ": " + option +  "/ " + ResourceBundle.getBundle("Etiquetas").getString("Prize") + ": " + prize;
	}
}
