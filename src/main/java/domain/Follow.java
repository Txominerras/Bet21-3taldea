package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Follow implements Serializable {

    @XmlID
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @Id @GeneratedValue
    private int id;
    @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private NormalUser followed;
    @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private NormalUser follower;
    private double money;
    private double betPercentage;

    public Follow (NormalUser pFollowed, NormalUser pFollower, double pMoney, double pBetPercentage ) {

        this.followed = pFollowed;
        this.follower = pFollower;
        this.money = pMoney;
        this.betPercentage = pBetPercentage;
    }
    public NormalUser getFollowed() {
        return followed;
    }

    public void setFollowed(NormalUser followed) {
        this.followed = followed;
    }

    public NormalUser getFollower() {
        return follower;
    }

    public void setFollower(NormalUser follower) {
        this.follower = follower;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getBetPercentage() {
        return betPercentage;
    }

    public void setBetPercentage(int betPercentage) {
        this.betPercentage = betPercentage;
    }

    public double moneyBet(double betmoney) {


        double  moneyToBet = betmoney*betPercentage;

        if(moneyToBet<=money) {
        return moneyToBet;
        }
        return 0;
    }
    public String toString() {
    	return followed.toString() + " " + this.money;
    }
}